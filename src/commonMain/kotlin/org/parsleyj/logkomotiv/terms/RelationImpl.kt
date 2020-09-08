package org.parsleyj.logkomotiv.terms

import org.parsleyj.kotutils.empty
import org.parsleyj.kotutils.get
import org.parsleyj.kotutils.list
import org.parsleyj.logkomotiv.terms.Relation.Companion.RELATION_DIR
import org.parsleyj.logkomotiv.terms.types.Type
import org.parsleyj.logkomotiv.unify.Substitution

/**
 * Relation standard implementation.
 */
open class RelationImpl(
    type: Type,
    override val name: String, terms: List<Term>
) : StructImpl(
    type, prependNameToListOfTerms(
        name, terms
    )
), Relation {

    override fun directoryPath(): List<String> {
        return Struct.mutListAppend(Struct.mutListAppend(
            super<StructImpl>.directoryPath().toMutableList(), RELATION_DIR),
            predicateStyleName)
    }

    /**
     * Creates a relation with specified type and name
     */
    constructor(type: Type, name: String) : this(type, name, list.empty<Term>())

    override fun applySubstitution(subs: Substitution): RelationImpl {
        val newTerms: MutableList<Term> = mutableListOf()
        val terms: List<Term> = toKotlinList()
        for (term in terms.subList(1, terms.size)) {
            newTerms.add(term.applySubstitution(subs))
        }
        return RelationImpl(type, name, newTerms)
    }

    override fun justARenaming(term2: Term): Boolean {
        if (term2 is RelationImpl) {
            val r2 = term2
            return if (name != r2.name) {
                false
            } else rest().justARenaming(r2.rest())
        }
        return super<StructImpl>.justARenaming(term2)
    }

    override fun toString(): String {
        val rest: Struct = rest()
        return name + rest
    }

    companion object {
        private fun prependNameToListOfTerms(name: String, terms: List<Term>): MutableList<Term> {
            return (list[Atom(name)] + terms).toMutableList()
        }
    }
}