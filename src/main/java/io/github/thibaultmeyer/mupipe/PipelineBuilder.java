package io.github.thibaultmeyer.mupipe;

import io.github.thibaultmeyer.mupipe.sink.Sink;
import io.github.thibaultmeyer.mupipe.source.Source;
import io.github.thibaultmeyer.mupipe.task.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * Pipeline builder.
 */
public class PipelineBuilder {

    private final PipelineInitialData pipelineInitialData;

    /**
     * Build a new instance.
     */
    public PipelineBuilder() {

        this.pipelineInitialData = new PipelineInitialData();
    }

    public <R> PipelineBuilderStageSource<R> addSource(final Source<R> source) {

        this.pipelineInitialData.sourceList.add(source);
        return new PipelineBuilderStageSource<>(this.pipelineInitialData);
    }

    public <I, O> PipelineBuilderStageTask<O> addTask(final Task<I, O> task) {

        this.pipelineInitialData.taskList.add(task);
        return new PipelineBuilderStageTask<>(this.pipelineInitialData);
    }

    public final static class PipelineBuilderStageSource<R> {

        private final PipelineInitialData pipelineInitialData;

        public PipelineBuilderStageSource(final PipelineInitialData pipelineInitialData) {
            this.pipelineInitialData = pipelineInitialData;
        }

        public PipelineBuilderStageSource<R> addSource(final Source<R> source) {

            this.pipelineInitialData.sourceList.add(source);
            return this;
        }

        public <O> PipelineBuilderStageTask<O> addTask(final Task<R, O> task) {

            this.pipelineInitialData.taskList.add(task);
            return new PipelineBuilderStageTask<>(this.pipelineInitialData);
        }

        public <W> PipelineBuilderStageSink<W> addSink(final Sink<W> sink) {

            this.pipelineInitialData.sinkList.add(sink);
            return new PipelineBuilderStageSink<>(this.pipelineInitialData);
        }
    }

    public final static class PipelineBuilderStageTask<I> {

        private final PipelineInitialData pipelineInitialData;

        public PipelineBuilderStageTask(final PipelineInitialData pipelineInitialData) {
            this.pipelineInitialData = pipelineInitialData;
        }

        public <R> PipelineBuilderStageTask<R> addTask(final Task<I, R> task) {

            this.pipelineInitialData.taskList.add(task);
            return new PipelineBuilderStageTask<>(this.pipelineInitialData);
        }

        public <W> PipelineBuilderStageSink<W> addSink(final Sink<W> sink) {

            this.pipelineInitialData.sinkList.add(sink);
            return new PipelineBuilderStageSink<>(this.pipelineInitialData);
        }
    }

    public final static class PipelineBuilderStageSink<W> {

        private final PipelineInitialData pipelineInitialData;

        public PipelineBuilderStageSink(final PipelineInitialData pipelineInitialData) {
            this.pipelineInitialData = pipelineInitialData;
        }

        public PipelineBuilderStageSink<W> addSink(final Sink<W> sink) {

            this.pipelineInitialData.sinkList.add(sink);
            return this;
        }

        public Pipeline build() {

            return new Pipeline(
                this.pipelineInitialData.sourceList,
                this.pipelineInitialData.taskList,
                this.pipelineInitialData.sinkList);
        }
    }

    /**
     * Pipeline initial data.
     */
    private final static class PipelineInitialData {

        public final List<Source<?>> sourceList;
        public final List<Task<?, ?>> taskList;
        public final List<Sink<?>> sinkList;

        /**
         * Build a new instance.
         */
        public PipelineInitialData() {

            this.sourceList = new ArrayList<>();
            this.taskList = new ArrayList<>();
            this.sinkList = new ArrayList<>();
        }
    }
}
