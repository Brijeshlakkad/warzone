package com.warzone.team08.VM.exceptions;


import com.warzone.team08.VM.VirtualMachine;

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    public void uncaughtException(Thread t, Throwable e) {
        VirtualMachine.getInstance().stderr("Something went wrong!");
    }
}