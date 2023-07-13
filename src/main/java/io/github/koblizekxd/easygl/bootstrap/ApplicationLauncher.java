package io.github.koblizekxd.easygl.bootstrap;

import io.github.koblizekxd.easygl.Application;
import org.reflections.Reflections;

public final class ApplicationLauncher {
    private ApplicationLauncher() {}

    public static void main(String[] args) {
        Reflections rf = new Reflections();
        Class<?> app = rf.getSubTypesOf(Application.class).stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No applications found!"));
        try {
            Application a = (Application) app.newInstance();
            GameRuntime runtime = new GameRuntime(a);
            runtime.start();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
