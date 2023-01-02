# μPipe

[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg?logo=github)](https://raw.githubusercontent.com/thibaultmeyer/mupipe/master/LICENSE)
[![Repository release](https://img.shields.io/github/v/release/thibaultmeyer/mupipe?logo=github)](https://github.com/thibaultmeyer/mupipe/releases)
[![Maven](https://img.shields.io/maven-central/v/io.github.thibaultmeyer/mupipe.svg?logo=apache-maven)](https://central.sonatype.com/artifact/io.github.thibaultmeyer/mupipe/1.0.0/versions)
[![Repository size](https://img.shields.io/github/repo-size/thibaultmeyer/mupipe.svg?logo=git)](https://github.com/thibaultmeyer/mupipe)

[![Java CI](https://img.shields.io/github/actions/workflow/status/thibaultmeyer/mupipe/build.yml?logo=github&color=%231da868&branch=master)](https://github.com/thibaultmeyer/mupipe/actions/workflows/build.yml)
[![CodeQL](https://img.shields.io/github/actions/workflow/status/thibaultmeyer/mupipe/codeql-analysis.yml?label=codeql&logo=github&color=%231da868&branch=master)](https://github.com/thibaultmeyer/mupipe/actions/workflows/codeql-analysis.yml)

Pipeline microframework for data processing
*****


## Microframework
μPipe (_/mjuː.paɪp/_) is a microframework for implementing data processing pipelines
simply and very quickly. Depending on the type of processing to be done, you want an
easy-to-use tool running in minutes, not a complex tool with dedicated hosting. That
is why this project was born.



## Build
To compile μPipe for Java, you must ensure that Java 11 (or above) and Maven are correctly
installed.

    #> mvn package
    #> mvn install

To speed up process, you can ignore unit tests by using: `-DskipTests=true -Dmaven.test.skip=true`.



## How to use

```xml
<dependency>
  <groupId>io.github.thibaultmeyer</groupId>
  <artifactId>mupipe</artifactId>
  <version>x.y.z</version>
</dependency>
```


```java
final Pipeline pipeline = Pipeline.newBuilder()
    .addSource(new CollectionSource<>(List.of(1, 2, 3, 4, 5, 6)))
    .addTask(new FilterTask<>((element) -> element % 2 == 0))
    .addSink(new PrintStreamOutputSink<>(System.out))
    .build();
```



## License
This project is released under terms of the [MIT license](https://raw.githubusercontent.com/thibaultmeyer/mupipe/master/LICENSE).
