object Sentiment extends Enumeration {
  type Sentiment = Value
  val POSITIVE = Value("POSITIVE")
  val NEGATIVE = Value("NEGATIVE")
  val NEUTRAL = Value("NEUTRAL")
  
  def toSentiment(sentiment: Int): Sentiment = sentiment match {
    case x if x < 2 => Sentiment.NEGATIVE
    case 2 => Sentiment.NEUTRAL
    case x if x > 2 => Sentiment.POSITIVE
  }
}