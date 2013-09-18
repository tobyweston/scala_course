## Exercises

1. **Question** If `pi` is `3.145159` and `radius` is `10`, expand the following expression to get a single result value

    (2 * pi) * radius

2. **Question** Given the following functions

    def square(x: Int) = x * x
    def sumOfSquares(x: Int, y: Int): Int = square(x) + square(y)

Reduce the following expression to a value using the *call by name* substitution model.

    sumOfSquares(3, 2+2)

3. **Question** Given the functions from question 2, reduce the following expression to a value, this time using the *call by value* substitution model.

    sumOfSquares(3, 2+2)

4. **Question** Given the following function

    def square(x: Int, y: Int) = x * x

Reduce the following expressions to a value using both *call by name* **and** *value* and indicate which has the fewer steps.

 * `test(2, 3)`
 * `test(3+4, 8)`
 * `test(7, 2*4)`
 * `test(3+4, 2*4)`

5. **Question** What is the advantage of call by value over call by reference?

6. **Question** What is the advantage of call by reference over call by value?

7. **Question** Which evaluation strategy does Scala use by default?

8. **Question** How to you write a function to use the non-default strategy?


