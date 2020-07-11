import play.api.libs.json.Json

/** Handles all interactions with Wikipedia API */
class Wikipedia extends TextExtractor {

    def extract(input: String): String = {
        // TODO use input. For now just use random
        getRandomExtract()
    }

    /** Return all content provided by random page wikipedia endpoint */
    def getRandomPage(): String = {
        Wikipedia.ApiHelp.getFromUrlArgs(
            Array(
                Wikipedia.Host, Wikipedia.BasePath, Wikipedia.PagePath,
                Wikipedia.RandomPath, Wikipedia.SummaryPath
            )
        )
        
    }

    /** Return Extract Section Only provided by random page wikipedia endpoint */
    def getRandomExtract(): String = {
        val fullPage: String = getRandomPage()
        val currentJson = Json.parse(fullPage)
        (currentJson \ Wikipedia.ExtractField).as[String]
    }
}

// Adding hardcoded string literals to companion object.
// TODO where is a better place for string literals such as these?
object Wikipedia {
    // URL fields
    private val Host = "https://en.wikipedia.org"
    private val BasePath = "api/rest_v1"
    private val PagePath = "page"
    private val RandomPath = "random"
    private val SummaryPath = "summary"

    // Json Blob Fields
    private val ExtractField = "extract"

    private val ApiHelp = new ApiHelper()
}