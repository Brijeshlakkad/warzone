package com.warzone.team08.CLI.services;

import com.warzone.team08.CLI.constants.specifications.CommandSpecification;
import com.warzone.team08.CLI.exceptions.InvalidArgumentException;
import com.warzone.team08.CLI.exceptions.InvalidCommandException;
import com.warzone.team08.CLI.layouts.UserClassLayout;
import com.warzone.team08.CLI.models.UserCommand;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * This class decides the <code>API</code> to call based on interpreted the <code>UserCommand</code>.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class RequestService {
    /**
     * This variable will be used when a single command with or without its value will be entered.
     * <p>
     * For example: > savemap filename
     * <p>
     * In this case, the method with this name will be called of a specific class decided at runtime.
     */
    private final String DEFAULT_METHOD_NAME = "execute";

    /**
     * Calls the suggested function of the mapped class. Uses the mappings of the command text to the class name. Here,
     * the suggested function is the same as the argument key provided by the user, and its values are the argument to
     * be passed to the function call. (Mappings for this can be created if the both are not the same?)
     *
     * @param p_userCommand Value of the object (instance) which is equivalent to the user text input.
     * @throws InvalidArgumentException Raised if the command not found.
     * @throws InvalidCommandException  Raised if the argument key(s) not found or its value(s) are not provided.
     */
    public void takeAction(UserCommand p_userCommand) throws InvalidArgumentException, InvalidCommandException {
        // Gets the mapped class of the command and calls its function; With arguments, if any.
        try {
            Class<?> l_class = Class.forName(UserClassLayout.matchAndGetClassName(p_userCommand.getHeadCommand()));
            Object l_object = l_class.getDeclaredConstructor().newInstance();

            // If the command does not have any argument keys
            if (p_userCommand.getPredefinedUserCommand().getCommandSpecification()
                    != CommandSpecification.AT_LEAST_ONE) {
                // Call the default method of the instance with the arguments
                this.handleMethodInvocation(l_object, DEFAULT_METHOD_NAME, p_userCommand.getCommandValues(), true);
            } else {
                // Iterate over the user arguments
                for (Map.Entry<String, List<String>> entry : p_userCommand.getUserArguments().entrySet()) {
                    String p_argKey = entry.getKey();
                    List<String> p_argValues = entry.getValue();

                    // If the argument key does not have any value, it will send empty list
                    this.handleMethodInvocation(l_object, p_argKey, p_argValues, false);
                }
            }
        } catch (ClassNotFoundException | IllegalAccessException |
                InstantiationException p_e) {

            throw new InvalidCommandException("Command not found!");
        } catch (NoSuchMethodException |
                InvocationTargetException p_e) {
            // If belongs to VMException
            if (p_e.getCause() != null &&
                    p_e.getCause().getClass() != null &&
                    p_e.getCause().getClass().getSuperclass() != null &&
                    p_e.getCause().getClass().getSuperclass().getName().equals("com.warzone.team08.VM.exceptions.VMException")) {
                throw new InvalidCommandException(p_e.getCause().getMessage());
            } else {
                throw new InvalidArgumentException("Unrecognized argument and/or its values");
            }
        }
    }

    /**
     * This method handles the actual call of the specific method at runtime. Prepares two arrays of Class and Object
     * for the argument type and the value respectively. Uses these arrays to find the method and call the method with
     * the value(s).
     *
     * @param p_object    An instance of the object which specifies the class for being called method
     * @param p_argKey    Value of the argument key passed by the user; This represents also the method name of the
     *                    object.
     * @param p_argValues Value of the argument values; This represents the arguments to be passed to the method call.
     * @throws NoSuchMethodException     Raised if the method doesn't exist at the object.
     * @throws InvocationTargetException Raised if invoked a method that throws an underlying exception itself.
     * @throws IllegalAccessException    Raised if the method is not accessible by the caller.
     */
    private void handleMethodInvocation(Object p_object,
                                        String p_argKey,
                                        List<String> p_argValues,
                                        boolean shouldMergeValues)
            throws NoSuchMethodException,
            InvocationTargetException,
            IllegalAccessException {
        Object l_responseObject;
        if (shouldMergeValues) {
            // Get the reference and call the method with arguments
            Method l_methodReference = p_object.getClass().getMethod(p_argKey, List.class);
            l_responseObject = l_methodReference.invoke(p_object, p_argValues);
        } else {
            // Create two arrays:
            // 1. For type of the value
            // 2. For the values
            Class<?>[] l_valueTypes = new Class[p_argValues.size()];
            Object[] l_values = p_argValues.toArray();
            for (int l_argIndex = 0; l_argIndex < p_argValues.size(); l_argIndex++) {
                l_valueTypes[l_argIndex] = String.class;
            }

            // Get the reference and call the method with arguments
            Method l_methodReference = p_object.getClass().getMethod(p_argKey, l_valueTypes);
            l_responseObject = l_methodReference.invoke(p_object, l_values);
        }
        try {
            String l_responseValue = (String) l_responseObject;
            if (!l_responseValue.isEmpty()) {
                System.out.println(l_responseValue);
            }
        } catch (
                Exception l_ignored) {
            // Ignore exception if the object does not represent the String value.
        }
    }
}
