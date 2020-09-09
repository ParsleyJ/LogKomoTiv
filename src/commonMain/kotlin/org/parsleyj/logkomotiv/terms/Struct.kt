package org.parsleyj.logkomotiv.terms

import org.parsleyj.logkomotiv.utils.Container
import org.parsleyj.logkomotiv.utils.Uniquer

/**
 * A Struct is a term made of a sequence of sub-terms.
 */
interface Struct : Term {
    override fun directoryPath(): List<String> {
        return mutListAppend(super.directoryPath().toMutableList(), STRUCT_DIR)
    }

    /**
     * Populates a [List] with the terms of this struct.
     * @return the populated Java List
     */
    fun toKotlinList(): List<Term>

    /**
     * Returns the first term in the struct
     */
    fun first(): Term

    /**
     * Returns a struct made of all the terms in this struct, except the first one.
     */
    fun rest(): Struct

    /**
     * Returns true if this struct has no terms.
     */
    fun empty(): Boolean

    /**
     * Returns the size of the struct (number of subterms)
     */
    fun length(): Int {
        return toKotlinList().size
    }

    override fun eq(y: Term): Boolean {
        return if (y is Struct) {
            if (empty()) {
                y.empty()
            } else this.first().eq(y.first())
                    && rest().eq(y.rest())
        } else false
    }

    override fun createUniqueVarNames(uniquer: Uniquer<String>, namesMap: MutableMap<String, String>) {
        for (term in toKotlinList()) {
            term.createUniqueVarNames(uniquer, namesMap)
        }
    }

    override fun populateVarMap(varMap: MutableMap<String, Variable>) {
        for (term in toKotlinList()) {
            term.populateVarMap(varMap)
        }
    }

    override fun extractQueryStructure(counter: Container<Int>, varPositions: MutableMap<String, MutableList<Int>>) {
        for (term in toKotlinList()) {
            term.extractQueryStructure(counter, varPositions)
        }
    }

    override fun justARenaming(term2: Term): Boolean {
        if (term2 is Struct) {
            val struct2 = term2
            if (length() == 0) {
                return struct2.length() == 0
            }
            return if (length() == struct2.length()) {
                (this.first().justARenaming(struct2.first())
                        && rest().justARenaming(struct2.rest()))
            } else {
                false
            }
        }
        return super.justARenaming(term2)
    }

    companion object {
        fun mutListAppend(original: MutableList<String>, element: String): MutableList<String> {
            original.add(element)
            return original
        }

        const val STRUCT_DIR = "STRUCT"
    }
}