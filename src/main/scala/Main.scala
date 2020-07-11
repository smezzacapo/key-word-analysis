/** Query random wikipedia page and return Sentiment of the extract
*/
object Main extends App {
    println("Starting Application. Hello, World! Yet Again!")
    try {
        val wikiHelper = new Wikipedia()
        val currentExtract = wikiHelper.getRandomExtract()
        println("CURRENT EXTRACT: " + currentExtract)
        val textHelper = new TextAnalysis()
        val currentSentiment = textHelper.getTextSentiment(currentExtract)
        println("EXTRACT SENTIMENT: " + currentSentiment)
    }
    catch {
        case io: java.io.IOException => println("Failed to hit Wikipedia API: " + io)
        case il: IllegalArgumentException => println("Did not provide appropriate extract text: " + il)
        case e: Exception => println("Unanticipated Exception: " + e)
    }
    finally {
        println("Good Bye!")
    }
}
