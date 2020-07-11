/** Cleans and performs analysis on provided text values 
 * 
 * This will include Sentiment Analysis and key word frequency
 * 
 * Sentiment Analysis from: https://github.com/shekhargulati/52-technologies-in-2016/blob/master/03-stanford-corenlp/README.md
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

    def getTextSentiment(input: String): Sentiment.Value = Option(input) match {
        case Some(text) if !text.isEmpty => new SentimentAnalyzer().extractSentiment(text)
        case _ => throw new IllegalArgumentException("Empty Input, No Text to Analyze!")
    }

}


class SentimentAnalyzer {

    val props = new Properties()
    props.setProperty("annotators", "tokenize, ssplit, parse, sentiment")
    val pipeline: StanfordCoreNLP = new StanfordCoreNLP(props)

    def extractSentiment(text: String): Sentiment.Value = {
      // If text has multiple sentences, return sentiment for longest sentence
      val (_, sentiment) = extractSentiments(text)
        .maxBy { case (sentence, _) => sentence.length }
      sentiment
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