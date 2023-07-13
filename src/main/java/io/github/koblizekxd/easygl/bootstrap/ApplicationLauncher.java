package io.github.koblizekxd.easygl.bootstrap;

import io.github.koblizekxd.easygl.Application;
import org.reflections.Reflections;

public final class ApplicationLauncher {
    private ApplicationLauncher() {}

    public static void main(String[] args) {
        Reflections rf = new Reflections();
        Class<?> app = rf.getSubTypesOf(Application.class).stream().findFirst()
                .orElseThrow(() -> new RuntimeException("No applications found!"));
    }
}
