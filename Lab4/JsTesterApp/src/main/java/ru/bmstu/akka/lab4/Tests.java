package ru.bmstu.akka.lab4;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "packageId",
        "jsScript",
        "functionName",
        "tests"
})

public class Tests {
    @JsonProperty("packageId")
    private String packageId;

    @JsonProperty("jsScript")
    private String jsScript;

    @JsonProperty("functionName")
    private String functionName;

    @JsonProperty("tests")
    private List<Test> tests = null;

    @JsonIgnore
    private Map<String, Object> unexpectedProperties = new HashMap<>();

    @JsonProperty("packageId")
    public String getPackageId() {
        return packageId;
    }

    @JsonProperty("jsScript")
    public String getJsScript() {
        return jsScript;
    }

    @JsonProperty("functionName")
    public String getFunctionName() {
        return functionName;
    }

    @JsonProperty("tests")
    public List<Test> getTests() {
        return tests;
    }

    @JsonAnyGetter
    public Map<String, Object> getUnexpectedProperties() {
        return unexpectedProperties;
    }

    @JsonProperty("packageId")
    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    @JsonProperty("jsScript")
    public void setJsScript(String jsScript) {
        this.jsScript = jsScript;
    }

    @JsonProperty("functionName")
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    @JsonProperty("tests")
    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    @JsonAnySetter
    public void setUnexpectedProperties(String key, Object value) {
        unexpectedProperties.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder testsString = new StringBuilder();
        for (Test test : tests) {
            testsString.append("\n\t").append(test.toString()).append("\n");
        }
        return "{ PackageId: " + packageId +
                ", name: " + functionName +
                "\nScript:\n" + jsScript +
                "tests: " + testsString + " }";
    }
}
