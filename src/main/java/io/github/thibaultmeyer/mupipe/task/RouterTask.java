package io.github.thibaultmeyer.mupipe.task;

import io.github.thibaultmeyer.mupipe.Pipeline;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Routes items to another pipelines.
 *
 * @param <I> Input item type
 */
public class RouterTask<I> implements Task<I, Void> {

    final Function<I, Integer> pipelineDecisionMakerFunction;
    final List<PipelineMethodHandler> pipelineMethodHandlerList;

    /**
     * Build a new instance.
     *
     * @param pipelineCollection            Collection of pipelines
     * @param pipelineDecisionMakerFunction Function to determine the pipeline to be used
     */
    public RouterTask(final Collection<Pipeline> pipelineCollection,
                      final Function<I, Integer> pipelineDecisionMakerFunction) {

        this.pipelineDecisionMakerFunction = pipelineDecisionMakerFunction;
        this.pipelineMethodHandlerList = new ArrayList<>();

        for (final Pipeline pipeline : pipelineCollection) {

            final PipelineMethodHandler handler = this.retrievePipelineProceedOnItemMethod(pipeline);
            this.pipelineMethodHandlerList.add(handler);
        }
    }

    @Override
    public Void execute(final I item, final boolean isLastItemFromSource) throws Exception {

        final Integer idx = this.pipelineDecisionMakerFunction.apply(item);
        final PipelineMethodHandler handler = this.pipelineMethodHandlerList.get(idx);

        if (handler != null) {
            handler.method.invoke(handler.instance, item, isLastItemFromSource);
        }

        return null;
    }

    /**
     * Retrieves the private method "proceedOnItem".
     *
     * @param pipeline Pipeline
     * @return Method "proceedOnItem", otherwise, {@code null}
     */
    private PipelineMethodHandler retrievePipelineProceedOnItemMethod(final Pipeline pipeline) {

        for (final Method method : pipeline.getClass().getDeclaredMethods()) {
            if (method.getName().equals("proceedOnItem")) {
                method.setAccessible(true);
                return new PipelineMethodHandler(pipeline, method);
            }
        }

        return null;
    }

    /**
     * Pipeline Method handler.
     */
    private static final class PipelineMethodHandler {

        public final Pipeline instance;
        public final Method method;

        /**
         * Build a new instance.
         *
         * @param instance Pipeline instance
         * @param method   Pipeline method to call
         */
        public PipelineMethodHandler(final Pipeline instance,
                                     final Method method) {

            this.instance = instance;
            this.method = method;
        }
    }
}
