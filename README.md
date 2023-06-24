# Text Analyzer

![vertx](https://img.shields.io/badge/vert.x-4.4.4-purple.svg)

This application exposes an endpoint which takes a text (not restricted only to 1 word) and compares it with an internal base of texts, returning the closest words both "by value" and "lexicographically".

* "By value" meaning the sum of the int values of every character.
* "Lexicographically" meaning by [Leveshtein Distance](https://en.wikipedia.org/wiki/Levenshtein_distance).

## Building

To launch the tests:
```
./gradlew clean test
```

To run the application:
```
./gradlew clean run
```

