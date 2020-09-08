package org.parsleyj.logkomotiv.terms

import org.parsleyj.kotutils.NonEmptyIterable
import org.parsleyj.logkomotiv.unify.UnificationResult

fun interface CustomMultiUnifiable{
    /**
     * The unification operation for this term. It implements the semantics of unification for this term.
     *
     * @param theta theta value as input of unification, containing the partial variable bindings.
     * @param other the other term to be unified with this native term.
     * @return an iterable of multiple theta values after the various unifications. Each one of the theta values may be
     * a solution with eventual updated variable bindings, or a failure value.
     */
    fun customMultiUnify(theta:UnificationResult, other: Term): NonEmptyIterable<UnificationResult>
}
