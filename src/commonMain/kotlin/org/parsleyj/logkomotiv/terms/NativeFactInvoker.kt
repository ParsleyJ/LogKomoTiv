package org.parsleyj.logkomotiv.terms

import org.parsleyj.logkomotiv.terms.types.Type
import org.parsleyj.logkomotiv.unify.Substitution

/**
 * Simple "stub" structured term, which can be used in knowledge base construction, to create terms that can eventually
 * unify native facts in the KB.
 *
 * @constructor
 * Creates an 'invoker' Relation for the Native Fact with specified `type`, `module`, `name` and
 * sub-`terms`.
 */
class NativeFactInvoker(
    private val module: String,
    type: Type,
    name: String,
    terms: List<Term>
) : RelationImpl(
    type,
    name,
    terms
) {

    override fun directoryPath(): List<String> {
        return NativeFact.genDirectoryNameForNative(module, name, length() - 1)
    }

    override fun applySubstitution(subs: Substitution): RelationImpl {
        val newTerms: MutableList<Term> = mutableListOf()
        val terms: List<Term> = toKotlinList()
        for (term in terms.subList(1, terms.size)) {
            newTerms.add(term.applySubstitution(subs))
        }
        return NativeFactInvoker(module, type, name, newTerms)
    }
}