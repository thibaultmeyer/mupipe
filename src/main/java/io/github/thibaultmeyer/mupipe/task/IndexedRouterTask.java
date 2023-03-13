package io.github.thibaultmeyer.mupipe.task;

import io.github.thibaultmeyer.mupipe.Pipeline;
import io.github.thibaultmeyer.mupipe.datastore.DataStore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

/**
 * Routes element to specific pipeline. Each pipeline is identified by a numeric value from 0 to n.
 *
 * @param <I> Input element type
 * @since 1.1.0
 */
public class IndexedRouterTask<I> extends AbstractRouterTask<I> {

    final Function<I, Integer> pipelineDecisionMakerFunction;
    final List<PipelineMethodHandler> pipelineMethodHandlerList;

    /**
     * Build a new instance.
     *
     * @param pipelineCollection            Collection of pipelines
     * @param pipelineDecisionMakerFunction Function to determine the pipeline to be used
     * @since 1.0.0
     */
    public IndexedRouterTask(final Collection<Pipeline> pipelineCollection,
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
}
