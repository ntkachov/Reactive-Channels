package com.ntkachov.channel;
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

    public ChannelProcessor() {

    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        this.trees = Trees.instance(processingEnv);
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, "Processing Channels");

        for (Element annotatedElement : roundEnv.getRootElements()){
            analyseTreeOfClass(annotatedElement);
            printElem(annotatedElement, 0);
        }
        return true;
    }

    private void analyseTreeOfClass(Element annotatedElement ){
        CodeTreeAnalyzer analyzer = new CodeTreeAnalyzer(messager);
        TreePath path = trees.getPath(annotatedElement);
        analyzer.scan(path, trees);
    }

    private void printElem(Element elem, int depth){
        messager.printMessage(Diagnostic.Kind.WARNING,
                depth + " " + elem.getSimpleName() + " " + elem.getModifiers().toString()
                        + " " +elem.asType() + " " + elem.getKind() );

        depth++;
        for(Element element : elem.getEnclosedElements()){
            printElem(element, depth);
        }
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotataions = new LinkedHashSet<String>();
        annotataions.add(MyAnnotation.class.getCanonicalName());
        return annotataions;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_7;
    }
}
