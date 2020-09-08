package org.parsleyj.logkomotiv.backtracking

import org.parsleyj.kotutils.Generator
import org.parsleyj.kotutils.generate
import org.parsleyj.kotutils.iterate
import org.parsleyj.logkomotiv.KnowledgeBase
import org.parsleyj.logkomotiv.terms.Term
import org.parsleyj.logkomotiv.unify.SimpleUnify
import org.parsleyj.logkomotiv.unify.UnificationResult
import org.parsleyj.logkomotiv.utils.Uniquer

/**
 * Created on 10/12/2019.
 *
 */


fun backwardChainingAsk(
    kb: KnowledgeBase,
    goals: List<Term>,
    prevTheta: UnificationResult = UnificationResult.empty(),
    varNameUniquer: Uniquer<String> = Uniquer { "__gen_$it" }
) =
    iterate(bcAnd(kb, goals, prevTheta, varNameUniquer))



fun bcAnd(
    kb: KnowledgeBase,
    goals: List<Term>,
    theta: UnificationResult,
    varNameUniquer: Uniquer<String> = Uniquer { "__gen_$it" }
): Generator<UnificationResult, Unit> = generate {
    when {
        theta.isFailure -> {
            yield(UnificationResult.FAILURE)
        }
        goals.isEmpty() -> {
            yield(UnificationResult(theta.substitution!!))
        }
        else -> {
            val first = goals[0]
            val rest = goals.subList(1, goals.size)
            val firstGoalbcOr = if(first is AutoUnifiable){
                first.autoUnify(theta, varNameUniquer)
            }else{
                iterate(bcOr(kb, first, theta, varNameUniquer))
            }
            for (theta2 in firstGoalbcOr) {
                if (!theta2.isFailure) {
                    for (theta3 in iterate(bcAnd(kb, rest, theta2, varNameUniquer))) {
                        if (!theta3.isFailure) {
                            yield(theta3)
                        }
                    }
                }
            }
            yield(UnificationResult.FAILURE)
        }
    }
}

fun bcOr(
    kb: KnowledgeBase,
    goal: Term,
    theta: UnificationResult,
    varNameUniquer: Uniquer<String> = Uniquer { "__gen_$it" }
): Generator<UnificationResult, Unit> = generate {
    for (rule in kb.rules) {
        val stdRule = rule.standardizeApart(varNameUniquer)
        val lhs = stdRule.premises
        val rhs = stdRule.head
        val multiUnifIterable = SimpleUnify.multiUnify(theta, rhs, goal)
        for(theta1 in multiUnifIterable) {
            for (theta2 in iterate(bcAnd(kb, lhs, theta1, varNameUniquer))) {
                if (!theta2.isFailure) {
                    yield(theta2)
                }
            }
        }
        yield(UnificationResult.FAILURE)
    }
}

