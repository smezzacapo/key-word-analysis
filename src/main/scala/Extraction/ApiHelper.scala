/**
 * Helper class for making api calls
 */
class ApiHelper {

    @throws(classOf[java.io.IOException])
    def getFromUrl(url: String): String = {
        io.Source.fromURL(url).mkString
    }

    def getFromUrlArgs(urlArgs: Array[String]): String = {
        getFromUrl(
            createUrl(urlArgs)
        )
    }

    private def createUrl(urlArgs: Array[String]): String = {
        urlArgs.mkString("/")
    }
}