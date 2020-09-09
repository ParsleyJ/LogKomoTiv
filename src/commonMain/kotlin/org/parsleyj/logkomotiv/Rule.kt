package org.parsleyj.logkomotiv

import org.parsleyj.kotutils.joinWithSeparator
import org.parsleyj.logkomotiv.terms.Term
import org.parsleyj.logkomotiv.unify.Substitution
import org.parsleyj.logkomotiv.utils.Uniquer


/**
 * A rule is a structure composed of sequence of conjuncts representing the premises of the rule and
 * a "head" of the rule.
 *
 * @constructor
 * Creates a new rule with specified premises, head and no action.
 */
class Rule(
    val head: Term,
    val premises: List<Term>
) {


    /**
     * Applies the specified substitution to all the terms in the premises and head of the rule.
     * The result is a new rule, no changes are done in place.
     * @param subs the substitutions to be applied
     * @return the new rule
     */
    fun applySubstitution(subs: Substitution): Rule {
        return Rule(
            head.applySubstitution(subs),
            premises.map { a: Term -> a.applySubstitution(subs) }
        )
    }

    /**
     * Renames all the variables to new generated names. All the variables with the same name within the same rule are
     * renamed to the same new name.
     * @param uniquer the id generator used to generate the new names.
     * @return a copy of this rule with variables renamed.
     */
    fun standardizeApart(uniquer: Uniquer<String>): Rule {
        val namesMap = mutableMapOf<String, String>()
        for (term in premises) {
            term.createUniqueVarNames(uniquer, namesMap)
        }
        head.createUniqueVarNames(uniquer, namesMap)
        return applySubstitution(Substitution.varNameSubstitution(namesMap))
    }



    override fun toString(): String {
        return if(premises.isEmpty()){
            "$head"
        }else {
            "$head :- ${premises.map { "$it" }.joinWithSeparator(", ")}"
        }
    }
}