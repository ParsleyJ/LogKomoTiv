# LogKomoTiv

A Kotlin Multiplatform Logic Programming DSL Library.

(**Work in progress**)

Write logic-programming-style declarative rules and query your knowledge base, in Kotlin. 

Since it's _pure_ Kotlin, it can be used as common library in a Multiplatform project, so you can use it on the JVM, on Android, compile it to binary code, run it in the browser.

### A DSL library

LogKomoTiv is a DSL library: it exploits the mechanisms provided by Kotlin to define constructs and instructions to allow a clear definition of a knowledge base.

```kotlin
// Let's create a simple knowledge base.
// We can create atoms using the 'a()' function
// We can create variables using the generic 'v<Type>("name")' function.
// We can also query native libraries (or other knowledge bases) by using the [] operator.
val myKB = knowledgeBase {
    // base case: factorial of 0 is 1.
    fact["f"(a(0), a(1))]

    // inductive step. factorial of M is F, such that...
    rule["f"(v<Int>("M"), v<Int>("F"))] (
        // ... M is greater than 0,
        IntLib[">"(v<Int>("M"), a(0))],
        // ... 'M-1' is equal to M - 1,
        IntLib["-"(v<Int>("M"), a(1), v<Int>("M-1"))],
        // ... 'fact_of_M-1' is the factorial of 'M-1',
        "f"(v<Int>("M-1"), v<Int>("fact_of_M-1")),
        // ... and F is 'fact_of_M-1' multiplied by M.
        IntLib["*"(v<Int>("fact_of_M-1"), v<Int>("M"), v<Int>("F"))]
    )
}
```

Similar constructs can be used to create queries and navigate the results:

```kotlin
// We want to compute all the factorial values from 0! to 10!.
// use [] operator on a kb to create a query.
val allFactorialsUpTo10 = myKB[
        // the range predicates unifies the last variable with all the ints
        //  from the first atom to the second (not included)
        IntLib["range"(a(0), a(11), v<Int>("N"))],
        // for each N, the FactOfN is computed
        "f"(v<Int>("N"), v<Int>("FactOfN"))
]
// however, ^^^ no factorial computation is executed... yet.

// allFactorialsUpTo10 is now a sequence of solutions that is lazily generated when it is iterated:
for (solution in allFactorialsUpTo10) {
    println(solution)
}
```

Output:

```
{FactOfN=«1», N=«0»}
{FactOfN=«1», N=«1»}
{FactOfN=«2», N=«2»}
{FactOfN=«6», N=«3»}
{FactOfN=«24», N=«4»}
{FactOfN=«120», N=«5»}
{FactOfN=«720», N=«6»}
{FactOfN=«5040», N=«7»}
{FactOfN=«40320», N=«8»}
{FactOfN=«362880», N=«9»}
{FactOfN=«3628800», N=«10»}
```
If it is known that the variables will unify with atomic terms, we can directly extract them and specify their types:

```kotlin
// (.atoms2<Int, Int>() transforms the iterable of solutions into an iterable of pairs of integers)
for ((n, factOfN) in allFactorialsUpTo10.atoms2<Int, Int>()) {
    println("$n! = $factOfN")
}
```

Output:

```
0! = 1
1! = 1
2! = 2
3! = 6
4! = 24
5! = 120
6! = 720
7! = 5040
8! = 40320
9! = 362880
10! = 3628800
```


