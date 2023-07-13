package io.github.koblizekxd.easygl.bootstrap;

import io.github.koblizekxd.easygl.Application;
import io.github.koblizekxd.easygl.api.annotations.Script;
import io.github.koblizekxd.easygl.api.behaviour.IBehaviour;
import org.reflections.Reflections;

import java.util.Set;

public final class ApplicationLauncher {
    private ApplicationLauncher() {}

    public static void main(String[] args) {
        Reflections rf = new Reflections();
        Class<?> app = rf.getSubTypesOf(Application.class).stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No applications found!"));
        Set<Class<?>> types = rf.getTypesAnnotatedWith(Script.class);
        try {
            Application a = (Application) app.newInstance();
            a.getScriptHandler().collect((Set<Class<? extends IBehaviour>>) types);
            GameRuntime runtime = new GameRuntime(a);
            runtime.start();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
