package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    c match {
      case 0 => 1
      case _ if c == r => 1
      case _ => pascal(c - 1, r - 1) + pascal(c, r - 1)
    }
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def depth(chars: List[Char], currentDepth: Int = 0): Int = chars.headOption match {
      case Some('(') if currentDepth >= 0 => depth(chars.tail, currentDepth + 1)
      case Some(')') => depth(chars.tail, currentDepth - 1)
      case Some(_) => depth(chars.tail, currentDepth)
      case None => currentDepth
    }

    depth(chars) == 0
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = ???
}
