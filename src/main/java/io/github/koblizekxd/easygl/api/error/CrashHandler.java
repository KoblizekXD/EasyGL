package io.github.koblizekxd.easygl.api.error;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Consumer;

public final class CrashHandler {

    private final Runnable runnable;
    private Consumer<Exception> exception;

    public static void crash(Class<?> responsibleClass, String reason, Throwable stacktrace) {
        Logger logger
                = LogManager.getLogger("CrashHandler/" + responsibleClass.getSimpleName());

        logger.error("");
        logger.error("An error occurred and lead to unexpected application behaviour!");
        logger.error("CrashHandler has collected following information:");
        logger.error("\tResponsible class: {}", responsibleClass.getSimpleName());
        logger.error("\tProbable cause: {}", reason);
        logger.error("\tClass stacktrace: ");
        for (StackTraceElement element : stacktrace.getStackTrace()) {
            logger.error("\t\t{}:{}[{}]",
                    element.getMethodName(),
                    element.getClassName(),
                    element.getLineNumber());
        }
        logger.error("*****");
        logger.error("");
        logger.fatal("Application crashed, therefore it will not continue to run");
    }
    public CrashHandler(Runnable runnable) {
        this.runnable = runnable;
    }
    public CrashHandler exception(Consumer<Exception> exception) {
        this.exception = exception;
        return this;
    }
    public void start() {
        try {
            runnable.run();
        } catch (Exception e) {
            exception.accept(e);
        }
    }
}
