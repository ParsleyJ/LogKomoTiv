package org.parsleyj.logkomotiv.terms

import org.parsleyj.kotutils.get
import org.parsleyj.kotutils.list
import org.parsleyj.logkomotiv.unify.Substitution
import org.parsleyj.logkomotiv.utils.Container
import org.parsleyj.logkomotiv.utils.Uniquer



/**
 * A "Term" is the most generic type of data handled by a logical reasoning engine.
 */
interface Term {
    /**
     * Returns the indexing directory path of the fact represented by this term.
     *
     * @return the directory path
     */
    fun directoryPath(): List<String> {
        return list[GLOBAL_DIR]
    }

    /**
     * Populates the provided namesMap of old-name/new-name associations, in order to standardize this term.
     *
     * @param uniquer  the unique string generator used to create the variable names
     * @param namesMap the associations between old and new names. This method reads and updates this map.
     */
    fun createUniqueVarNames(uniquer: Uniquer<String>, namesMap: MutableMap<String, String>)

    /**
     * Method used to apply a variable [Substitution] to this Term.
     *
     * @param subs the substitutions to be applied
     * @return a Term in which all variables affected by the Substitution are correctly substituted.
     */
    fun applySubstitution(subs: Substitution): Term {
        return this
    }

    /**
     * returns true if this and y are both completely instantiated (they do not contain variables), and are equal.
     *
     * @param y the term to which this term will be checked against.
     * @return true if this and y are both completely instantiated (they do not contain variables), and are equal.
     */
    fun eq(y: Term): Boolean

    /**
     * returns true if this term and term2 have similar structure and if for each variable in this structure, there
     * is a corresponding variable in term2.
     *
     * @param term2 the term to which this term will be checked against.
     * @return true if this term and term2 have similar structure and if to each variable in this structure, there
     * is a variable in the same position in term2, false otherwise.
     */
    fun justARenaming(term2: Term): Boolean {
        return eq(term2)
    }


    /**
     * Populates a map with names/types pairs of the variables in this term.
     *
     * @param varMap the names/types map of the variables in this term.
     */
    fun populateVarMap(varMap: MutableMap<String, Variable>)

    fun kotlinify():Any = this

    fun extractQueryStructure(counter: Container<Int>, varPositions: MutableMap<String, MutableList<Int>>)


    operator fun invoke(s: Substitution) = this.applySubstitution(s)



    companion object {
        /**
         * Global indexing directory
         */
        const val GLOBAL_DIR = "GLOBAL"
    }
}