import java.net.URLEncoder
/**
 * Helper class for making api calls
 */
class ApiHelper {

    @throws(classOf[java.io.IOException])
    def getFromUrl(url: String): String = {
        println("MY URL: " + url)
        io.Source.fromURL(url).mkString
    }

    def getFromUrlArgs(urlArgs: Array[String], params: Option[Map[String, String]]): String = {
        val baseUrl = urlArgs.mkString("/")
        params match {
            case None => getFromUrl(baseUrl)
            case Some(param) => getFromUrl(baseUrl + "?" + param.map {
                case (k, v) => k + "=" + URLEncoder.encode(v, "UTF-8")
            }.mkString("&"))
        }
    }
}