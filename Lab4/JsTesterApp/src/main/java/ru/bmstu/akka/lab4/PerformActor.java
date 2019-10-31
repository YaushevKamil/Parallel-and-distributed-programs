package ru.bmstu.akka.lab4;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;

public class PerformActor extends AbstractActor {
    private static String performScript(String functionName,
                                        String script,
                                        List<String> params)
            throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(script);
        Invocable invocable = (Invocable) engine;
        return invocable.invokeFunction(functionName, params.toArray()).toString();
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder.create()
                .match(JsFunction.class, m -> {
                    String description;
                    try {
                        String result = PerformActor.performScript(m.getFunctionName(), m.getScript(), m.getParams());
                        
                    } catch (ScriptException e) {
                        description = "Error: ScriptException\n" + e.getMessage();
                    } catch (NoSuchMethodException e) {
                        description = "Error: NoSuchMethodException" + e.getMessage();
                    }

                    String result =
                    String output =
                })
                .build();
    }
}
