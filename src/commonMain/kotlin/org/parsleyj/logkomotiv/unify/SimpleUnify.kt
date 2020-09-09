package org.parsleyj.logkomotiv.unify

import org.parsleyj.kotutils.ItSingleton
import org.parsleyj.kotutils.NonEmptyIterable
import org.parsleyj.logkomotiv.terms.*

/**
 * Class containing the simple recursive unification algorithm, with some tweaks to handle Native facts.
 */
object SimpleUnify {
    /**
     * Simple recursive unification algorithm, with some tweaks to handle Native facts.
     *
     * @param theta the input theta value, containing all the previous binding information.
     * @param x     the first term to be unified
     * @param y     the second term to be unified
     * @return a new theta value, eventually containing all the new variable substitutions (bindings) to be performed
     * on `x` in order to make it "equal" or "just a renaming" of `y`. If there is no such substitution, a
     * failure value is returned.
     */
    private fun unify(
        theta: UnificationResult,
        x: Term,
        y: Term
    ): UnificationResult = when {
        theta.isFailure -> UnificationResult.FAILURE
        x.eq(y) -> theta
        x is Variable -> unifyVar(x, y, theta)
        y is Variable -> unifyVar(y, x, theta)
        x is Struct && y is Struct -> {
            if (x.toKotlinList().size != y.toKotlinList().size) {
                UnificationResult.FAILURE
            } else unify(
                unify(
                    theta,
                    x.first(),
                    y.first()
                ),
                x.rest(),
                y.rest()
            )
        }
        else -> UnificationResult.FAILURE
    }

    fun multiUnify(
        theta: UnificationResult,
        x: Term,
        y: Term
    ): NonEmptyIterable<UnificationResult> = when {
        theta.isFailure -> ItSingleton(UnificationResult.FAILURE)
        x is CustomMultiUnifiable -> x.customMultiUnify(theta, y)
        y is CustomMultiUnifiable -> y.customMultiUnify(theta, x)
        x.eq(y) -> ItSingleton(theta)
        x is Variable -> ItSingleton(unifyVar(x, y, theta))
        y is Variable -> ItSingleton(unifyVar(y, x, theta))
        x is Struct && y is Struct -> {
            if (x.toKotlinList().size != y.toKotlinList().size) {
                ItSingleton(UnificationResult.FAILURE)
            } else ItSingleton(
                unify(
                    unify(
                        theta,
                        x.first(),
                        y.first()
                    ),
                    x.rest(),
                    y.rest()
                )
            )
        }
        else -> ItSingleton(UnificationResult.FAILURE)
    }

    fun multiUnify(
        x: Term,
        y: Term
    ): Iterable<UnificationResult> = multiUnify(UnificationResult.empty(), x, y)

    /**
     * Sub algorithm of `unify(...)` used to unify a variable with a term.
     *
     * @param var   the variable to be unified
     * @param t     the term to which the variable will be unified with
     * @param theta the input theta value containing all previously found substitutions.
     * @return the theta value eventually enriched with the substitution to be performed to this variable, or a failure
     * value in case of incompatibilities between the variable and the provided term.
     */
    private fun unifyVar(
        `var`: Variable,
        t: Term,
        theta: UnificationResult
    ): UnificationResult {
        if (theta.isFailure) {
            return UnificationResult.FAILURE
        }
        if (theta.substitution!!.contains(`var`.name)) {
            return unify(theta, theta.substitution!![`var`.name]!!, t)
        }
        if (t is Variable && theta.substitution!!.contains(t.name)) {
            return unify(theta, `var`, theta.substitution!![t.name]!!)
        }
        val copy: UnificationResult = theta.copy()
        copy.substitution?.put(`var`.name, t)
        return copy
    }


    /**
     * Performs a fresh new unification between the two provided terms. This is equivalent to a call to
     * `SimpleUnify.unify(UnificationResult.empty(), x, y)`.
     *
     * @param x the first term to be unified
     * @param y the second term to be unified
     * @return the resulting theta value
     */
    private fun unify(x: Term, y: Term): UnificationResult {
        return unify(UnificationResult.empty(), x, y)
    }

    /**
     * Unification algorithm between two sequences of conjuncts. Note that the two list parameters are assumed
     * to be of the same size. The terms are unified pair-wise on the order in which they appear in the lists, and
     * the whole unification process aborts with a failure as soon as a conjunct cannot be unified with the
     * corresponding one in the other list.
     *
     * @param theta the input theta value containing all previously found substitutions
     * @param a the first sequence to be unified
     * @param b the second sequence to be unified
     *
     * @return the overall unification result
     */
    fun conjunctUnify(
        theta: UnificationResult,
        a: List<Term>,
        b: List<Term>
    ): UnificationResult {
        if (theta.isFailure) {
            return UnificationResult.FAILURE
        }
        if (a.isEmpty() || b.isEmpty()) {
            return theta
        }
        return if (a.size == 1 || b.size == 1) {
            unify(theta, a[0], b[0])
        } else conjunctUnify(
            unify(theta, a[0], b[0]),
            a.subList(1, a.size),
            b.subList(1, b.size)
        )
    }
}