/**
 * Pipeline microframework for data processing.
 * <pre>{@code
 *  // Simple pipeline declaration and execution
 *  final Pipeline pipeline = Pipeline.newBuilder()
 *      .addSource(new CollectionSource<>(List.of("1", "2", "3", "4", "5", "6")))
 *      .addTask((element, dataStore, isLastElementFromSource) -> Integer.parseInt(element))
 *      .addTask(new FilterTask<>((element) -> element % 2 == 0))
 *      .addSink(new PrintStreamOutputSink<>(System.out))
 *      .build();
 *
 *  pipeline.execute();
 * }</pre>
 *
 * @since 1.0.0
 */
package io.github.thibaultmeyer.mupipe;
