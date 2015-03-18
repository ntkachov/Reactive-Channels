package com.ntkachov.channel;

import sun.tools.java.CompilerError;

import javax.annotation.processing.Messager;
import java.lang.annotation.AnnotationFormatError;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Nick on 3/16/15.
 */
public class Targets {

    private HashMap<String, Object> targetMap;

    public Targets(){
        targetMap = new HashMap<String, Object>();
    }

    public void addTarget(String target){
        validateTarget(target);
        this.targetMap.put(target, null);
    }

    public void addTarget(String[] target){
        for(String t : target){
            addTarget(t);
        }
    }

    public boolean isTarget(String variable){
        return targetMap.containsKey(variable);
    }

    private void validateTarget(String target) {
        if(target == null){
            throw new AnnotationFormatError("Channel target is null");
        }
        if(Utils.isTextEmpty(target)){
            throw new AnnotationFormatError("Channel target cannot contain spaces");
        }
    }

    @Override
    public String toString() {
        String toString = "{";
        for(Map.Entry entry : targetMap.entrySet()){
            toString += entry.getKey() + " : " + entry.getValue() + " ,";
        }
        return toString + "}";
    }
}
