package org.parsleyj.logkomotiv.terms

import org.parsleyj.logkomotiv.terms.types.Type
import org.parsleyj.logkomotiv.unify.Substitution

/**
 * Simple implementation of [Struct].
 */
open class StructImpl : Struct {
    override val type: Type
    private val terms: List<Term>

    constructor() {
        terms = mutableListOf()
        type = Type.ANY
    }

    constructor(type: Type, terms: List<Term>) {
        this.type = type
        this.terms = terms
    }

    override fun toKotlinList(): List<Term> {
        return terms
    }

    override fun first(): Term {
        return if (terms.isEmpty()) {
            EMPTY
        } else terms[0]
    }

    override fun rest(): Struct {
        if (empty()) {
            return EMPTY
        }
        return if (terms.size == 1) {
            EMPTY
        } else StructImpl(Type.ANY, ArrayList(terms.subList(1, terms.size)))
    }

    override fun empty(): Boolean {
        return terms.isEmpty()
    }

    override fun applySubstitution(subs: Substitution): Struct {
        return StructImpl(this.type, this.terms.map { it.applySubstitution(subs) })
    }

    override fun toString(): String {
        return "(${terms.map { "$it" }.reduce{it1, it2 -> "$it1, $it2"}})"
    }

    companion object {
        val EMPTY: Struct = StructImpl()
    }
}