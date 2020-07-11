/**
 * Extract text associated with user provided key word(s).
 * Each unique data source (wikipedia, reddit, etc) is a private class
 * Factory Pattern
 */
trait TextExtractor {
    def extract(input: String): String
}

object TextExtractor {
    def apply(s: String):TextExtractor = {
        val cleanString = s.trim().toLowerCase()
        if (cleanString == "wikipedia") return new Wikipedia
        if (cleanString == "reddit") throw new NotImplementedError("Reddit is not implemented")
        else throw new IllegalArgumentException(cleanString + " is not a valid TextExtractor!")
  }
}