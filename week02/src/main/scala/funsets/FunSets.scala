package funsets

import common._

/**
 * 2. Purely Functional Sets.
 */
object FunSets {

  /**
   * We represent a set by its characteristic function, i.e. its `contains` predicate.
   */
  type Set = Int => Boolean

  /**
   * Indicates whether a set contains a given element.
   *
   * Invokes the predicate (function) defined by the type alias. NB, set(element) is equivalent to set.apply(element)
   */
  def contains(set: Set, element: Int): Boolean = set.apply(element)

  /**
   * Returns the set of the one given element.
   */
  def singletonSet(element: Int): Set = x => x == element

  /**
   * Returns the union of the two given sets, the sets of all elements that are in either `s` or `t`.
   */
  def union(s: Set, t: Set): Set = x => s(x) || t(x)

  /**
   * Returns the intersection of the two given sets, the set of all elements that are both in `s` and `t`.
   */
  def intersect(s: Set, t: Set): Set = x => s(x) && t(x)

  /**
   * Returns the difference of the two given sets, the set of all elements of `s` that are not in `t`.
   */
  def diff(s: Set, t: Set): Set = x => s(x) && !t(x)

  /**
   * Returns the subset of `s` for which `p` holds.
   */
  def filter(s: Set, predicate: Int => Boolean): Set = intersect(s, predicate)

  /**
   * The bounds for `forall` and `exists` are +/- 1000.
   */
  val bound = 1000

  /**
   * Returns whether all bounded integers within `s` satisfy `p`.
   */
  def forall(s: Set, predicate: Int => Boolean): Boolean = {
    def recur(x: Int): Boolean = {
      if (x > bound)
        true
      else if (contains(s, x))
        predicate.apply(x) && recur(x + 1)
      else
        recur(x + 1)
    }
    recur(-bound)
  }

  /**
   * Returns whether there exists a bounded integer within `s`
   * that satisfies `p`.
   */
  def exists(s: Set, p: Int => Boolean): Boolean = ???

  /**
   * Returns a set transformed by applying `f` to each element of `s`.
   */
  def map(s: Set, f: Int => Int): Set = ???

  /**
   * Displays the contents of a set
   */
  def toString(s: Set): String = {
    val xs = for (i <- -bound to bound if contains(s, i)) yield i
    xs.mkString("{", ",", "}")
  }

  /**
   * Prints the contents of a set on the console.
   */
  def printSet(s: Set) {
    println(toString(s))
  }
}
