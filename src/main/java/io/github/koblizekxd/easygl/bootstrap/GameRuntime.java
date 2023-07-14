package io.github.koblizekxd.easygl.bootstrap;

import io.github.koblizekxd.easygl.Application;
import io.github.koblizekxd.easygl.api.behaviour.scripts.Script;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public final class GameRuntime {
    private final Application app;
    private final Logger logger;
    private long window;

    GameRuntime(Application app) {
        this.app = app;
        this.logger = LogManager.getLogger("GameRuntime");
    }
    public void start() {
        logger.info("");
        logger.info("Using EasyGL");
        logger.info("Running on LWJGL {}", Version.getVersion());
        logger.info("");

        GLFWErrorCallback.createPrint(IoBuilder.forLogger(logger).buildPrintStream()).set();

        if (!glfwInit()) logger.fatal("Application has failed to start: GLFW initialization failed");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        window = glfwCreateWindow(300, 300, "Application", NULL, NULL);
        if (window == NULL)
            logger.fatal("Application has failed to start: Window initialization failed");

        // TODO: INPUT API

        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
        app.init();
        loop();
    }
    private void loop() {
        GL.createCapabilities();
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        while ( !glfwWindowShouldClose(window) ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glfwSwapBuffers(window);

            app.loop();
            for (Script script : app.getScriptHandler().getRunningScripts()) {
                if (!script.hasExecuted()) {
                    script.getBehaviour().init();
                    script.execute();
                }
                script.getBehaviour().loop();
            }

            glfwPollEvents();
        }
    }
}
