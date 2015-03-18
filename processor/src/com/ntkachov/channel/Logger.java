package com.ntkachov.channel;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * Created by Nick on 3/16/15.
 */
public class Logger {

    private static Messager mMessager;

    public static void setMessager(Messager messager){
        mMessager = messager;
    }

    public static void d(String msg){
        mMessager.printMessage(Diagnostic.Kind.WARNING,msg);
    }

    public static void e(Element elem, Exception e) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, e.getLocalizedMessage(), elem);
    }
}
