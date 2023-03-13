package io.github.thibaultmeyer.mupipe.task;

import io.github.thibaultmeyer.mupipe.Pipeline;
import io.github.thibaultmeyer.mupipe.datastore.DataStore;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Routes element to specific pipeline. Each pipeline is identified by a customizable name.
 *
 * @param <I> Input element type
 * @since 1.1.0
 */
public class NamedRouterTask<I> extends AbstractRouterTask<I> {

    final Function<I, String> pipelineDecisionMakerFunction;
    final Map<String, PipelineMethodHandler> pipelineMethodHandlerMap;

    /**
     * Build a new instance.
     *
     * @param pipelineMap                   Map of pipelines
     * @param pipelineDecisionMakerFunction Function to determine the pipeline to be used
     * @since 1.1.0
     */
    public NamedRouterTask(final Map<String, Pipeline> pipelineMap,
                           final Function<I, String> pipelineDecisionMakerFunction) {

        this.pipelineDecisionMakerFunction = pipelineDecisionMakerFunction;
        this.pipelineMethodHandlerMap = new HashMap<>();

        for (final Map.Entry<String, Pipeline> entry : pipelineMap.entrySet()) {

            final PipelineMethodHandler handler = this.retrievePipelineprocessCurrentElementMethod(entry.getValue());
            this.pipelineMethodHandlerMap.put(entry.getKey(), handler);
        }
    }

    @Override
    public Void execute(final I element, final DataStore dataStore, final boolean isLastElementFromSource) throws Exception {

        final String pipelineName = this.pipelineDecisionMakerFunction.apply(element);
        final PipelineMethodHandler handler = this.pipelineMethodHandlerMap.get(pipelineName);

        if (handler != null) {
            handler.method.invoke(handler.instance, element, dataStore, isLastElementFromSource);
        }

        return null;
    }
}
