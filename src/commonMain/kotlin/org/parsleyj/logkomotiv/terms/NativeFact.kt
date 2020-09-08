package org.parsleyj.logkomotiv.terms

import org.parsleyj.kotutils.ItSingleton
import org.parsleyj.kotutils.NonEmptyIterable
import org.parsleyj.logkomotiv.terms.types.Type
import org.parsleyj.logkomotiv.unify.Substitution
import org.parsleyj.logkomotiv.unify.UnificationResult

/**
 * A special kind of [Relation] used to provide facts that can be unified via a custom
 * hard-coded unification implementation.
 * Useful to create libraries of native facts (e.g. for arithmetical operations etc...).
 * Native facts are collected into modules for improved indexing; each module is represented by a string name.
 */
open class NativeFact : RelationImpl, CustomMultiUnifiable {
    fun interface NativeFactCustomUnificationFunction {
        fun unify(self: NativeFact, theta: UnificationResult, other: Relation): NonEmptyIterable<UnificationResult>
    }

    private val module: String
    private val customUnification: NativeFactCustomUnificationFunction

    /**
     * Creates a native fact with the specified type, module name and relation name.
     */
    constructor(
        type: Type,
        module: String,
        name: String,
        customUnification: NativeFactCustomUnificationFunction
    ) : super(type, name) {
        this.module = module
        this.customUnification = customUnification
    }

    /**
     * Creates a native fact with the specified type, module name, relation name and list of sub-terms.
     */
    constructor(
        type: Type,
        module: String,
        name: String,
        terms: List<Term>,
        customUnification: NativeFactCustomUnificationFunction
    ) : super(type, name, terms) {
        this.module = module
        this.customUnification = customUnification
    }

    override fun directoryPath(): List<String> {
        return genDirectoryNameForNative(module, name, length() - 1)
    }

    override fun customMultiUnify(theta: UnificationResult, other: Term): NonEmptyIterable<UnificationResult> {
        if (other is Relation) {
            return customUnification.unify(this, theta, other)
        }
        return ItSingleton(UnificationResult.FAILURE) //other has to be a relation
    }

    override fun applySubstitution(subs: Substitution): NativeFact {
        val newTerms: MutableList<Term> = mutableListOf()
        val terms: List<Term> = toKotlinList()
        for (term in terms.subList(1, terms.size)) {
            newTerms.add(term.applySubstitution(subs))
        }
        return NativeFact(type, module, name, newTerms, customUnification)
    }

    companion object {
        const val NATIVEFACTS_DIR = "NATIVEFACTS"

        fun genDirectoryNameForNative(module: String, name: String, arity: Int): List<String> {
            val result: MutableList<String> = mutableListOf()
            result.add(Term.GLOBAL_DIR)
            result.add(NATIVEFACTS_DIR)
            result.add(module)
            result.add(Relation.getPredicateStyleName(name, arity))
            return result
        }
    }
}