final object Interpreter {
  final def visit(node: Node): Values.Number = node match {
    case NumberNode(x: Double) => Values.Number(x)
    case BinOpNode(Token(TokenType.Plus, None), left, right) => Values.Number(visit(left).value + visit(right).value)
    case BinOpNode(Token(TokenType.Minus, None), left, right) => Values.Number(visit(left).value - visit(right).value)
    case BinOpNode(Token(TokenType.Multiply, None), left, right) => Values.Number(visit(left).value * visit(right).value)
    case BinOpNode(Token(TokenType.Divide, None), left, right) => Values.Number(visit(left).value / visit(right).value)
    case UnaryOpNode(Token(TokenType.Plus, None), node) => Values.Number(+visit(node).value)
    case UnaryOpNode(Token(TokenType.Minus, None), node) => Values.Number(-visit(node).value)
    case NullNode() => throw CalculatorException("Null tree")
    case _ => throw CalculatorException("Invalid operation during runtime")
  }

  final def interpret(node: Node) = visit(node)
}
