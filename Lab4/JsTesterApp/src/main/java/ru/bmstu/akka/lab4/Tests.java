package ru.bmstu.akka.lab4;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "packageId",
        "expectedResult",
        "params"
})

public class Tests {
}
