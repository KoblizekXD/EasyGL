package io.github.koblizekxd.easygl;

import io.github.koblizekxd.easygl.api.annotations.Game;
import io.github.koblizekxd.easygl.api.behaviour.IBehaviour;
import io.github.koblizekxd.easygl.api.behaviour.scripts.ScriptHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class Application implements IBehaviour {
    private ScriptHandler handler;

    public ScriptHandler getScriptHandler() {
        if (handler == null) handler = new ScriptHandler();
        return handler;
    }

    public final Logger getLogger() {
        if (this.getClass().isAnnotationPresent(Game.class)) {
            Game annotation = this.getClass().getAnnotation(Game.class);
            return LogManager.getLogger(annotation.name());
        } else return LogManager.getLogger(this.getClass().getSimpleName());
    }
}
