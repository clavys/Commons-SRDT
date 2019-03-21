Atlanmod Commons
===
[![Build Status](https://travis-ci.org/atlanmod/Commons.svg?branch=master)](https://travis-ci.org/atlanmod/Commons)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.atlanmod.commons/commons-core/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.atlanmod.commons/commons-core)
[![CodeCov](https://codecov.io/gh/atlanmod/Commons/branch/master/graph/badge.svg)](https://codecov.io/gh/atlanmod/Commons/branch/master)
[![Codacy](https://api.codacy.com/project/badge/Grade/d5df667a5b264f9e95ad0095719b7d6a)](https://www.codacy.com/app/atlanmod/Commons?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=atlanmod/Commons&amp;utm_campaign=Badge_Grade)
[![Javadoc](https://img.shields.io/badge/javadoc--blue.svg)](https://atlanmod.github.io/Commons/releases/latest/doc/)
[![Licence](https://img.shields.io/badge/licence-EPL--2.0-blue.svg)](https://www.eclipse.org/legal/epl-2.0/)

This library is a set of common classes and methods, including:
-   Lazy objects and references for on-demand loading, and local caching
-   An asynchronous logger
-   A wrapper for caching, using [Caffeine][caffeine-home]
-   A wrapper for hashing, using [Zero-allocation Hashing][zah-home]
-   A wrapper for serialization, using [FST][fst-home]
-   Utility classes about primitives, collections, functional interfaces, concurrency, reflection, etc.

It was created to regroup and share classes, methods and dependencies between the different projects of the Atlanmod team, and was designed to ease updating without interfering with other projects.

Some of methods are inspired by [Guava][guava-home].

## Latest release

The most recent release is Atlanmod Commons 1.0.4, released March 21, 2019.
-   Javadoc: [commons][release-doc]

To add a dependency on this library using Maven, use the following:
```xml
<dependencies>
  <dependency>
    <groupId>org.atlanmod.commons</groupId>
    <artifactId>commons-core</artifactId>
    <version>1.0.4</version>
  </dependency>
</dependencies>
```


## Snapshots

Snapshots are automatically build from the `master` and are available throught Maven using `1.0.5-SNAPSHOT`.
-   Javadoc: [commons][snapshot-doc]


[release-doc]: https://atlanmod.github.io/Commons/releases/latest/doc/
[snapshot-doc]: https://atlanmod.github.io/Commons/releases/snapshot/doc/

[guava-home]: https://github.com/google/guava
[caffeine-home]: https://github.com/ben-manes/caffeine
[zah-home]: https://github.com/OpenHFT/Zero-Allocation-Hashing
[fst-home]: https://github.com/RuedigerMoeller/fast-serialization
