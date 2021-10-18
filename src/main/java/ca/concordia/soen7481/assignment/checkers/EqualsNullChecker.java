package ca.concordia.soen7481.assignment.checkers;

import ca.concordia.soen7481.assignment.DirExplorer;
import ca.concordia.soen7481.assignment.bugpatterns.BugPattern;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EqualsNullChecker implements Checker {
    public List<BugPattern> check(File projectDir) {

        List<BugPattern> bugPatterns = new ArrayList<>();

        new DirExplorer((level, path, file) -> path.endsWith(".java"), (level, path, file) -> {
            try {
                final boolean[] equalsFound = {false};

                final int[] line = {0};

                new VoidVisitorAdapter<Object>() {
                    @Override
                    public void visit(MethodDeclaration md, Object arg) {
                        super.visit(md, arg);
                        if (md.getNameAsString().equals("equals") && md.getTypeAsString().equals("boolean")) {
                            NodeList<Parameter> nodes = md.getParameters();
                            if ((nodes.size() == 1) && (nodes.get(0).getTypeAsString().equals("Object"))) {
                               Optional<BlockStmt> body = md.getBody();
                                if(body.isPresent()) {
                                    equalsFound[0] = true;
                                }

                                // Get line
                                line[0] = (md.getRange().isPresent() ? md.getRange().get().begin.line : 0);
                            }
                        } 
                    }
                }.visit(JavaParser.parse(file), null);

                if (equalsFound[0]) {
                   // bugPatterns.add(new EqualsNullChecker(line[0], file, "equals"));
                }
            } catch (IOException e) {
                new RuntimeException(e);
            }
        }).explore(projectDir);

        //return !equalsFound || hcFound;
        return bugPatterns;
    }
}
