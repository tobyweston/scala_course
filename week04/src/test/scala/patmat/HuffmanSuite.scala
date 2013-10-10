package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
  trait TestTrees {
    val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
    val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)

    val t3 = Fork(Leaf('a', 3), Fork(Leaf('c', 2), Fork(Leaf('d', 1), Leaf('b', 1),List('d', 'b'), 2), List('c', 'd', 'b'), 4), List('a', 'c', 'd', 'b'), 7)
    val t3_bits = List(0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 0)
    val t3_chars: List[Char] = List('a', 'b', 'a', 'a', 'c', 'c', 'd')
  }

  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }

  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a','b','d'))
    }
  }

  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }

  test("increment") {
    assert(increment('b', List(('a', 1), ('b', 1))) === List(('a', 1), ('b', 2)))
  }

  test("times") {
    assert(times(string2Chars("abaaccd")) === List(('a', 3), ('b', 1), ('c', 2), ('d', 1)))
  }

  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
  }

  test("singleton") {
    assert(singleton(Nil) === false)
    assert(singleton(List(Leaf('a', 1))) === true)
    assert(singleton(List(Leaf('a', 1), Leaf('b', 2))) === false)
  }

  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)))
  }

  test("combine of some leaf list needing sorting") {
    val leaflist = List(Leaf('e', 2), Leaf('t', 2), Leaf('x', 3))
    assert(combine(leaflist) === List(Leaf('x', 3), Fork(Leaf('e',2), Leaf('t',2), List('e', 't'), 4)))
  }

  test("combine empty list") {
    assert(combine(Nil) === Nil)
  }

  test("until of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(until(singleton, combine)(leaflist) === List(Fork(Fork(Leaf('e', 1), Leaf('t', 2), List('e', 't'), 3), Leaf('x', 4), List('e', 't', 'x'), 7)))
  }

  test("create code tree") {
    new TestTrees {
      assert(createCodeTree(string2Chars("abaaccd")) === t3)
    }
  }

  test("decode") {
    new TestTrees {
      assert(decode(t3, t3_bits) === t3_chars)
    }
  }

  test("encode") {
    new TestTrees {
      assert(encode(t3)(t3_chars) === t3_bits)
    }
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }
}
