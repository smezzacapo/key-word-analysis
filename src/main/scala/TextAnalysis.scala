/** Cleans and performs analysis on provided text values 
 * 
 * This will include Sentiment Analysis and key word frequency
 * 
 * Sentiment Analysis modified from: https://github.com/shekhargulati/52-technologies-in-2016/blob/master/03-stanford-corenlp/README.md
*/
import java.util.Properties

import edu.stanford.nlp.ling.CoreAnnotations
import edu.stanford.nlp.util.CoreMap
import edu.stanford.nlp.trees.Tree
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations
import edu.stanford.nlp.pipeline.{Annotation, StanfordCoreNLP}
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations

import scala.collection.mutable.Buffer
import scala.collection.JavaConverters._


class TextAnalysis {

    val props = new Properties()
    props.setProperty("annotators", "tokenize, ssplit, parse, sentiment")
    val pipeline: StanfordCoreNLP = new StanfordCoreNLP(props)

    def getTextSentiment(input: String): Map[Sentiment.Value, Integer] = Option(input) match {
        case Some(text) if !text.isEmpty => extractSentiment(text)
        case _ => throw new IllegalArgumentException("Empty Input, No Text to Analyze!")
    }

    private def extractSentiment(text: String): Map[Sentiment.Value, Integer] = {
        // Sum up results, grouping by Sentiment.Value
        var m = Map[Sentiment.Value, Integer]()
        val allResults = extractSentiments(text)
        allResults.foreach {
            case (_, sentiment) => 
                val curCount: Integer = m.getOrElse(sentiment, 0) //TODO why does adding +1 here confuse compiler?
                val newCount = curCount+1
                m += sentiment -> newCount
        }
        m
    }

    private def extractSentiments(text: String): List[(String, Sentiment.Value)] = {
        val annotation: Annotation = pipeline.process(text)
        val sentences: java.util.List[CoreMap] = annotation.get(classOf[CoreAnnotations.SentencesAnnotation])
        val convertedSentences: Buffer[CoreMap] = sentences.asScala

        val sentenceToTree: Buffer[(CoreMap, Tree)] = convertedSentences.map(
            sentence => (sentence, sentence.get(classOf[SentimentCoreAnnotations.SentimentAnnotatedTree]))
        )

        sentenceToTree.map {
            case (sentence, tree) => (sentence.toString, Sentiment.toSentiment(
                RNNCoreAnnotations.getPredictedClass(tree)
            ))
        }.toList
    }
}