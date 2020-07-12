import com.concurrentthought.cla._

class InputHelper {

    def processInput(args: Array[String]): KeyTextInput = {
        val keyText = "keyText"
        val dataSource = "dataSource"
        val limit = "limit"

        val keyTextArg  = Opt.string(
            name     = keyText,
            flags    = Seq("--keyText", "--kt"),
            help     = "The text to perform analysis on."
        )
        val dataSourceArg = Opt.string(
            name     = dataSource,
            flags    = Seq("--dataSource", "--ds"),
            help     = "Where to extract data related to keyText from."
        )
        val limitArg = Opt.string(
            name     = limit,
            flags    = Seq("--limit", "--l"),
            help     = "Max results to return from the user provided dataSource."
        )

        val initialArgs = Args(
            Seq(keyTextArg, dataSourceArg, limitArg)
        )
        val finalArgs: Args = initialArgs.process(args)
        new KeyTextInput(
            finalArgs.get(keyText).getOrElse(throw new IllegalArgumentException("No Key Text Provided")),
            finalArgs.get(dataSource).getOrElse(throw new IllegalArgumentException("No Data Source Provided")),
            finalArgs.get(limit).getOrElse(throw new IllegalArgumentException("No Limit Provided"))
        )
    }
}