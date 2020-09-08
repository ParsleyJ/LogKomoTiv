package org.parsleyj.logkomotiv.example

import org.parsleyj.logkomotiv.*
import org.parsleyj.logkomotiv.nativeFacts.NativeFacts.INT_LIB
import org.parsleyj.logkomotiv.backtracking.get
import org.parsleyj.logkomotiv.terms.types.KotlinType.Companion.INT


fun factorialExample() {
    val kb = knowledgeBase {
        fact["f"(A(0), A(1))]
        rule["f"("M" / INT, "F" / INT)](
            ">"("M" / INT, A(0)),
            "-"("M" / INT, A(1), "M-1" / INT) from INT_LIB,
            "f"("M-1" / INT, "fact_of_M-1" / INT),
            "*"("fact_of_M-1" / INT, "M" / INT, "F" / INT) from INT_LIB
        )
    }


    val allFactorialsUpTo10 = kb[
            invokeNative("INT_LIB", "range", A(0), A(11), "N" / INT),
            "f"("N" / INT, "FactOfN" / INT)
    ]
    // ^^^ no factorial computation executed yet


    // allFactorialsUpTo10 is now a sequence of solutions that can be lazily iterated:
    for (solution in allFactorialsUpTo10) {
        println(solution)
    }

    // if we know that the variables will unify with atomic terms, we can directly extract them:
    // (.atoms2<Int, Int>() transforms the iterable of solutions in an iterable of pairs of integers)
    for ((n, factOfN) in allFactorialsUpTo10.atoms2<Int,Int>()){
        println("$n! = $factOfN")
    }

    // like any iterable in kotlin, we can use functional-style operations
    allFactorialsUpTo10.atoms2<Int, Int>()
        .map { (n, f) -> "$n->$f" }
        .forEach { println(it) }

    // note that each invocation to iterator() performs a new query.

}