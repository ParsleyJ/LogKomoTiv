package org.parsleyj.logkomotiv.example

import org.parsleyj.logkomotiv.*
import org.parsleyj.logkomotiv.nativeFacts.NativeFacts.INT_LIB
import org.parsleyj.logkomotiv.backtracking.get
import org.parsleyj.logkomotiv.nativeFacts.NativeFacts.IntLib
import org.parsleyj.logkomotiv.terms.types.KotlinType.Companion.INT


fun factorialExample() {
    val myKB = knowledgeBase {
        fact["f"(a(0), a(1))]
        rule["f"(v<Int>("M"), v<Int>("F"))](
            IntLib[">"(v<Int>("M"), a(0))],
            IntLib["-"(v<Int>("M"), a(1), v<Int>("M-1"))],
            "f"(v<Int>("M-1"), v<Int>("fact_of_M-1")),
            IntLib["*"(v<Int>("fact_of_M-1"), v<Int>("M"), v<Int>("F"))]
        )
    }


    val allFactorialsUpTo10 = myKB[
            IntLib["range"(a(0), a(11), v<Int>("N"))],
            "f"(v<Int>("N"), v<Int>("FactOfN"))
    ]
    // ^^^ no factorial computation executed whatsoever... yet.


    // allFactorialsUpTo10 is now a sequence of solutions that is lazily generated when it is iterated:
    for (solution in allFactorialsUpTo10) {
        println(solution)
    }

    // if we know that the variables will unify with atomic terms, we can directly extract them:
    // (.atoms2<Int, Int>() transforms the iterable of solutions in an iterable of pairs of integers)
    for ((n, factOfN) in allFactorialsUpTo10.atoms2<Int, Int>()) {
        println("$n! = $factOfN")
    }

    // like any iterable in kotlin, we can use functional-style operations
    allFactorialsUpTo10.atoms2<Int, Int>()
        .map { (n, f) -> "$n->$f" }
        .forEach { println(it) }

    // note that each invocation to iterator() performs a new query.

}