TODO:

Just rough notes for now, to be cleaned up after the initial project structure has taken form. 

Backend:

- SQS queue input
    - Text to match, number of articles to grab extract from, isRandom optional
    - Update main to read from SQS and iterate over N messages concurrently
- Postgres RDS output
    - Table: Text key words to Sentiment
    - Table: Text key words to full extract
    - Concept of run_id to compare the same text key word across runs for changes over time?

- Add unique word counter to TextAnalysis
    - Store unique word counts by Text Key Word in RDS table
- Other 'api' based classes such as Reddit etc. Write an interface, go back to Wikipedia and extend it

------------

Orchestration:

- Scala dockerFile
- Create DAGFile using Docker Operator and run scala process every N minutes
    - SQS sensor instead of set interval?

------------

Frontend / API

- React UI
    - Page for adding 1 or more Key Words
    - Page for viewing results by searching 1 or more existing Key Words (autocomplete)
    - Different websites - configurable. Wikipedia by default but also Reddit etc.
    - Some sort of report generation / simplistic segmentation? AKA in last N days what are top N words associated with positive sentiments?

- .net Core API
    - Writes user provided new Key Words to SQS to be processed
    - Reads from Postgres RDS to return content to UI
        - WHERE should the DAL actually live? .net core API that is called by scala/other code? Prefer not to have two separate code bases reading/writing from there.
    


    ------------------------------


    SCALA UPDATES

    1. Initial commit to github **
    1. Fix package naming **
    2. Interface for 'analysis getters or whatever' wikipedia/reddit **
    3. Update wikipedia to take input and grab N results
    4. TextAnalysis should be the 'api' hitter. Have a fake method for now that returns json for what to do
        -Rename to TextAnalysisApiHelper?
    5. Reddit version
    6. Where to move sentiment? SentimentAnalysis.scala? 
    7. Generic CRUD operation helper class?
    8. Output to TextAnalysisAPIHelper


    For now ONLY SENTIMENTS