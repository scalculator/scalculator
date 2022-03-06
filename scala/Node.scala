sealed abstract class Node {
  override def toString: String = this match {
    case NumberNode(value) => value.toString
    case BinOpNode(token, left, right) => s"($left${token.op}$right)"
    case UnaryOpNode(token, node) => s"(${token.op}$node)"
    case NullNode() => "Null"
  }
}

final case class NumberNode(value: Double) extends Node
final case class BinOpNode(token: Token, left: Node, right: Node) extends Node
final case class UnaryOpNode(token: Token, node: Node) extends Node
final case class NullNode() extends Node
