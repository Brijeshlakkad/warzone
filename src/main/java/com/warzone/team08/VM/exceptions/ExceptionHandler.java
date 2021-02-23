package com.warzone.team08.VM.exceptions;


public class ExceptionHandler implements Thread.UncaughtExceptionHandler {
    public void uncaughtException(Thread t, Throwable e) {
        System.out.print("Unhandled exception caught!");
    }
}