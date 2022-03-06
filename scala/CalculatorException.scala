final case class CalculatorException(private val message: String = "", private val cause: Throwable = None.orNull) extends Exception(message, cause)
