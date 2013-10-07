package objsets

import common._
import TweetReader._

/**
 * A class to represent tweets.
 */
class Tweet(val user: String, val text: String, val retweets: Int) {
  override def toString: String = "User: " + user + "\n" + "Text: " + text + " [" + retweets + "]"
}

object Tweet {
  def apply(user: String, text: String, retweets: Int): Tweet = {
    new Tweet(user, text, retweets)
  }
}

/**
 * This represents a set of objects of type `Tweet` in the form of a binary search
 * tree. Every branch in the tree has two children (two `TweetSet`s). There is an
 * invariant which always holds: for every branch `b`, all elements in the left
 * subtree are smaller than the tweet at `b`. The elements in the right subtree are
 * larger.
 *
 * Note that the above structure requires us to be able to compare two tweets (we
 * need to be able to say which of two tweets is larger, or if they are equal). In
 * this implementation, the equality / order of tweets is based on the tweet's text
 * (see `def incl`). Hence, a `TweetSet` could not contain two tweets with the same
 * text from different users.
 *
 * The advantage of representing sets as binary search trees is that the elements
 * of the set can be found quickly. If you want to learn more you can take a look
 * at the Wikipedia page [1], but this is not necessary in order to solve this
 * assignment.
 *
 * [1] http://en.wikipedia.org/wiki/Binary_search_tree
 */
abstract class TweetSet {

  /**
   * This method takes a predicate and returns a subset of all the elements
   * in the original set for which the predicate is true.
   *
   * Question: Can we implement this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def filter(p: Tweet => Boolean): TweetSet

  /**
   * This is a helper method for `filter` that propagates the accumulated tweets.
   */
  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet

  /**
   * Returns a new `TweetSet` that is the union of `TweetSet`s `this` and `that`.
   *
   * Question: Should we implement this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def union(that: TweetSet): TweetSet

  /**
   * Returns the tweet from this set which has the greatest retweet count.
   *
   * Calling `mostRetweeted` on an empty set should throw an exception of
   * type `java.util.NoSuchElementException`.
   *
   * Question: Should we implement this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def mostRetweeted: Tweet

  def mostRetweeted(comparator: Tweet): Tweet = comparator

  /**
   * Returns a list containing all tweets of this set, sorted by retweet count
   * in descending order. In other words, the head of the resulting list should
   * have the highest retweet count.
   *
   * Hint: the method `remove` on TweetSet will be very useful.
   * Question: Should we implement this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def descendingByRetweet: TweetList

  /**
   * The following methods are already implemented
   */

  /**
   * Returns a new `TweetSet` which contains all elements of this set, and the
   * the new element `tweet` in case it does not already exist in this set.
   *
   * If `this.contains(tweet)`, the current set is returned.
   */
  def incl(tweet: Tweet): TweetSet

  /**
   * Returns a new `TweetSet` which excludes `tweet`.
   */
  def remove(tweet: Tweet): TweetSet

  /**
   * Tests if `tweet` exists in this `TweetSet`.
   */
  def contains(tweet: Tweet): Boolean

  /**
   * This method takes a function and applies it to every element in the set.
   */
  def foreach(f: Tweet => Unit): Unit
}

class Empty extends TweetSet {

  def filter(p: (Tweet) => Boolean): TweetSet = new Empty

  def filterAcc(p: Tweet => Boolean, accumulator: TweetSet): TweetSet = accumulator

  def union(that: TweetSet): TweetSet = that

  def contains(tweet: Tweet): Boolean = false

  def incl(tweet: Tweet): TweetSet = new NonEmpty(tweet, new Empty, new Empty)

  def remove(tweet: Tweet): TweetSet = this

  def foreach(f: Tweet => Unit): Unit = ()

  def descendingByRetweet: TweetList = Nil

  def mostRetweeted: Tweet = ???

}

class NonEmpty(root: Tweet, left: TweetSet, right: TweetSet) extends TweetSet {

  def filter(predicate: (Tweet) => Boolean): TweetSet = {
    filterAcc(predicate, new Empty)
  }

  def filterAcc(predicate: Tweet => Boolean, accumulator: TweetSet): TweetSet = {
    left.filterAcc(predicate, right.filterAcc(predicate, if (predicate(root)) accumulator.incl(root) else accumulator))
  }

  def union(that: TweetSet): TweetSet = that.union(left).union(right).incl(root)

  def contains(tweet: Tweet): Boolean =
    if (tweet.text < root.text) left.contains(tweet)
    else if (root.text < tweet.text) right.contains(tweet)
    else true

  def incl(tweet: Tweet): TweetSet = {
    if (tweet.text < root.text) new NonEmpty(root, left.incl(tweet), right)
    else if (root.text < tweet.text) new NonEmpty(root, left, right.incl(tweet))
    else this
  }

  def remove(tweet: Tweet): TweetSet =
    if (tweet.text < root.text) new NonEmpty(root, left.remove(tweet), right)
    else if (root.text < tweet.text) new NonEmpty(root, left, right.remove(tweet))
    else left.union(right)

  def foreach(f: Tweet => Unit): Unit = {
    f(root)
    left.foreach(f)
    right.foreach(f)
  }

  def mostRetweeted: Tweet = mostRetweeted(root)

  override def mostRetweeted(comparator: Tweet) = {
    if (root.retweets > comparator.retweets)
      right.mostRetweeted(left.mostRetweeted(root))
    else
      right.mostRetweeted(left.mostRetweeted(comparator))
  }

  def descendingByRetweet: TweetList = {
    new Cons(mostRetweeted, remove(mostRetweeted).descendingByRetweet)
  }

}

trait TweetList {
  def head: Tweet

  def tail: TweetList

  def isEmpty: Boolean

  def foreach(f: Tweet => Unit): Unit =
    if (!isEmpty) {
      f(head)
      tail.foreach(f)
    }
}

object Nil extends TweetList {
  def head = throw new java.util.NoSuchElementException("head of EmptyList")

  def tail = throw new java.util.NoSuchElementException("tail of EmptyList")

  def isEmpty = true
}

class Cons(val head: Tweet, val tail: TweetList) extends TweetList {
  def isEmpty = false
}


object GoogleVsApple {
  val google = List("android", "Android", "galaxy", "Galaxy", "nexus", "Nexus")
  val apple = List("ios", "iOS", "iphone", "iPhone", "ipad", "iPad")

  lazy val googleTweets: TweetSet = ???
  lazy val appleTweets: TweetSet = ???

  /**
   * A list of all tweets mentioning a keyword from either apple or google,
   * sorted by the number of retweets.
   */
  lazy val trending: TweetList = ???
}

object Main extends App {
  // Print the trending tweets
  GoogleVsApple.trending foreach println
}
