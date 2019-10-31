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

    @JsonIgnore
    private Map<String, String> unexpectedProperties = new HashMap<>();
}
