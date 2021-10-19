package ca.concordia.soen7481.assignment.checkers;

import ca.concordia.soen7481.assignment.DirExplorer;
import ca.concordia.soen7481.assignment.bugpatterns.BugPattern;
import ca.concordia.soen7481.assignment.bugpatterns.EqualsNullCheckBugPattern;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


    public class EqualsNullChecker implements Checker  {
        public List<BugPattern> check(File projectDir) {
    
            List<BugPattern> bugPatterns = new ArrayList<>();
    
            new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
                try {
                    
                    final boolean[] temp = {false};
    
                    final int[] lineNo = {0};
    
                    new VoidVisitorAdapter<Object>() {
                        private Object BooleanLiteralExpr;
                        @Override
                        public void visit(MethodDeclaration md, Object arg) {
                            super.visit(md, arg);
                            BooleanLiteralExpr ar = new BooleanLiteralExpr();
                            if (md.getNameAsString().equals("equals") && md.getTypeAsString().equals("boolean") && ar.getValue()==false) {
                                    temp[0]=false;
                                    lineNo[0] = (md.getRange().isPresent() ? md.getRange().get().begin.line : 0);
                                }
                            }
                        }.visit(JavaParser.parse(file), null);
    
                    if (temp[0]==false) {
                        bugPatterns.add(new EqualsNullCheckBugPattern(lineNo[0], file, "equals"));
                    }
                } catch (IOException e) {
                    new RuntimeException(e);
                }
            }).explore(projectDir);
            
            return bugPatterns;
        }
    }
