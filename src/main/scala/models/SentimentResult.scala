import play.api.libs.json.JsValue

/** Contains output from extraction and analysis
 * TODO this is pretty ugly, think of a better way to keep track of results
 */
class SentimentResult(val pageName: String) {
    var fullPage: JsValue = null
    var summary: String = ""
    var sentimentCounts: Map[Sentiment.Value, Integer] = null
}