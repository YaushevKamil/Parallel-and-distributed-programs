package ru.bmstu.akka.lab4;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "testName",
        "expectedResult",
        "params"
})

public class Test {
    @JsonProperty("testName") private String testName;
    @JsonProperty("expectedResult") private String expectedResult;
    @JsonProperty("params") private List<String> params;
    @JsonIgnore 

}
