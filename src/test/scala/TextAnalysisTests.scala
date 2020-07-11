import org.scalatest.funsuite.AnyFunSuite

class TextAnalysisTests extends AnyFunSuite {
    test("A negative sentiment can be produced") {
        val text = "Everything is terrible and awful and boring."
        val helper = new TextAnalysis()
        val sentiment = helper.getTextSentiment(text)
        assert(sentiment == Sentiment.NEGATIVE)
    }
    test("A positive sentiment can be produced") {
        val text = "Everything is awesome and wonderful and fun!"
        val helper = new TextAnalysis()
        val sentiment = helper.getTextSentiment(text)
        assert(sentiment == Sentiment.POSITIVE)
    }
}