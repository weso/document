# document 

This project contains code that was developed as part of the Scala standard library but was later deprecated. 
We maintain the code mainly because we used it in one of our projects and also as a small and simple test module that we can use to check continuous integration, documentation, cross-scala compilations, tests, and so on.

It consists os a basic pretty-printing library, 
 which was based on Lindig's strict version of Wadler's adaptation of Hughes' pretty-printer derived from 
 [Document.scala](https://github.com/scala/scala/blob/v2.11.8/src/library/scala/text/Document.scala) 
 whose author was Michel Schinz.

You can see the [web page](https://weso.github.io/document) for technical documentation.

[![Continuous Integration](https://github.com/weso/document/actions/workflows/ci.yml/badge.svg)](https://github.com/weso/document/actions/workflows/ci.yml)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/es.weso/document_3/badge.svg)](https://maven-badges.herokuapp.com/maven-central/es.weso/document_3)

[![codecov](https://codecov.io/gh/weso/document/branch/master/graph/badge.svg)](https://codecov.io/gh/weso/document)


## Further info

* [The design of a pretty-printing library](http://www.cse.chalmers.se/~rjmh/Papers/pretty.html) by John Hughes, describes the original pretty-printing library in Haskell, [PDF](http://belle.sourceforge.net/doc/hughes95design.pdf)
* [Strictly Pretty Paper](https://lindig.github.io/papers/strictly-pretty-2000.pdf) by Christian Lindig, describes a strict implementation of the pretty printing algorithm in OCaml

## Contribution

Contributions are greatly appreciated.
Please fork this repository and open a pull request to add more features or [submit issues](https://github.com/weso/document/issues)

## Publishing to OSS-Sonatype

This project uses [the sbt ci release](https://github.com/olafurpg/sbt-ci-release) plugin for publishing to [OSS Sonatype](https://oss.sonatype.org/).

##### SNAPSHOT Releases
Open a PR and merge it to watch the CI release a -SNAPSHOT version

##### Full Library Releases
1. Push a tag and watch the CI do a regular release
2. `git tag -a v0.1.0 -m "v0.1.0"`
3. `git push origin v0.1.0`
_Note that the tag version MUST start with v._

