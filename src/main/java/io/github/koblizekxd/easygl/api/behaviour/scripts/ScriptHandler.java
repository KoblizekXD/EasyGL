package io.github.koblizekxd.easygl.api.behaviour.scripts;

import io.github.koblizekxd.easygl.api.annotations.Script;
import io.github.koblizekxd.easygl.api.behaviour.IBehaviour;

import java.util.*;

public class ScriptHandler {
    private final Map<String, IBehaviour> scripts;
    private final List<IBehaviour> runningScripts;

    public ScriptHandler() {
        scripts = new HashMap<>();
        runningScripts = new ArrayList<>();
    }

    public void collect(Set<Class<? extends IBehaviour>> types) {
        for (Class<? extends IBehaviour> type : types) {
            try {
                IBehaviour beh = type.newInstance();
                String name = type.getAnnotation(Script.class).value();
                scripts.put(name, beh);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    IBehaviour getScript(String name) {
        return scripts.get(name);
    }
    public void execute(String name) {
        runningScripts.add(getScript(name));
    }

    public List<IBehaviour> getRunningScripts() {
        return runningScripts;
    }
}
