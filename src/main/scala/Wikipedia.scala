import play.api.libs.json.Json

/** Handles all interactions with Wikipedia API */
class Wikipedia {

    //Generic GET request
    // TODO no brackets?
    @throws(classOf[java.io.IOException])
    private def get(url: String): String = {
        io.Source.fromURL(url).mkString
    }

    private def createUrl(urlArgs: Array[String]): String = {
        urlArgs.mkString("/")
    }

    /** Return all content provided by random page wikipedia endpoint */
    def getRandomPage(): String = {
        get(
            createUrl(
                Array(
                    Wikipedia.Host, Wikipedia.BasePath, Wikipedia.PagePath,
                    Wikipedia.RandomPath, Wikipedia.SummaryPath
                )
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
}