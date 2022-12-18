package io.github.thibaultmeyer.mupipe.exception;

/**
 * All exceptions thrown by the pipeline are subclasses of {@code PipelineException}.
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
}
