package io.github.thibaultmeyer.mupipe.task;

import io.github.thibaultmeyer.mupipe.Pipeline;
import io.github.thibaultmeyer.mupipe.datastore.DataStore;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Routes element to specific pipeline.
 *
 * @param <I> Input element type
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

            final PipelineMethodHandler handler = this.retrievePipelineprocessCurrentElementMethod(pipeline);
            this.pipelineMethodHandlerList.add(handler);
        }
    }

    @Override
    public Void execute(final I element, final DataStore dataStore, final boolean isLastElementFromSource) throws Exception {

        final Integer idx = this.pipelineDecisionMakerFunction.apply(element);
        final PipelineMethodHandler handler = this.pipelineMethodHandlerList.get(idx);

        if (handler != null) {
            handler.method.invoke(handler.instance, element, dataStore, isLastElementFromSource);
        }

        return null;
    }

    /**
     * Retrieves the private method "processCurrentElement".
     *
     * @param pipeline Pipeline
     * @return Method "processCurrentElement", otherwise, {@code null}
     */
    private PipelineMethodHandler retrievePipelineprocessCurrentElementMethod(final Pipeline pipeline) {

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
