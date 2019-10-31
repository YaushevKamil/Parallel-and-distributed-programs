package ru.bmstu.akka.lab4;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class PerformActor extends AbstractActor {
    private static String performScript(ScriptEngine engine,
                                        String functionName,
                                        String script,
                                        String... args)
            throws ScriptException, NoSuchMethodException {
        engine.eval(script);
        Invocable invocable = (Invocable) engine;
        invocable.invokeFunction(m.getFunctionName(), m.getParams().toArray()).toString();
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(JsFunction.class, m -> {
                    ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

                    String result =
                    String output =
                })
                .build();
    }
}
