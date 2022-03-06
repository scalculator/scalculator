import scala.annotation.tailrec
final object Parser {
  final def parse(tokens: List[Token]): Node = tokens.head match {
    case Token(TokenType.Eof, None) => NullNode()
    case _ =>
      val (tks, result) = expr(tokens)
      tks.headOption match {
        case Some(head) => if (head != Token(TokenType.Eof)) throw CalculatorException("Invalid syntax")
        case None => throw CalculatorException("Invalid syntax")
      }
      result
  }

  final private def expr(tokens: List[Token]): (List[Token], Node) = {
    val (toks, result) = term(tokens)
    toks match {
      case Token(TokenType.Plus, None) :: tail =>
        val (tks, res) = term(tail)
        (tks, BinOpNode(Token(TokenType.Plus), result, res))
      case Token(TokenType.Minus, None) :: tail =>
        val (tks, res) = term(tail)
        (tks, BinOpNode(Token(TokenType.Minus), result, res))
      case _ => (toks, result) // This case is not guaranteed to happen
    }
  }

  final private def term(tokens: List[Token]): (List[Token], Node) = {
    val (toks, result) = factor(tokens)
    toks match {
      case Token(TokenType.Multiply, None) :: tail =>
        val (tks, res) = factor(tail)
        (tks, BinOpNode(Token(TokenType.Multiply), result, res))
      case Token(TokenType.Divide, None) :: tail =>
        val (tks, res) = factor(tail)
        (tks, BinOpNode(Token(TokenType.Divide), result, res))
      case _ => (toks, result) // This case is not guaranteed to happen
    }
  }

  final private def factor(tokens: List[Token]): (List[Token], Node) = tokens match {
    case head :: tail => head match {
      case Token(TokenType.Lparen, None) =>
        val (toks, result) = expr(tail)
        if (toks.head == Token(TokenType.Rparen))
          (toks.tail, result)
        else
          throw CalculatorException("Invalid syntax")
      case Token(TokenType.Number, x: Option[Double]) =>
        (tail, NumberNode(x.get))
      case Token(TokenType.Plus, None) =>
        val (toks, tree) = factor(tail)
        (toks, UnaryOpNode(Token(TokenType.Plus), tree))
      case Token(TokenType.Minus, None) =>
        val (toks, tree) = factor(tail)
        (toks, UnaryOpNode(Token(TokenType.Minus), tree))
      case _ => throw CalculatorException("Invalid syntax")
    }
    case _ => throw CalculatorException("Invalid syntax")
  }
}