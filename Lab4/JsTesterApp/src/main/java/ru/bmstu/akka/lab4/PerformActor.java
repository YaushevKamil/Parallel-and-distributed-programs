package ru.bmstu.akka.lab4;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class PerformActor extends AbstractActor {
    private static String performScript(String functionName, String script, String... args)  {

    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(JsFunction.class, m -> {
                    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
                    engine.eval(m.getScript());
                    Invocable invocable = (Invocable) engine;
                    String result = invocable.invokeFunction(m.getFunctionName(), m.getParams().toArray()).toString();
                    String output =
                })
                .build();
    }
}
