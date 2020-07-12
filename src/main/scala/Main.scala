import java.awt.RenderingHints.Key
/** Query random wikipedia page and return Sentiment of the extract
*/
object Main extends App {
    println("Starting Application. Hello, World! Yet Again!")
    try {
        val input: KeyTextInput = new InputHelper().processInput(args)
        println("Processing the following key text: " + input.keyText)
        println("Using the following data source: " + input.dataSource)
        val extractionHelper = TextExtractor(input.dataSource)
        val extractedText = extractionHelper.extract(input.keyText, input.limit)
        val analysisHelper = new TextAnalysis()
        for (text <- extractedText) {
            Option(text) match {
                case Some(s) if !s.isEmpty =>
                    val currentSentiment = analysisHelper.getTextSentiment(s)
                    println("EXTRACT SENTIMENT: " + currentSentiment)
                case _ => println("Empty text summary found, skipping.")
            }
        }
    }
    catch {
        case io: java.io.IOException => println("Failed to hit API to extract text: " + io)
        case il: IllegalArgumentException => println("Did not provide appropriate argument: " + il)
        case e: Exception => println("Unanticipated Exception: " + e)
    }
    finally {
        println("Good Bye!")
    }
}
