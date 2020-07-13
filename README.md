This is a simple project to help refamiliarize myself with Scala.

For a user-provided word or phrase, query Wikipedia for up to N related pages.
For each related page, perform sentiment analysis on the summary using StanfordCoreNLP (https://stanfordnlp.github.io/CoreNLP/).
Print the total number of positive vs neutral vs negative sentences.


Requirements:
Scala: 2.13.1
SBT: 1.3.12

run --help 
Lists all available command line arguments

NOTE: All user provided phrases should include underscores between words.

Examples:

1)
run --kt=Barack_Obama --ds=wikipedia --l=5

PAGE NAME: Barack Obama
RESULTS: Map(NEGATIVE -> 25, POSITIVE -> 1, NEUTRAL -> 1)
PAGE NAME: Barack Obama citizenship conspiracy theories
RESULTS: Map(NEGATIVE -> 12, NEUTRAL -> 2)
PAGE NAME: Barack Obama 2008 presidential campaign
RESULTS: Map(NEGATIVE -> 7, NEUTRAL -> 1)
PAGE NAME: Barack Obama Sr.
RESULTS: Map(NEGATIVE -> 13, POSITIVE -> 1, NEUTRAL -> 3)
PAGE NAME: Barack Obama Supreme Court candidates
RESULTS: Map(NEGATIVE -> 10, NEUTRAL -> 2)



2)
run --kt=Donald_Trump --ds=wikipedia --l=5

PAGE NAME: Donald Trump
RESULTS: Map(NEGATIVE -> 24, POSITIVE -> 3, NEUTRAL -> 2)
PAGE NAME: Donald Trump Jr.
RESULTS: Map(NEGATIVE -> 9, NEUTRAL -> 1)
PAGE NAME: Donald Trump sexual misconduct allegations
RESULTS: Map(NEGATIVE -> 16, NEUTRAL -> 1)
PAGE NAME: Donald Trump 2016 presidential campaign
RESULTS: Map(NEGATIVE -> 17, NEUTRAL -> 1)
PAGE NAME: Donald Trump on social media
RESULTS: Map(NEGATIVE -> 11, NEUTRAL -> 2, POSITIVE -> 1)




TODO:
1. Fix broken tests and increase coverage to >70%. 
2. Replace println with actual logging support.
    Maybe: http://software.clapper.org/grizzled-slf4j/index.html
3. Add pre-commit.
4. Add DockerFile. 
5. Implement a Reddit TextExtractor.
6. Write results to a postgres DB (RDS AWS free tier?) - this will allow for analysis of changing sentiment over time.
7. Github Actions