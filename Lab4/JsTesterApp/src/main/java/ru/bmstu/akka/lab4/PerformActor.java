package ru.bmstu.akka.lab4;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import ru.bmstu.akka.lab4.messages.StoreMessage;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.List;

import static ru.bmstu.akka.lab4.JsTesterApp.storeActor;

public class PerformActor extends AbstractActor {
    private static ActorRef
    private static String performScript(String functionName,
                                        String script,
                                        List<Object> params)
            throws ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(script);
        Invocable invocable = (Invocable) engine;
        return invocable.invokeFunction(functionName, params.toArray()).toString();
    }

    @Override
    public Receive createReceive() {
        return ReceiveBuilder
                .create()
                .match(JsFunction.class, m -> {
                    String packageId = m.getPackageId();
                    String expectedResult = m.getExpectedResult();
                    String description = "";
                    try {
                        String actualResult = PerformActor.performScript(
                                m.getFunctionName(),
                                m.getScript(),
                                m.getParams()
                            );
                        description = actualResult.equals(expectedResult) ? "Right: " : "Wrong: ";
                        description += "expected: " + expectedResult + ", actual: " + actualResult;
                    } catch (ScriptException e) {
                        description = "Error: ScriptException\n" + e.getMessage();
                    } catch (NoSuchMethodException e) {
                        description = "Error: NoSuchMethodException" + e.getMessage();
                    }
                    storeActor.tell(new StoreMessage(packageId, description), ActorRef.noSender());
                })
                .build();
    }
}
