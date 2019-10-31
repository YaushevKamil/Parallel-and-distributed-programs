package ru.bmstu.akka.lab4;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "testName",
        "expectedResult",
        "params"
})

public class Test {
    @JsonProperty("testName")
    private String testName;

    @JsonProperty("expectedResult")
    private String expectedResult;

    @JsonProperty("params")
    private List<String> params = null;

    @JsonIgnore
    private Map<String, String> additionalProperties;

    @JsonProperty("testName")
    public String getTestName() {
        return testName;
    }

    @JsonProperty("expectedResult")
    public String getExpectedResult() {
        return expectedResult;
    }

    @JsonProperty("params")
    public List<String> getParams() {
        return params;
    }

    @JsonProperty("testName")
    public  setTestName(String testName) {
        return testName;
    }

    @JsonProperty("expectedResult")
    public String setExpectedResult() {
        return expectedResult;
    }

    @JsonProperty("params")
    public List<String> setParams() {
        return params;
    }
}
