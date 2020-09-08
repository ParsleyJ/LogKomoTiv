package org.parsleyj.logkomotiv.example

import org.parsleyj.logkomotiv.*
import org.parsleyj.logkomotiv.nativeFacts.NativeFacts.INT_LIB
import org.parsleyj.logkomotiv.backtracking.get
import org.parsleyj.logkomotiv.nativeFacts.NativeFacts.IntLib
import org.parsleyj.logkomotiv.terms.types.KotlinType.Companion.INT


fun factorialExample() {
    // let's create a simple knowledge base
    val myKB = knowledgeBase {
        // base case: factorial of 0 is 1.
        fact["f"(a(0), a(1))]

        // inductive step. factorial of M is F, such that...
        rule["f"(v<Int>("M"), v<Int>("F"))](
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


    // we want to compute all the factorial values from 0 to 10.
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

    println()

    // if we know that the variables will unify with atomic terms, we can directly extract them:
    // (.atoms2<Int, Int>() transforms the iterable of solutions in an iterable of pairs of integers)
    for ((n, factOfN) in allFactorialsUpTo10.atoms2<Int, Int>()) {
        println("$n! = $factOfN")
    }

    println()

    // like any iterable in kotlin, we can use functional-style operations
    allFactorialsUpTo10.atoms2<Int, Int>()
        .map { (n, f) -> "$n->$f" }
        .forEach { println(it) }

    // note that each invocation to iterator() performs a new query.

}