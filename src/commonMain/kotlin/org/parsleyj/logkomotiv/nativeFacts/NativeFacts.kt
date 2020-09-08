package org.parsleyj.logkomotiv.nativeFacts


import org.parsleyj.kotutils.*
import org.parsleyj.logkomotiv.V
import org.parsleyj.logkomotiv.knowledgeBase
import org.parsleyj.logkomotiv.terms.*
import org.parsleyj.logkomotiv.terms.types.KotlinType
import org.parsleyj.logkomotiv.terms.types.Type.Companion.ANY
import org.parsleyj.logkomotiv.type
import org.parsleyj.logkomotiv.unify.SimpleUnify.multiUnify

import org.parsleyj.logkomotiv.unify.UnificationResult
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


/**
 * A collection of some static methods about native facts.
 * - some static methods are used to ease native fact implementation.
 * - some static methods are used to easily add a set of common and useful native facts to knowledge bases.
 */
object NativeFacts {


    /**
     * Commonly-used native facts regarding equality
     */
    private fun generateNativeCommonFacts(): List<Term> {
        val result: MutableList<Term> = mutList.empty()
        result.add(
            NativeFact(ANY, "COMMON_LIB", "==",
                list[V("X" type ANY), V("Y" type ANY)]
            ) label@{ self: NativeFact, theta: UnificationResult, other: Relation ->
                val tempTheta: UnificationResult =
                    multiUnify(theta.copy(), StructImpl(ANY, self.toKotlinList()), other).itFirst()
                if (tempTheta.isFailure) {
                    return@label ItSingleton(UnificationResult.FAILURE)
                }
                val tmp2: Term = self.rest().applySubstitution((tempTheta.substitution)!!)
                if (tmp2 !is Struct) {
                    return@label ItSingleton(UnificationResult.FAILURE)
                }

                if (tmp2.toKotlinList().size != 2) {
                    return@label ItSingleton(UnificationResult.FAILURE)
                }
                val term1: Term = tmp2.toKotlinList()[0]
                val term2: Term = tmp2.toKotlinList()[1]
                val result1: UnificationResult = theta.copy()
                if (term1 is Variable && term2 !is Variable) {
                    return@label multiUnify(result1, term2, term1)
                } else if (term1 !is Variable && term2 is Variable) {
                    return@label multiUnify(result1, term1, term2)
                } else if (term1 !is Variable /*&& term2 !is Variable*/) {
                    return@label if (term1.eq(term2)) {
                        ItSingleton(result1)
                    } else {
                        ItSingleton(UnificationResult.FAILURE)
                    }
                } else {
                    return@label ItSingleton(UnificationResult.FAILURE)
                }
            }
        )
        result.add(
            NativeFact(ANY, "COMMON_LIB", "!=", list[V("X"), V("Y")]
            ) label@{ self: NativeFact, theta: UnificationResult, other: Relation ->
                val tempTheta: UnificationResult =
                    multiUnify(theta.copy(), StructImpl(ANY, self.toKotlinList()), other).itFirst()
                if (tempTheta.isFailure) {
                    return@label ItSingleton(UnificationResult.FAILURE)
                }
                val tmp2: Term = self.rest().applySubstitution((tempTheta.substitution)!!)
                if (tmp2 !is Struct) {
                    return@label ItSingleton(UnificationResult.FAILURE)
                }
                if (tmp2.toKotlinList().size != 2) {
                    return@label ItSingleton(UnificationResult.FAILURE)
                }
                val term1: Term = tmp2.toKotlinList()[0]
                val term2: Term = tmp2.toKotlinList()[1]
                val result1: UnificationResult = theta.copy()
                if (term1 !is Variable && term2 !is Variable) {
                    return@label if (term1.eq(term2)) {
                        ItSingleton(UnificationResult.FAILURE)
                    } else {
                        ItSingleton(result1)
                    }
                } else {
                    return@label ItSingleton(UnificationResult.FAILURE)
                }
            }
        )
        return result
    }

    /**
     * Commonly-used native facts regarding simple integer arithmetic and comparisons
     */
    private fun generateNativeIntegerFacts(): List<Term> {
        val result: MutableList<Term> = mutList.empty()
        //TODO
        result += binaryOperator(
            KotlinType.INT, "INT_LIB", "-",
            Int::class, Int::class, Int::class,
            { a: Int, b: Int -> a - b },
            "Integer subtraction"
        )

//        result += unaryOperator(
//            KotlinType.INT, "INT_LIB", "-",
//            Int::class, Int::class,
//            { a: Int -> -a }, "Unary minus"
//        )
//        result += unaryOperator(
//            KotlinType.INT, "INT_LIB", "abs",
//            Int::class,
//            Int::class,
//            { a: Int -> abs(a) }, "Integer absolute value",
//            printPrefixedForm = false
//        )
//        result += binaryOperator(
//            KotlinType.INT, "INT_LIB", "+",
//            Int::class, Int::class, Int::class,
//            { a: Int, b: Int -> a + b },
//            "Integer sum"
//        )
        //TODO
        result += binaryOperator(
            KotlinType.INT, "INT_LIB", "*",
            Int::class, Int::class, Int::class,
            { a: Int, b: Int -> a * b },
            "Integer multiplication"
        )
//        result += binaryOperator(
//            KotlinType.INT, "INT_LIB", "%",
//            Int::class, Int::class, Int::class,
//            { a: Int, b: Int -> a % b },
//            "Integer division remainder"
//        )
//        result += binaryOperator(
//            KotlinType.INT, "INT_LIB", "/",
//            Int::class, Int::class, Int::class,
//            { a: Int, b: Int -> a / b },
//            "Integer division"
//        )
//        result += binaryOperator(
//            KotlinType.INT, "INT_LIB", "min",
//            Int::class, Int::class, Int::class,
//            { a: Int, b: Int -> min(a, b) },
//            "Integer binary minimum value",
//            printInfixedForm = false
//        )
//        result += binaryOperator(
//            KotlinType.INT, "INT_LIB", "max",
//            Int::class, Int::class, Int::class,
//            { a: Int, b: Int -> max(a, b) },
//            "Integer binary maximum value",
//            printInfixedForm = false
//        )
        result += binaryPredicate(
            KotlinType.INT, "INT_LIB", ">",
            Int::class, Int::class,
            { a: Int, b: Int -> a > b },
            "Integer 'greater than' comparison"
        )
        result += binaryPredicate(
            KotlinType.INT, "INT_LIB", ">=",
            Int::class, Int::class,
            { a: Int, b: Int -> a >= b },
            "Integer 'greater or equal than' comparison"
        )
//        result += binaryPredicate(
//            KotlinType.INT, "INT_LIB", "<",
//            Int::class, Int::class,
//            { a: Int, b: Int -> a < b },
//            "Integer 'less than' comparison"
//        )
//        result += binaryPredicate(
//            KotlinType.INT, "INT_LIB", "<=",
//            Int::class, Int::class,
//            { a: Int, b: Int -> a <= b },
//            "Integer 'less or equal than' comparison"
//        )
        result += binaryGenerator(
            KotlinType.INT, "INT_LIB", "range",
            Int::class, Int::class, Int::class,
            { a: Int, b: Int -> a until b},
            "Generates solutions for which the third term is unified with an integer, increasing from the first term" +
                    " to the (excluded) second term",
            printInfixedForm = false
        )
        return result
    }

    val COMMON_LIB = "COMMON_LIB" to generateNativeCommonFacts()
    val INT_LIB = "INT_LIB" to generateNativeIntegerFacts()

    val IntLib = knowledgeBase { import(INT_LIB) }
    val CommonLib = knowledgeBase { import(COMMON_LIB) }

}
