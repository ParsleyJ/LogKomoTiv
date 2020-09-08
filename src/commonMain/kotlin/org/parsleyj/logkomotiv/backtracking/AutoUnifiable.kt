package org.parsleyj.logkomotiv.backtracking

import org.parsleyj.kotutils.NonEmptyIterable
import org.parsleyj.logkomotiv.unify.UnificationResult
import org.parsleyj.logkomotiv.utils.Uniquer

fun interface AutoUnifiable {
    fun autoUnify(theta: UnificationResult, varNameUniquer: Uniquer<String>):NonEmptyIterable<UnificationResult>
}