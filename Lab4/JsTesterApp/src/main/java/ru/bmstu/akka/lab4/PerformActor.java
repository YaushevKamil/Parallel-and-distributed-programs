package ru.bmstu.akka.lab4;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class PerformActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(JsFunction.class, m -> {
                    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
                    engine.eval(script);
                    Invocable invocable = (Invocable) engine;
                    return 
                })
                .build();
    }
}
