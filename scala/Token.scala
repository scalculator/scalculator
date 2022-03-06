final object TokenType extends Enumeration {
  type TokenType = Value

  val Number, Plus, Minus, Multiply, Divide, Lparen, Rparen, Eof = Value
}

final case class Token(val typ: TokenType.TokenType, val value: Option[Double] = None) {
  override def toString: String =
    if (value.isDefined)
      s"$typ:$value"
    else
      typ.toString
  
  final def op: Char = typ match {
    case TokenType.Plus => '+'
    case TokenType.Minus => '-'
    case TokenType.Multiply => '*'
    case TokenType.Divide => '/'
    case _ => throw CalculatorException("Invalid token for 'final def op: Char' member")
  }
}
