package com.ntkachov.channel;

import com.sun.source.tree.ExpressionStatementTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

import javax.annotation.processing.Messager;
import javax.tools.Diagnostic;

/**
 * Created by Nick on 3/16/15.
 */
public class CodeTreeAnalyzer extends TreePathScanner<Object, Trees> {

    public CodeTreeAnalyzer() {
        super();
    }

    @Override
    public Object visitMethod(MethodTree node, Trees trees) {
//        Logger.d("Method:" + node.getName().toString());
        return super.visitMethod(node, trees);
    }

    @Override
    public Object visitVariable(VariableTree node, Trees trees) {
//        Logger.d("Variable:" + node.getName().toString());
        return super.visitVariable(node, trees);
    }

    //This is how we can see if our
    @Override
    public Object visitExpressionStatement(ExpressionStatementTree node, Trees trees) {
        Logger.d("Expression:" + node.getExpression().toString() + " " + trees.getSourcePositions().getStartPosition(this.getCurrentPath().getCompilationUnit(), node));
        return super.visitExpressionStatement(node, trees);
    }
}
