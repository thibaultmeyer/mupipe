package io.github.thibaultmeyer.mupipe.exception;

import io.github.thibaultmeyer.mupipe.sink.Sink;
import io.github.thibaultmeyer.mupipe.source.Source;

/**
 * All exceptions thrown by the pipeline are subclasses of {@code PipelineException}.
 *
 * @see PipelineException.CannotOpenSink
 * @see PipelineException.CannotOpenSource
 * @see PipelineException.SinkFailure
 * @see PipelineException.SourceFailure
 */
public class PipelineException extends RuntimeException {

    /**
     * Build a new instance.
     *
     * @param message The detail message
     * @param cause   The cause
     */
    protected PipelineException(final String message, final Exception cause) {

        super(message, cause);
    }

    /**
     * Exception indicates that something goes wrong during read from source process.
     */
    public static class SourceFailure extends PipelineException {

        /**
         * Build a new instance.
         *
         * @param cause The cause
         */
        public SourceFailure(final Exception cause) {

            super("Cannot read element from source", cause);
        }
    }

    /**
     * Exception indicates that sink process fail.
     */
    public static class SinkFailure extends PipelineException {

        /**
         * Build a new instance.
         *
         * @param cause The cause
         */
        public SinkFailure(final Exception cause) {

            super("Cannot process element on sink", cause);
        }
    }

    /**
     * Exception indicates that Source can't be opened.
     */
    public static class CannotOpenSource extends PipelineException {

        /**
         * Build a new instance.
         *
         * @param source The Source that could not be opened
         * @param cause  The cause
         */
        public CannotOpenSource(final Source<?> source, final Exception cause) {

            super("Cannot open Source '" + source.getClass().getSimpleName() + "'", cause);
        }
    }

    /**
     * Exception indicates that Sink can't be opened.
     */
    public static class CannotOpenSink extends PipelineException {

        /**
         * Build a new instance.
         *
         * @param sink  The Sink that could not be opened
         * @param cause The cause
         */
        public CannotOpenSink(final Sink<?> sink, final Exception cause) {

            super("Cannot open Sink '" + sink.getClass().getSimpleName() + "'", cause);
        }
    }
}
