package ru.bmstu.akka.lab4;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "packageId",
        "jsScript",
        "functionName",
        
})

public class Tests {
}
