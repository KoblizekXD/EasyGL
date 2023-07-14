package io.github.koblizekxd.easygl.api.behaviour.scripts;

import io.github.koblizekxd.easygl.api.behaviour.IBehaviour;

public class Script {
    private final IBehaviour behaviour;

    public Script(IBehaviour behaviour) {
        this.behaviour = behaviour;
    }

    public IBehaviour getBehaviour() {
        return behaviour;
    }

    private boolean hasExecuted = false;
    public boolean hasExecuted() {
        return hasExecuted;
    }
    public void execute() {
        hasExecuted = true;
    }
}
