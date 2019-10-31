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
    @JsonProperty("testName")
    private String testName;

    @JsonProperty("expectedResult")
    private String expectedResult;

    @JsonProperty("params")
    private List<String> params = null;

    @JsonIgnore
    private Map<String, String> unexpectedProperties = new HashMap<>();
}
