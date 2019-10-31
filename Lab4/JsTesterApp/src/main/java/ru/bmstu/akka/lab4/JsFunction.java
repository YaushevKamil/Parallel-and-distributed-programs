package ru.bmstu.akka.lab4;

import java.util.List;

public class JsFunction {
    private String packageId;
    private String functionName;
    private String script;
    private List<Object> params;
    private String expectedResult;

    public JsFunction(String packageId,
                      String functionName, String script, List<Object> params,
                      String expectedResult) {
        this.packageId = packageId;
        this.functionName = functionName;
        this.script = script;
        this.params = params;
        this.expectedResult = expectedResult;
    }

    public String getPackageId() {
        return packageId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getScript() {
        return script;
    }

    public List<Object> getParams() {
        return params;
    }

    public String getExpectedResult() {
        return expectedResult;
    }
}
