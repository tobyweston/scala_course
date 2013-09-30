package objsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TweetSetSuite extends FunSuite {
  trait TestSets {
    val A = Tweet("a", "a body", 20)
    val B: Tweet = Tweet("b", "b body", 20)
    val C = Tweet("c", "c body", 7)
    val D = Tweet("d", "d body", 9)

    val set1 = new Empty
    val set2 = set1.incl(A)
    val set3 = set2.incl(B)
    val set4c = set3.incl(C)
    val set4d = set3.incl(D)
    val set5 = set4c.incl(D)
  }

  def asSet(tweets: TweetSet): Set[Tweet] = {
    var result = Set[Tweet]()
    tweets.foreach(result += _)
    result
  }

  def fromArray(tweets: Tweet*): TweetSet = {
    val result = new Empty
    tweets.map(tweet => result.incl(tweet))
    result
  }

  def size(set: TweetSet): Int = asSet(set).size

  test("filter: on empty set") {
    new TestSets {
      assert(size(set1.filter(tweet => tweet.user == "a")) === 0)
    }
  }

  test("filter: a on set5") {
    new TestSets {
//      assert(size(fromArray(A, B, C, D).filter(tweet => tweet.user == "a")) === 1)
      val result = set5.filter(tweet => tweet.user == "a")
      assert(size(result) === 1)
    }
  }

  test("filter: 20 on set5") {
    new TestSets {
      assert(size(set5.filter(tw => tw.retweets == 20)) === 2)
    }
  }

  test("union: set4c and set4d") {
    new TestSets {
      assert(size(set4c.union(set4d)) === 4)
    }
  }

  test("union: with empty set (1)") {
    new TestSets {
      assert(size(set5.union(set1)) === 4)
    }
  }

  test("union: with empty set (2)") {
    new TestSets {
      assert(size(set1.union(set5)) === 4)
    }
  }

  test("descending: set5") {
    new TestSets {
      val trends = set5.descendingByRetweet
      assert(!trends.isEmpty)
      assert(trends.head.user == "a" || trends.head.user == "b")
    }
  }
}
