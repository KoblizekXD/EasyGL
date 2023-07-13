package io.github.koblizekxd.easygl;

import io.github.koblizekxd.easygl.api.annotations.Game;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Application {
    public final Logger getLogger() {
        if (this.getClass().isAnnotationPresent(Game.class)) {
            Game annotation = this.getClass().getAnnotation(Game.class);
            return LogManager.getLogger(annotation.name());
        } else return LogManager.getLogger(this.getClass().getSimpleName());
    }

    public void init() {}
    public void loop() {}
}
