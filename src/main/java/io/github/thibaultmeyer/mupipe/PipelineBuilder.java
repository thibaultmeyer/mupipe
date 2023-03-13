package io.github.thibaultmeyer.mupipe;

import io.github.thibaultmeyer.mupipe.sink.Sink;
import io.github.thibaultmeyer.mupipe.source.Source;
import io.github.thibaultmeyer.mupipe.task.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Pipeline builder.
 *
 * @since 1.0.0
 */
public final class PipelineBuilder {

    private final PipelineInitialData pipelineInitialData;

    /**
     * Build a new instance.
     *
     * @since 1.0.0
     */
    public PipelineBuilder() {

        this.pipelineInitialData = new PipelineInitialData();
    }

    /**
     * Adds a source.
     *
     * @param source Source to add
     * @param <R>    Type of elements returned by the source
     * @return Current pipeline builder instance
     * @since 1.0.0
     */
    public <R> PipelineBuilderStageSource<R> addSource(final Source<R> source) {

        this.pipelineInitialData.sourceList.add(source);
        return new PipelineBuilderStageSource<>(this.pipelineInitialData);
    }

    /**
     * Adds multiple sources.
     *
     * @param sourceCollection Sources to add
     * @param <R>              Type of elements returned by the source
     * @return Current pipeline builder instance
     * @since 1.0.0
     */
    public <R> PipelineBuilderStageSource<R> addSource(final Collection<Source<R>> sourceCollection) {

        this.pipelineInitialData.sourceList.addAll(sourceCollection);
        return new PipelineBuilderStageSource<>(this.pipelineInitialData);
    }

    /**
     * Indicates that no source is required while specifying the type of elements that will be processed.
     *
     * @param <R> Type of elements returned by the source
     * @return Current pipeline builder instance
     * @since 1.0.0
     */
    public <R> PipelineBuilderStageTask<R> noSource() {

        return new PipelineBuilderStageTask<>(this.pipelineInitialData);
    }

    /**
     * Pipeline builder: Source.
     *
     * @param <R> Type of elements returned by the source
     * @since 1.0.0
     */
    public static final class PipelineBuilderStageSource<R> extends PipelineBuilderStageTask<R> {

        /**
         * Build anew instance.
         *
         * @param pipelineInitialData Pipeline initial data
         * @since 1.0.0
         */
        public PipelineBuilderStageSource(final PipelineInitialData pipelineInitialData) {

            super(pipelineInitialData);
        }

        /**
         * Adds a source.
         *
         * @param source Source to add
         * @return Current pipeline builder instance
         * @since 1.0.0
         */
        public PipelineBuilderStageSource<R> addSource(final Source<R> source) {

            this.pipelineInitialData.sourceList.add(source);
            return new PipelineBuilderStageSource<>(this.pipelineInitialData);
        }

        /**
         * Adds multiple sources.
         *
         * @param sourceCollection Sources to add
         * @return Current pipeline builder instance
         * @since 1.0.0
         */
        public PipelineBuilderStageSource<R> addSource(final Collection<Source<R>> sourceCollection) {

            this.pipelineInitialData.sourceList.addAll(sourceCollection);
            return new PipelineBuilderStageSource<>(this.pipelineInitialData);
        }
    }

    /**
     * Pipeline builder: Task.
     *
     * @param <I> Input element type
     * @since 1.0.0
     */
    public static class PipelineBuilderStageTask<I> extends PipelineBuilderStageSink<I> {

        /**
         * Build anew instance.
         *
         * @param pipelineInitialData Pipeline initial data
         * @since 1.0.0
         */
        private PipelineBuilderStageTask(final PipelineInitialData pipelineInitialData) {

            super(pipelineInitialData);
        }

        /**
         * Adds a task.
         *
         * @param task Task to add
         * @param <O>  Output element type
         * @return Current pipeline builder instance
         * @since 1.0.0
         */
        public <O> PipelineBuilderStageTask<O> addTask(final Task<I, O> task) {

            this.pipelineInitialData.taskList.add(task);
            return new PipelineBuilderStageTask<>(this.pipelineInitialData);
        }
    }

    /**
     * Pipeline builder: Sink.
     *
     * @param <I> Input element type
     * @since 1.0.0
     */
    public static class PipelineBuilderStageSink<I> {

        final PipelineInitialData pipelineInitialData;

        /**
         * Build anew instance.
         *
         * @param pipelineInitialData Pipeline initial data
         * @since 1.0.0
         */
        private PipelineBuilderStageSink(final PipelineInitialData pipelineInitialData) {

            this.pipelineInitialData = pipelineInitialData;
        }

        /**
         * Adds a sink.
         *
         * @param sink Sink to add
         * @return Current pipeline builder instance
         * @since 1.0.0
         */
        public PipelineBuilderStageSink<I> addSink(final Sink<I> sink) {

            this.pipelineInitialData.sinkList.add(sink);
            return new PipelineBuilderStageSink<>(this.pipelineInitialData);
        }

        /**
         * Adds multiple sinks.
         *
         * @param sinkCollection Sinks to add
         * @return Current pipeline builder instance
         * @since 1.0.0
         */
        public PipelineBuilderStageSink<I> addSink(final Collection<Sink<I>> sinkCollection) {

            this.pipelineInitialData.sinkList.addAll(sinkCollection);
            return new PipelineBuilderStageSink<>(this.pipelineInitialData);
        }

        /**
         * Build the pipeline
         *
         * @return A pipeline
         * @since 1.0.0
         */
        public Pipeline build() {

            return new Pipeline(
                this.pipelineInitialData.sourceList,
                this.pipelineInitialData.taskList,
                this.pipelineInitialData.sinkList);
        }
    }

    /**
     * Pipeline initial data.
     *
     * @since 1.0.0
     */
    private static final class PipelineInitialData {

        public final List<Source<?>> sourceList;
        public final List<Task<?, ?>> taskList;
        public final List<Sink<?>> sinkList;

        /**
         * Build a new instance.
         *
         * @since 1.0.0
         */
        private PipelineInitialData() {

            this.sourceList = new ArrayList<>();
            this.taskList = new ArrayList<>();
            this.sinkList = new ArrayList<>();
        }
    }
}
