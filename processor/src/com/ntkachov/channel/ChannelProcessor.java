package com.ntkachov.channel;
import com.ntkachov.channel.policy.ChannelPolicyException;
import com.ntkachov.channel.policy.Policy;
import com.ntkachov.channel.policy.StrictPolicy;
import com.sun.source.util.TreePath;
import com.sun.source.util.Trees;


import javax.annotation.processing.AbstractProcessor;import javax.annotation.processing.Filer;import javax.annotation.processing.Messager;import javax.annotation.processing.ProcessingEnvironment;import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import javax.tools.Diagnostic;
import java.lang.Override;import java.lang.String;import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Nick on 3/10/15.
 */
public class ChannelProcessor extends AbstractProcessor {

    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;
    private Trees trees;

    private Targets targets;
    private Policy policy;

    public ChannelProcessor() {

    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        targets = new Targets();
        policy = new StrictPolicy();
        Logger.setMessager(messager);
        this.trees = Trees.instance(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, "Processing Channels");
        for(Element channelElement : roundEnv.getElementsAnnotatedWith(Channel.class)){
            Channel annotation = channelElement.getAnnotation(Channel.class);
            String[] variable = annotation.value();
            targets.addTarget(variable);
            walkElementTree(channelElement);
        }

        Logger.d("Targets: " + targets.toString());

        for (Element annotatedElement : roundEnv.getRootElements()){
            analyseTreeOfClass(annotatedElement);
        }


        return true;
    }

    private void analyseTreeOfClass(Element annotatedElement ){
        CodeTreeAnalyzer analyzer = new CodeTreeAnalyzer();
        TreePath path = trees.getPath(annotatedElement);
        analyzer.scan(path, trees);
    }

    private void walkElementTree(Element elem){
        try {
            policy.verifyTargetElements(elem, targets);
        } catch (ChannelPolicyException e){
            Logger.e(elem, e);
        }
        for(Element element : elem.getEnclosedElements()){
            walkElementTree(element);
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotataions = new LinkedHashSet<String>();
        annotataions.add(MyAnnotation.class.getCanonicalName());
        annotataions.add(Channel.class.getCanonicalName());
        return annotataions;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }


}
