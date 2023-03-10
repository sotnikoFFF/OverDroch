package com.meew.overparser.parser.exceptions;

import org.slf4j.LoggerFactory;

import java.util.logging.Logger;

public class ParserException extends Exception {


    public ParserException(String msg, Class clss){
        LoggerFactory.getLogger(clss).error(msg);
    }

    public ParserException(String msg){
        LoggerFactory.getLogger(getCallerClassName()).error(msg);
    }

    public static Class getCallerClassName() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i=1; i<stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!ste.getClassName().equals(ParserException.class.getName()) && ste.getClassName().indexOf("java.lang.Thread")!=0) {
                return ste.getClass();
            }
        }
        return null;
    }
}
