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
    if (c == 0 || r == c || r == 0)
      1
    else
      pascal(c, r - 1) + pascal(c - 1, r - 1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    recur(chars, 0) == 0
  }

  def recur(characters: List[Char], count: Int): Int = {
    if (characters.isEmpty || count < 0)
      count
    else if (characters.head == '(')
      recur(characters.tail, count + 1)
    else if (characters.head == ')')
      recur(characters.tail, count - 1)
    else
      recur(characters.tail, count)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money == 0)
      1
    else if (money < 0 || coins.isEmpty)
      0
    else
      countChange(money - coins.head, coins) + countChange(money, coins.tail)
  }

}
