package com.ntkachov.channel.policy;

import com.ntkachov.channel.Logger;
import com.ntkachov.channel.Targets;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.tools.Diagnostic;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nick on 3/16/15.
 */
public class Policy {

    public static interface PolicyModifierMatchingOperation {
        void matchModifiers(Set<Modifier> validMods, Set<Modifier> elementMods);
    }

    private Set<Modifier> validModifiers;
    Policy(){
        validModifiers = getValidModifiers();
    }

    public void verifyTargetElements(Element annotatedElement, Targets targets) {
        Logger.d("verifyTargetElements " + annotatedElement.getSimpleName() + " "
                + annotatedElement.getModifiers() + "");
        if(annotatedElement.getKind() == ElementKind.FIELD){
            matchModifiers(annotatedElement);
        }
    }

    public static class ALL implements PolicyModifierMatchingOperation{
        @Override
        public void matchModifiers(Set<Modifier> validMods, Set<Modifier> elementMods) {
            if(!(validMods.containsAll(elementMods) && elementMods.containsAll(validMods))){
                throw new ChannelPolicyException("Channel Variable must be " + validMods.toString() + " in accordance " +
                        "with this policy");
            }
        }
    }

    public static class SOME implements PolicyModifierMatchingOperation{
        @Override
        public void matchModifiers(Set<Modifier> validMods, Set<Modifier> elementMods) {
            if(!validMods.containsAll(elementMods)){
                throw new ChannelPolicyException("Channel Variable cannot be " + elementMods.toString()
                        + ". Valid modifiers for this policy are " + validMods.toString());
            }
        }
    }

    protected void matchModifiers(Element annotatedElement) {
        getMatchOperation().matchModifiers(validModifiers, annotatedElement.getModifiers());
    }

    protected Set<Modifier> getValidModifiers() {
        Set<Modifier> validMods = new HashSet<Modifier>(1);
        validMods.add(Modifier.PRIVATE);
        return validMods;
    }

    protected PolicyModifierMatchingOperation getMatchOperation(){
        return new ALL();
    }

}
