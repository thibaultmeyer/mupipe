# μPipe

[![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg?logo=github)](https://raw.githubusercontent.com/thibaultmeyer/mupipe/master/LICENSE)
[![Repository release](https://img.shields.io/github/v/release/thibaultmeyer/mupipe?logo=github)](https://github.com/thibaultmeyer/mupipe/releases)
[![Maven](https://img.shields.io/maven-central/v/io.github.thibaultmeyer/mupipe.svg?logo=apache-maven)](https://search.maven.org/artifact/io.github.thibaultmeyer/mupipe)
[![Repository size](https://img.shields.io/github/repo-size/thibaultmeyer/mupipe.svg?logo=git)](https://github.com/thibaultmeyer/mupipe)

[![Java CI](https://img.shields.io/github/actions/workflow/status/thibaultmeyer/mupipe/build.yml?logo=github&color=%231da868&branch=master)](https://github.com/thibaultmeyer/mupipe/actions/workflows/build.yml)
[![CodeQL](https://img.shields.io/github/actions/workflow/status/thibaultmeyer/mupipe/codeql-analysis.yml?label=codeql&logo=github&color=%231da868&branch=master)](https://github.com/thibaultmeyer/mupipe/actions/workflows/codeql-analysis.yml)

Pipeline microframework for data processing
*****


## Microframework
μPipe (_/mjuː.paɪp/_) is a microframework for implementing data processing pipelines
simply and very quickly. Depending on the type of processing to be done, it is a pity
to waste time learning to use tools that are too complex, only to end up using 1% of
the available functionalities. That is why this project was born.



## Build
To compile μPipe for Java, you must ensure that Java 11 (or above) and Maven are correctly
installed.

    #> mvn package
    #> mvn install

To speed up process, you can ignore unit tests by using: `-DskipTests=true -Dmaven.test.skip=true`.



## License
This project is released under terms of the [MIT license](https://raw.githubusercontent.com/thibaultmeyer/mupipe/master/LICENSE).
