package ru.bmstu.akka.lab4;

import com.fasterxml.jackson.annotation.*;

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

    @JsonAnyGetter
    public Map<String, String> getAdditionalProperties() {
        return additionalProperties;
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

}
