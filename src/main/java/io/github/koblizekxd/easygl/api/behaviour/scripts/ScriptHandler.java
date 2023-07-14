package io.github.koblizekxd.easygl.api.behaviour.scripts;

import io.github.koblizekxd.easygl.api.annotations.Script;
import io.github.koblizekxd.easygl.api.behaviour.IBehaviour;

import java.util.*;

public class ScriptHandler {
    private final Map<String, io.github.koblizekxd.easygl.api.behaviour.scripts.Script> scripts;
    private final List<io.github.koblizekxd.easygl.api.behaviour.scripts.Script> runningScripts;

    public ScriptHandler() {
        scripts = new HashMap<>();
        runningScripts = new ArrayList<>();
    }

    public void collect(Set<Class<? extends IBehaviour>> types) {
        for (Class<? extends IBehaviour> type : types) {
            try {
                IBehaviour beh = type.newInstance();
                String name = type.getAnnotation(Script.class).value();
                scripts.put(name, new io.github.koblizekxd.easygl.api.behaviour.scripts.Script(beh));
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    io.github.koblizekxd.easygl.api.behaviour.scripts.Script getScript(String name) {
        return scripts.get(name);
    }
    public void execute(String name) {
        runningScripts.add(getScript(name));
    }
    public void kill(String name) {
        runningScripts.remove(getScript(name));
    }

    public List<io.github.koblizekxd.easygl.api.behaviour.scripts.Script> getRunningScripts() {
        return runningScripts;
    }
}
