package ru.bmstu.akka.lab4;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
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
    private List<Object> params = null;

    @JsonIgnore
    private Map<String, Object> unexpectedProperties = new HashMap<>();

    @JsonProperty("testName")
    public String getTestName() {
        return testName;
    }

    @JsonProperty("expectedResult")
    public String getExpectedResult() {
        return expectedResult;
    }

    @JsonProperty("params")
    public List<Object> getParams() {
        return params;
    }

    @JsonAnyGetter
    public Map<String, Object> getUnexpectedProperties() {
        return unexpectedProperties;
    }

    @JsonProperty("testName")
    public void setTestName(String testName) {
        this.testName = testName;
    }

    @JsonProperty("expectedResult")
    public void setExpectedResult(String expectedResult) {
        this.expectedResult = expectedResult;
    }

    @JsonProperty("params")
    public void setParams(List<String> params) {
        this.params = params;
    }

    @JsonAnySetter
    public void setUnexpectedProperties(String key, Object value) {
        unexpectedProperties.put(key, value);
    }
}