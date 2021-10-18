package ca.concordia.soen7481.assignment.checkers;

import ca.concordia.soen7481.assignment.DirExplorer;
import ca.concordia.soen7481.assignment.Util;
import ca.concordia.soen7481.assignment.bugpatterns.BugPattern;
import ca.concordia.soen7481.assignment.bugpatterns.IneffectiveConditionBugPattern;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IneffectiveConditionChecker implements Checker {

	@Override
	public List<BugPattern> check(File projectDir) {
		List<BugPattern> bugPatterns = new ArrayList<>();

        new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            try {
                new VoidVisitorAdapter<Object>() {
                    @Override
                    public void visit(IfStmt n, Object arg) {
                       super.visit(n, arg);

                       Expression cond = n.getCondition();
                       if(cond.isLiteralExpr()) {
                    	   bugPatterns.add(new IneffectiveConditionBugPattern(Util.getLineNumber(n), file, Util.getFunctionName(n)));
                       } else if(cond.isBinaryExpr()) {
                    	   BinaryExpr exp = cond.asBinaryExpr();
                    	   
            			   Expression expLeft = exp.getLeft();
            			   StringBuilder sbLeft = new StringBuilder();
            			   expLeft.getChildNodes().forEach(expNode -> sbLeft.append(expNode.toString()));
            			   String leftStr = sbLeft.toString();
            			   Expression expRight = exp.getRight();
            			   StringBuilder sbRight = new StringBuilder();
            			   expRight.getChildNodes().forEach(expNode -> sbRight.append(expNode.toString()));
            			   String rightStr = sbRight.toString();
            			   if(rightStr.equals(leftStr)) {
            				   bugPatterns.add(new IneffectiveConditionBugPattern(Util.getLineNumber(n), file, Util.getFunctionName(n)));
                		   }
                       }
                    }
                }.visit(JavaParser.parse(file), null);
            } catch (IOException e) {
                new RuntimeException(e);
            }
        }).explore(projectDir);

        return bugPatterns;
	}

}
