import scala.annotation.tailrec

final case class Lexer(val text: String, val position: Int = 0, val current: Option[Char] = None) {
  @inline
  final def advance: Lexer =
    Lexer(text, position + 1, text.length > position match {
      case true => Some(text.charAt(position))
      case false => None
    })
}

final object Lexer {
  @inline
  final def isNumber(c: Char): Boolean =
    c.isDigit || c == '.'
  
  final def getType(ctype: Char => Boolean, lexer: Lexer): (Lexer, String) = {
    val position = lexer.position
    @tailrec
    def get(lex: Lexer): Lexer = lex.current match {
      case Some(c) => if (ctype(c)) get(lex.advance) else lex
      case None => lex
    }
    val lex = get(lexer)
    (lex, lex.text.substring(position - 1, lex.position - 1))
  }

  final def getNumber(lexer: Lexer) = getType(isNumber, lexer)

  @tailrec
  final def nextToken(lexer: Lexer): (Lexer, Token) = lexer.current match {
    case Some(' ') | Some('\r') | Some('\t') | Some('\n') => nextToken(lexer.advance)
    case Some('+') => (lexer.advance, Token(TokenType.Plus))
    case Some('-') => (lexer.advance, Token(TokenType.Minus))
    case Some('*') => (lexer.advance, Token(TokenType.Multiply))
    case Some('/') => (lexer.advance, Token(TokenType.Divide))
    case Some('(') => (lexer.advance, Token(TokenType.Lparen))
    case Some(')') => (lexer.advance, Token(TokenType.Rparen))
    case Some(c: Char) =>
      if (isNumber(c)) {
        val (newLex, literal) = getNumber(lexer)
        literal.toDoubleOption match {
          case Some(result) => (newLex, Token(TokenType.Number, Some(result)))
          case None => throw CalculatorException("Parsing double/float/number error")
        }
      } else
        throw CalculatorException(s"Illegal character '$c'")
    case None => (lexer.advance, Token(TokenType.Eof))
  }

  final def generateTokens(lexer: Lexer): List[Token] = {
    @tailrec
    def gen(toks: List[Token], lex: Lexer): List[Token] = nextToken(lex) match {
      case (_, Token(TokenType.Eof, None)) => (Token(TokenType.Eof) :: toks).reverse
      case (l: Lexer, tok: Token) => gen(tok :: toks, l)
    }
    gen(List.empty[Token], lexer)
  } 

}
