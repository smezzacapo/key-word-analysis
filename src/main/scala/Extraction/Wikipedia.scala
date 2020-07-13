import play.api.libs.json.Json
import play.api.libs.json.JsObject
import play.api.libs.json.JsValue

/** Handles all interactions with Wikipedia API */
class Wikipedia extends TextExtractor {

    /**
      * Query wikipedia and return summaries for
      * the provided input
      *
      * @param input - key word or phrase to search for
      * @param limit - number of search results to include
      * @return list of string Wikipedia page summaries
      */
    def extract(input: String, limit: String): List[SentimentResult] = {
        val allPageNames: List[String] = getMatchingPageNames(input, limit)
        val allPages: List[SentimentResult] = for (name <- allPageNames) yield {
            val result = new SentimentResult(name)
            result.fullPage = getPageByName(name)
            result
        }
        for (pageResult <- allPages) yield {
            pageResult.summary = getSummaryFromPage(pageResult.fullPage)
            pageResult
        }
    }

    /**
      * Extract the summary string from a wikipedia page
      *
      * @param page - Wikipedia page as json
      * @return - String page summary
      */
    private def getSummaryFromPage(page: JsValue): String = {
        val pagesField = (page \ Wikipedia.QueryField \ Wikipedia.PagesField)
        val pagesJson = pagesField.as[JsObject]
        val pageKeys = pagesJson.keys
        val resultSet = for (key <- pageKeys) yield {
            (pagesJson \ key \ Wikipedia.ExtractField).as[String]
        }
        resultSet.mkString("|")
    }

    /**
      * Return Wikipedia page named pageName
      *
      * @param pageName - String page name to search for
      * @return Wikipedia page as json
      */
    private def getPageByName(pageName: String): JsValue = {
        Json.parse(
            Wikipedia.ApiHelp.getFromUrlArgs(
                Array(
                    Wikipedia.Host, Wikipedia.SearchBasePath
                ),
                Some(Map(
                    Wikipedia.ActionParamKey -> Wikipedia.ActionQueryParamValue,
                    Wikipedia.PropParamKey -> Wikipedia.PropExtractsParamValue,
                    Wikipedia.FormatParamKey -> Wikipedia.FormatParamValue,
                    Wikipedia.ExIntroParamKey -> Wikipedia.ExIntroParamValue,
                    Wikipedia.TitlesParamKey -> pageName
                ))
            )
        )
    }

    /**
      * Get up to limit wikipedia pages that are matched
      * to input
      *
      * @param input - search word or phrase
      * @param limit - number of pages to return
      * @return list of string page names
      */
    private def getMatchingPageNames(input: String, limit: String): List[String] = {
        val matchingPageResponse: String = Wikipedia.ApiHelp.getFromUrlArgs(
            Array(
                Wikipedia.Host, Wikipedia.SearchBasePath
            ),
            Some(Map(
                Wikipedia.ActionParamKey -> Wikipedia.ActionSearchParamValue,
                Wikipedia.SearchParamKey -> input,
                Wikipedia.LimitParamKey -> limit,
                Wikipedia.NamespaceParamKey -> Wikipedia.NamespaceParamValue,
                Wikipedia.FormatParamKey -> Wikipedia.FormatParamValue
            ))
        )
        val currentJson = Json.parse(matchingPageResponse)
        currentJson(Wikipedia.ExtractedPageIndex).as[List[String]]
    }

    /** Return all content provided by random page wikipedia endpoint */
    private def getRandomPage(): String = {
        Wikipedia.ApiHelp.getFromUrlArgs(
            Array(
                Wikipedia.Host, Wikipedia.BasePath, Wikipedia.PagePath,
                Wikipedia.RandomPath, Wikipedia.SummaryPath
            ),
            None
        )
        
    }

    /** Return Extract Section Only provided by random page wikipedia endpoint */
    private def getRandomExtract(): String = {
        val fullPage: String = getRandomPage()
        val currentJson = Json.parse(fullPage)
        (currentJson \ Wikipedia.ExtractField).as[String]
    }
}

// Adding hardcoded string literals to companion object.
// TODO where is a better place for string literals such as these?
object Wikipedia {
    private val Host = "https://en.wikipedia.org"

    // URL fields - Random Pages
    private val BasePath = "api/rest_v1"
    private val PagePath = "page"
    private val RandomPath = "random"
    private val SummaryPath = "summary"

    // URL fields - Search
    private val SearchBasePath = "w/api.php"

    // Search Parameters
    val ActionParamKey = "action"
    val ActionSearchParamValue = "opensearch"
    val ActionQueryParamValue = "query"
    val SearchParamKey = "search"
    val LimitParamKey = "limit"
    val NamespaceParamKey = "namespace"
    val NamespaceParamValue = "0"
    val FormatParamKey = "format"
    val FormatParamValue = "json"
    val PropParamKey = "prop"
    val PropExtractsParamValue = "extracts"
    val ExIntroParamKey = "exintro"
    val ExIntroParamValue = ""
    val TitlesParamKey = "titles"

    // Json Blob Fields
    private val ExtractField = "extract"
    private val QueryField = "query"
    private val PagesField = "pages"
    private val ExtractedPageIndex = 1

    private val ApiHelp = new ApiHelper()
}