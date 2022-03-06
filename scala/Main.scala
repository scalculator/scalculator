object Main {
  def main(args: Array[String]): Unit =
    Runtime.getRuntime().addShutdownHook(new Thread {
      override def run {
        try {
          Thread.sleep(200);
          println("Shutting down Scalculator...")
        } catch {
          case e: InterruptedException =>
            Thread.currentThread().interrupt
            e.printStackTrace
        }
      }
    })
    
    while (true) {
      try {
        Console.out.print("scalculator > ")
        Console.out.flush
        val text = scala.io.StdIn.readLine
        if (!text.trim().isEmpty) {
          val lexer = Lexer(text).advance
          val tokens = Lexer.generateTokens(lexer)
          //println(tokens)
          val tree = Parser.parse(tokens)
          //println(tree)
          if (tree == NullNode()) {
            throw CalculatorException("Null tree")
          }
          val value = Interpreter.interpret(tree)
          println(value.value)
        }
      } catch {
        case e: CalculatorException => Console.out.println(e.toString)
      }
    }
}
