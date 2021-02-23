package com.warzone.team08.CLI;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.warzone.team08.Application;
import com.warzone.team08.CLI.constants.specifications.CommandSpecification;
import com.warzone.team08.CLI.constants.states.UserInteractionState;
import com.warzone.team08.CLI.exceptions.InvalidArgumentException;
import com.warzone.team08.CLI.exceptions.InvalidCommandException;
import com.warzone.team08.CLI.layouts.UserClassLayout;
import com.warzone.team08.CLI.mappers.UserCommandMapper;
import com.warzone.team08.CLI.models.UserCommand;
import com.warzone.team08.UserInterfaceMiddleware;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * <pre>
 * This class represents the command line user interface where:
 * User asked to enter the text, interpret the text, and take action accordingly.
 * </pre>
 * The motivation behind this is to let different interfaces use with the same instance of Virtual Machine.
 *
 * @author Brijesh Lakkad
 * @version 1.0
 */
public class CommandLineInterface implements Runnable, UserInterfaceMiddleware {
    /**
     * Interprets user input text and converts it into the form which can be understood
     */
    private static UserCommandMapper d_userCommandMapper;

    /**
     * Keeps track of user interaction
     */
    private UserInteractionState d_interactionState = UserInteractionState.WAIT;

    /**
     * Gets the state of user interaction
     *
     * @return Value of the state of user interaction
     */
    public UserInteractionState getInteractionState() {
        return d_interactionState;
    }

    /**
     * Sets the state of user interaction whether: 1. the program is waiting for user to enter the input 2. User is
     * waiting for the execution to finish
     *
     * @param p_interactionState the state of user interaction
     */
    public void setInteractionState(UserInteractionState p_interactionState) {
        this.d_interactionState = p_interactionState;
    }

    /**
     * This variable will be used when a øsingle command with or without its value will be entered.
     * <p>
     * For example: > savemap filename
     * <p>
     * In this case, the method with this name will be called of a specific class decided at runtime.
     */
    private final String DEFAULT_METHOD_NAME = "execute";

    /**
     * Represents the created thread of this class implementing Runnable interface
     */
    public final Thread d_thread;

    private Queue<UserCommand> d_userCommandQueue = new LinkedList<>();

    public CommandLineInterface() {
        d_thread = new Thread(this);
        d_userCommandMapper = new UserCommandMapper();
    }

    /**
     * <code>InputStream</code> channel for user to provide the input.
     *
     * @param p_inputStream Value of any <code>InputStream</code>
     */
    public void setIn(InputStream p_inputStream) {
        System.setIn(p_inputStream);
    }

    /**
     * Waits for user input from command line interface
     *
     * @return Value of user text command
     * @throws IOException If any interruption occurs while waiting for user
     */
    private String waitForUserInput() throws IOException {
        // Enter data using BufferReader
        BufferedReader l_bufferedReader =
                new BufferedReader(new InputStreamReader(System.in));

        // Reading data using readLine and trim the input string
        return l_bufferedReader.readLine().trim();
    }

    /**
     * Method to be called when thread steps
     */
    public void run() {
        while (Application.isRunning()) {
            synchronized (this) {
                try {
                    if (this.getInteractionState() == UserInteractionState.WAIT) {
                        try {
                            // Takes user input and interprets it for further processing
                            UserCommand l_userCommand = d_userCommandMapper.toUserCommand(this.waitForUserInput());
                            if (l_userCommand.getPredefinedUserCommand().isGameEngineCommand()) {
                                throw new InvalidCommandException("Invalid command!");
                            }
                            this.setInteractionState(UserInteractionState.IN_PROGRESS);
                            // Takes action according to command instructions.
                            this.takeAction(l_userCommand);
                        } catch (IOException p_e) {
                            p_e.printStackTrace();
                        }
                    }
                    if (!d_userCommandQueue.isEmpty()) {
                        UserCommand l_userCommand = d_userCommandQueue.poll();
                        // Takes action according to command instructions.
                        this.takeAction(l_userCommand);
                    }
                } catch (InvalidArgumentException | InvalidCommandException p_exception) {
                    // Show exception message
                    // In Graphical User Interface, we can show different modals respective to the exception.
                    System.out.println(p_exception.getMessage());
                }
            }
        }
    }

    /**
     * Calls the suggested function of the mapped class. Uses the mappings of the command text to the class name. Here,
     * the suggested function is the same as the argument key provided by the user, and its values are the argument to
     * be passed to the function call. (Mappings for this can be created if the both are not the same?)
     *
     * @param p_userCommand  Value of the object (instance) which is equivalent to the user text input.
     * @param isGameEngineOn True if this action was taken while user was asked for input from <code>GameEngine</code>.
     * @throws InvalidArgumentException Raised if the command not found.
     * @throws InvalidCommandException  Raised if the argument key(s) not found or its value(s) are not provided.
     */
    public void takeAction(UserCommand p_userCommand, boolean isGameEngineOn) throws InvalidArgumentException, InvalidCommandException {
        try {
            // Gets the mapped class of the command and calls its function; With arguments, if any.
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
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException p_e) {
            throw new InvalidCommandException("Command not found!");
        } catch (NoSuchMethodException | InvocationTargetException p_e) {
            // If belongs to VMException
            if (p_e.getCause() != null &&
                    p_e.getCause().getClass() != null &&
                    p_e.getCause().getClass().getSuperclass() != null &&
                    p_e.getCause().getClass().getSuperclass().getName().equals("com.warzone.team08.VM.exceptions.VMException")) {
                throw new InvalidCommandException(p_e.getCause().getMessage());
            } else {
                throw new InvalidArgumentException("Unrecognized argument and/or its values");
            }
        } finally {
            if (!isGameEngineOn) {
                // Before returning, set interaction state to WAIT.
                this.setInteractionState(UserInteractionState.WAIT);
            }
        }
    }

    /**
     * {@link CommandLineInterface#takeAction(UserCommand, boolean)} Overloading function.
     *
     * @param p_userCommand Value of the object (instance) which is equivalent to the user text input.
     * @throws InvalidArgumentException Raised if the command not found.
     * @throws InvalidCommandException  Raised if the argument key(s) not found or its value(s) are not provided.
     */
    public void takeAction(UserCommand p_userCommand) throws InvalidArgumentException, InvalidCommandException {
        // By default, pass variable indicating that the game engine is not on.
        this.takeAction(p_userCommand, false);
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


    /**
     * Asks the user for command-input. Represented exchange messages in string for communication.
     *
     * @param p_message Message to be shown before asking for input.
     * @return Value of interpreted user command;
     */
    @Override
    public String askForUserInput(String p_message) {
        try {
            // Print the message if any.
            if (p_message != null && !p_message.isEmpty()) {
                System.out.println(p_message);
            }
            ObjectMapper mapper = new ObjectMapper();
            UserCommand l_userCommand = d_userCommandMapper.toUserCommand(this.waitForUserInput());
            if (l_userCommand.getPredefinedUserCommand().isGameEngineCommand()) {
                return mapper.writeValueAsString(l_userCommand);
            } else {
                d_userCommandQueue.add(l_userCommand);
            }
            return "";
        } catch (IOException p_ioException) {
            return "";
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param p_message Represents the message.
     */
    public void stdout(String p_message) {
        System.out.println(p_message);
    }

    /**
     * {@inheritDoc}
     *
     * @param p_message Represents the error message.
     */
    public void stderr(String p_message) {
        System.out.println(p_message);
    }
}
