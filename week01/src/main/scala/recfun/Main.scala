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
    if (c == 0) 1
    else if (c > r || c < 0 || r < 0) 0
    else pascal(c, r - 1) + pascal(c - 1, r - 1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {

    def balanceIter(bal: Int, chars: List[Char]): Boolean = {
      if(bal < 0) false
      else if(chars.isEmpty) bal == 0
      else if(chars.head == '(') balanceIter(bal + 1, chars.tail)
      else if(chars.head == ')') balanceIter(bal - 1, chars.tail)
      else balanceIter(bal, chars.tail)
    }

    balanceIter(0, chars)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = ???
}
