/** Query random wikipedia page and return Sentiment of the extract
*/
object Main extends App {
    println("Starting Application. Hello, World! Yet Again!")
    try {
        val textExtract = TextExtractor("wikipedia")
        val currentText = textExtract.extract("todoMyKeyWord")
        println("CURRENT TEXT: " + currentText)
        val textHelper = new TextAnalysis()
        val currentSentiment = textHelper.getTextSentiment(currentText)
        println("EXTRACT SENTIMENT: " + currentSentiment)
    }
    catch {
        case io: java.io.IOException => println("Failed to hit API to extract text: " + io)
        case il: IllegalArgumentException => println("Did not provide appropriate extract text: " + il)
        case e: Exception => println("Unanticipated Exception: " + e)
    }
    finally {
        println("Good Bye!")
    }
}
