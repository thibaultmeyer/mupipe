package io.github.thibaultmeyer.mupipe.task;

import io.github.thibaultmeyer.mupipe.Pipeline;

import java.lang.reflect.Method;

/**
 * Common methods used by router type task.
 *
 * @param <I> Input element type
 * @since 1.1.0
 */
abstract class AbstractRouterTask<I> implements Task<I, Void> {

    /**
     * Retrieves the private method "processCurrentElement".
     *
     * @param pipeline Pipeline
     * @return Method "processCurrentElement", otherwise, {@code null}
     * @since 1.1.0
     */
    protected PipelineMethodHandler retrievePipelineprocessCurrentElementMethod(final Pipeline pipeline) {

        for (final Method method : pipeline.getClass().getDeclaredMethods()) {
            if (method.getName().equals("handleElement")) {
                method.setAccessible(true);
                return new PipelineMethodHandler(pipeline, method);
            }
        }

        return null;
    }

    /**
     * Pipeline Method handler.
     *
     * @since 1.1.0
     */
    protected static final class PipelineMethodHandler {

        public final Pipeline instance;
        public final Method method;

        /**
         * Build a new instance.
         *
         * @param instance Pipeline instance
         * @param method   Pipeline method to call
         * @since 1.1.0
         */
        public PipelineMethodHandler(final Pipeline instance,
                                     final Method method) {

            this.instance = instance;
            this.method = method;
        }
    }
}
