package ca.concordia.soen7481.assignment.bugpatterns;

import java.io.File;

public class EqualsNullCheckBugPattern extends BugPattern {
    public EqualsNullCheckBugPattern(int line, File file, String functionName) {
        super(line, file, functionName);
    }
    @Override
    public String getIdentifier() {
        return "HE";
    }

    @Override
    public String getName() {
        return "Equals Hashcode";
    }

    @Override
    public String getDescription() {
        return "Equals() does not check null)";
    }
}
