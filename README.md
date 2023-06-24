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

## Usage
```shell
curl --location 'localhost:8080/analyze' \
--header 'Content-Type: application/json' \
--data '{
    "text": "69F75A67-3CCB-4018-8905-4F280AE65574"
}'
```

## Approach
- There is a file that acts as a repository of the stored texts. Each text is delimited by a line break.
- At startup, the application loads all the texts from the file, by chunks, and stores it into an in-memory cache. The cache is implemented internally as a ConcurrentHashSet.
- Every time a request comes with a text, the text is compared with all the texts from the cache, and lastly it's written into the cache.
- There is a periodic tasks that takes all the contents from the cache and write it into the file.
