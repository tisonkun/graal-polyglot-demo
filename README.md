## Prerequisite

1. [GraalVM](https://github.com/oracle/graal)
2. [TruffleRuby](https://github.com/oracle/truffleruby) - should be bundled with graalvm
3. [FastR](https://github.com/oracle/fastr) - install with `gu`

```
$ R
> install.packages(ggplot2)
> install.packages(dplyr)
```

```
$ gem install git
```

## Build

```
$ mvn clean package
```

## Usage

```
$ java -cp target/polyglot-0.1-SNAPSHOT.jar io.tison.polyglot.GitPlot --since <git-since-format> --repo <repo-output-path> --output <your-output-path>
```

then a png file should be generated as `<your-output-path>`.

