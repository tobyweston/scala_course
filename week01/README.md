## Exercises

### Question 1

If `pi` is `3.145159` and `radius` is `10`, expand the following expression to get a single result value

    (2 * pi) * radius


### Question 2

Given the following functions

    def square(x: Int) = x * x
    def sumOfSquares(x: Int, y: Int): Int = square(x) + square(y)


Reduce the following expression to a value using the *call by name* substitution model.

    sumOfSquares(3, 2+2)


### Question 3

Given the functions from question 2, reduce the following expression to a value, this time using the *call by value* substitution model.

    sumOfSquares(3, 2+2)


### Question 4

Given the following function

    def square(x: Int, y: Int) = x * x

Reduce the following expressions to a value using both *call by name* **and** *value* and indicate which has the fewer steps.

`test(2, 3)`
`test(3+4, 8)`
`test(7, 2*4)`
`test(3+4, 2*4)`

### Question 5

What is the advantage of call by value over call by reference?


### Question 6

What is the advantage of call by reference over call by value?


### Question 7

Which evaluation strategy does Scala use by default?


### Question 8

How to you write a function to use the non-default strategy?



