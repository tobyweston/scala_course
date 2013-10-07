package objsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TweetSetSuite extends FunSuite {
  trait TestSets {
    val A = Tweet("a", "a body", 20)
    val B = Tweet("b", "b body", 20)
    val C = Tweet("c", "c body", 7)
    val D = Tweet("d", "d body", 9)

    val emptySet = new Empty
    val setA = new Empty().incl(A)
    val setAB = new Empty().incl(A).incl(B)
    val setABC = new Empty().incl(A).incl(B).incl(C)
    val setABD = new Empty().incl(A).incl(B).incl(D)
    val setABCD = new Empty().incl(A).incl(B).incl(C).incl(D)
  }

  def asSet(tweets: TweetSet): Set[Tweet] = {
    var result = Set[Tweet]()
    tweets.foreach(result += _)
    result
  }

  def size(set: TweetSet): Int = asSet(set).size

  test("filter: on empty set") {
    new TestSets {
      assert(size(emptySet.filter(tweet => tweet.user == "a")) === 0)
    }
  }

  test("filter: a on set5") {
    new TestSets {
      assert(size(setABCD.filter(tweet => tweet.user == "a")) === 1)
    }
  }

  test("filter: 20 on set5") {
    new TestSets {
      assert(size(setABCD.filter(tw => tw.retweets == 20)) === 2)
    }
  }

  test("union: set4c and set4d") {
    new TestSets {
      assert(size(setABC.union(setABD)) === 4)
    }
  }

  test("union: with empty set (1)") {
    new TestSets {
      assert(size(setABCD.union(emptySet)) === 4)
    }
  }

  test("union: with empty set (2)") {
    new TestSets {
      assert(size(emptySet.union(setABCD)) === 4)
    }
  }

  test("descending: set5") {
    new TestSets {
      val trends = setABCD.descendingByRetweet
      assert(!trends.isEmpty)
      assert(trends.head.user == "a" || trends.head.user == "b")
    }
  }

  test("descending: empty set") {
    assert(new Empty().descendingByRetweet.isEmpty)
  }
}
