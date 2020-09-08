package org.parsleyj.logkomotiv.nativeFacts

import org.parsleyj.kotutils.*
import org.parsleyj.logkomotiv.V
import org.parsleyj.logkomotiv.terms.*
import org.parsleyj.logkomotiv.terms.types.Type
import org.parsleyj.logkomotiv.type
import org.parsleyj.logkomotiv.unify.SimpleUnify
import org.parsleyj.logkomotiv.unify.UnificationResult
import kotlin.reflect.KClass

/**
 * Creates a native fact that represents a binary relation between two values of the specified types.
 *
 * @param type      the type of the fact
 * @param module    the module of this native fact
 * @param name      the name of the relation
 * @param t1        the (Kotlin) type of the first sub-term
 * @param t2        the (Kotlin) type of the second sub-term
 * @param predicate predicate used to check if the provided terms unify with the fact
 * @param <T1>      the (Kotlin) type of the first sub-term
 * @param <T2>      the (Kotlin) type of the second sub-term
 * @return a native fact representing a binary predicate
 */
fun <T1 : Any, T2 : Any> binaryPredicate(
    type: Type,
    module: String,
    name: String,
    t1: KClass<T1>,
    t2: KClass<T2>,
    predicate: (T1, T2) -> Boolean,
    shortDescription: String = "",
    printInfixedForm: Boolean = true,
): NativeFact {
    val x1Var: Variable = V("X1" type t1)
    val x2Var: Variable = V("X2" type t2)
    return object : NativeFact(
        type,
        module,
        name,
        list[x1Var, x2Var],
        label@{ self: NativeFact, theta: UnificationResult, other: Relation ->

            val tempTheta: UnificationResult = SimpleUnify.multiUnify(
                theta.copy(),
                StructImpl(Type.ANY, self.toKotlinList()),
                other
            ).itFirst()

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
            var term1: Term? = tmp2.toKotlinList()[0]
            var term2: Term? = tmp2.toKotlinList()[1]
            if (term1 is Variable) {
                term1 = tempTheta.substitution!![term1.name]
            }
            if (term2 is Variable) {
                term2 = tempTheta.substitution!![term2.name]
            }
            if (term1 == null || term2 == null) {
                return@label ItSingleton(UnificationResult.FAILURE)
            }
            if (term1 !is Atom<*> || !t1.isInstance(term1.wrappedValue)) {
                return@label ItSingleton(UnificationResult.FAILURE)
            }
            if (term2 !is Atom<*> || !t2.isInstance(term2.wrappedValue)) {
                return@label ItSingleton(UnificationResult.FAILURE)
            }
            val result: UnificationResult = theta.copy()
            val x1: T1 = (term1 as Atom<T1>).wrappedValue
            val x2: T2 = (term2 as Atom<T2>).wrappedValue
            if (predicate(x1, x2)) {
                return@label ItSingleton(result)
            } else {
                return@label ItSingleton(UnificationResult.FAILURE)
            }
        }) {

        override fun toString(): String {
            val part1 = if (printInfixedForm) {
                "$x1Var $name $x2Var"
            } else {
                "$name($x1Var, $x2Var)"
            }
            val part2 = if (shortDescription.isBlank()) {
                ""
            } else {
                "% $shortDescription"
            }

            return "$part1  $part2"
        }
    }
}





/**
 * Creates a native fact that represents a relation between two arguments and the result of a function.
 *
 * @param type     the type of the fact
 * @param module   the module of this native fact
 * @param name     the name of the relation
 * @param t1       the (Kotlin) type of the first sub-term (argument)
 * @param t2       the (Kotlin) type of the second sub-term (argument)
 * @param tr       the (Kotlin) type of the third sub-term (result)
 * @param function the function used to unify the provided terms with the fact
 * @param <T1>     the (Kotlin) type of the first sub-term
 * @param <T2>     the (Kotlin) type of the second sub-term
 * @param <R>      the (Kotlin) type of the third sub-term
 * @return a native fact representing a binary function application
 */
fun <T1 : Any, T2 : Any, R : Any> binaryOperator(
    type: Type,
    module: String,
    name: String,
    t1: KClass<T1>,
    t2: KClass<T2>,
    tr: KClass<R>,
    function: (T1, T2) -> R,
    shortDescription: String = "",
    printInfixedForm: Boolean = true,
): NativeFact {
    val x1Var: Variable = V("X1" type t1)
    val x2Var: Variable = V("X2" type t2)
    val rVar: Variable = V("R" type tr)
    return object : NativeFact(type, module, name, list[x1Var, x2Var, rVar],
        label@{ self: NativeFact, theta: UnificationResult, other: Relation ->
            val tempTheta: UnificationResult = SimpleUnify.multiUnify(
                theta.copy(),
                StructImpl(type, self.toKotlinList()),
                other
            ).itFirst()
            if (tempTheta.isFailure) {
                return@label ItSingleton(UnificationResult.FAILURE)
            }
            val tmp2: Term = self.rest().applySubstitution((tempTheta.substitution)!!)
            if (tmp2 !is Struct) {
                return@label ItSingleton(UnificationResult.FAILURE)
            }
            if (tmp2.toKotlinList().size != 3) {
                return@label ItSingleton(UnificationResult.FAILURE)
            }
            var term1: Term? = tmp2.toKotlinList()[0]
            var term2: Term? = tmp2.toKotlinList()[1]
            val term3: Term = tmp2.toKotlinList()[2]
            if (term1 is Variable) {
                term1 = tempTheta.substitution!![term1.name]
            }
            if (term2 is Variable) {
                term2 = tempTheta.substitution!![term2.name]
            }
            if (term1 == null || term2 == null) {
                return@label ItSingleton(UnificationResult.FAILURE)
            }
            if (term1 !is Atom<*> || !t1.isInstance(term1.wrappedValue)) {
                return@label ItSingleton(UnificationResult.FAILURE)
            }
            if (term2 !is Atom<*> || !t2.isInstance(term2.wrappedValue)) {
                return@label ItSingleton(UnificationResult.FAILURE)
            }
            val result: UnificationResult = theta.copy()
            val x1: T1 = (term1 as Atom<T1>).wrappedValue
            val x2: T2 = (term2 as Atom<T2>).wrappedValue
            if (term3 !is Variable || tempTheta.substitution!!.contains(term3.name)) {
                if (term3 is Atom<*> && tr.isInstance(term3.wrappedValue)) {
                    val r: R = (term3 as Atom<R>).wrappedValue
                    return@label if (r == function(x1, x2)) {
                        ItSingleton(result)
                    } else {
                        ItSingleton(UnificationResult.FAILURE)
                    }
                } else {
                    return@label ItSingleton(UnificationResult.FAILURE)
                }
            }
            result.substitution!!.put(term3.name, Atom(function(x1, x2)))
            ItSingleton(result)
        }) {


        override fun toString(): String {
            val part1 = if (printInfixedForm) {
                "$rVar = $x1Var $name $x2Var"
            } else {
                "$name($x1Var, $x2Var, $rVar)"
            }
            val part2 = if (shortDescription.isBlank()) {
                ""
            } else {
                "% $shortDescription"
            }

            return "$part1  $part2"
        }
    }
}

/**
 * Creates a native fact that represents a relation between an argument and the result of a function.
 *
 * @param type     the type of the fact
 * @param module   the module of this native fact
 * @param name     the name of the relation
 * @param t        the (Kotlin) type of the first sub-term (argument)
 * @param tr       the (Kotlin) type of the second sub-term (result)
 * @param function the function used to unify the provided terms with the fact
 * @param <T>      the (Kotlin) type of the first sub-term
 * @param <R>      the (Kotlin) type of the second sub-term
 * @return a native fact representing a single-argument function application
</R></T> */
fun <T : Any, R : Any> unaryOperator(
    type: Type,
    module: String,
    name: String,
    t: KClass<T>,
    tr: KClass<R>,
    function: (T) -> R,
    shortDescription: String = "",
    printPrefixedForm: Boolean = true,
): NativeFact {
    val xVar: Variable = V("X" type t)
    val rVar: Variable = V("R" type tr)
    return object : NativeFact(type, module, name, list[xVar, rVar],
        label@{ self: NativeFact, theta: UnificationResult, other: Relation ->
            val tempTheta: UnificationResult = SimpleUnify.multiUnify(
                theta.copy(),
                StructImpl(type, self.toKotlinList()),
                other
            ).itFirst()
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
            var term1: Term? = tmp2.toKotlinList()[0]
            val term2: Term? = tmp2.toKotlinList()[1]
            if (term1 is Variable) {
                term1 = tempTheta.substitution!![term1.name]
            }
            if (term1 == null) {
                return@label ItSingleton(UnificationResult.FAILURE)
            }
            if (term1 !is Atom<*> || !t.isInstance(term1.wrappedValue)) {
                return@label ItSingleton(UnificationResult.FAILURE)
            }
            val result: UnificationResult = theta.copy()
            val x1: T = (term1 as Atom<T>).wrappedValue
            if (term2 !is Variable || tempTheta.substitution!!.contains(term2.name)) {
                if (term2 is Atom<*> && tr.isInstance(term2.wrappedValue)) {
                    val r: R = (term2 as Atom<R>).wrappedValue
                    return@label if ((r == function(x1))) {
                        ItSingleton(result)
                    } else {
                        ItSingleton(UnificationResult.FAILURE)
                    }
                } else {
                    return@label ItSingleton(UnificationResult.FAILURE)
                }
            }
            result.substitution!!.put(term2.name, Atom(function(x1)))
            ItSingleton(result)
        }
    ) {
        override fun toString(): String {
            val part1 = if (printPrefixedForm) {
                "$rVar = $name $xVar"
            } else {
                "$name($xVar, $rVar)"
            }
            val part2 = if (shortDescription.isBlank()) {
                ""
            } else {
                "% $shortDescription"
            }

            return "$part1  $part2"
        }
    }
}


/**
 * Creates a native fact that represents a relation between two arguments and the results of a "generator" function.
 *
 * @param type          the type of the fact
 * @param module        the module of this native fact
 * @param name          the name of the relation
 * @param t1            the (Kotlin) type of the first sub-term (argument)
 * @param t2            the (Kotlin) type of the second sub-term (argument)
 * @param tr            the (Kotlin) type of the third sub-term (result)
 * @param genFunction   the function used to generate the values to be unified with the result term
 * @param <T1>          the (Kotlin) type of the first sub-term
 * @param <T2>          the (Kotlin) type of the second sub-term
 * @param <R>           the (Kotlin) type of the third sub-term
 * @return a native fact representing a binary function application
 */
fun <T1 : Any, T2 : Any, R : Any> binaryGenerator(
    type: Type,
    module: String,
    name: String,
    t1: KClass<T1>,
    t2: KClass<T2>,
    tr: KClass<R>,
    genFunction: (T1, T2) -> Iterable<R>,
    shortDescription: String = "",
    printInfixedForm: Boolean = true,
): NativeFact {
    val x1Var: Variable = V("X1" type t1)
    val x2Var: Variable = V("X2" type t2)
    val rVar: Variable = V("R" type tr)
    return object : NativeFact(type, module, name, list[x1Var, x2Var, rVar],
        label@{ self: NativeFact, theta: UnificationResult, other: Relation ->
            val tempTheta: UnificationResult = SimpleUnify.multiUnify(
                theta.copy(),
                StructImpl(type, self.toKotlinList()),
                other
            ).itFirst()
            if (tempTheta.isFailure) {
                return@label ItSingleton(UnificationResult.FAILURE)
            }
            val tmp2: Term = self.rest().applySubstitution((tempTheta.substitution)!!)
            if (tmp2 !is Struct) {
                return@label ItSingleton(UnificationResult.FAILURE)
            }
            if (tmp2.toKotlinList().size != 3) {
                return@label ItSingleton(UnificationResult.FAILURE)
            }
            var term1: Term? = tmp2.toKotlinList()[0]
            var term2: Term? = tmp2.toKotlinList()[1]
            val term3: Term = tmp2.toKotlinList()[2]
            if (term1 is Variable) {
                term1 = tempTheta.substitution!![term1.name]
            }
            if (term2 is Variable) {
                term2 = tempTheta.substitution!![term2.name]
            }
            if (term1 == null || term2 == null) {
                return@label ItSingleton(UnificationResult.FAILURE)
            }
            if (term1 !is Atom<*> || !t1.isInstance(term1.wrappedValue)) {
                return@label ItSingleton(UnificationResult.FAILURE)
            }
            if (term2 !is Atom<*> || !t2.isInstance(term2.wrappedValue)) {
                return@label ItSingleton(UnificationResult.FAILURE)
            }

            val x1: T1 = (term1 as Atom<T1>).wrappedValue
            val x2: T2 = (term2 as Atom<T2>).wrappedValue

            val iterableOfResults = genFunction(x1, x2)

            if(term3 !is Variable || tempTheta.substitution!!.contains(term3.name)){
                if(term3 is Atom<*> && tr.isInstance(term3.wrappedValue)){
                    val r: R = (term3 as Atom<R>).wrappedValue
                    return@label iterableOfResults
                        .itTakeWhile { it == r }
                        .itMap { theta.copy() }
                        .asNonEmpty(UnificationResult.FAILURE)
                } else {
                    return@label ItSingleton(UnificationResult.FAILURE)
                }
            }

            return@label iterableOfResults.itMap {
                val result: UnificationResult = theta.copy()
                result.substitution!!.put(term3.name, Atom(it))
                result
            }.asNonEmpty(UnificationResult.FAILURE)
        }) {


        override fun toString(): String {
            val part1 = if (printInfixedForm) {
                "$rVar = $x1Var $name $x2Var"
            } else {
                "$name($x1Var, $x2Var, $rVar)"
            }
            val part2 = if (shortDescription.isBlank()) {
                ""
            } else {
                "% $shortDescription"
            }

            return "$part1  $part2"
        }
    }
}
