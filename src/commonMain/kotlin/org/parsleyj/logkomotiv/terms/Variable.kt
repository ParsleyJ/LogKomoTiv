package org.parsleyj.logkomotiv.terms

import org.parsleyj.logkomotiv.unify.Substitution
import org.parsleyj.logkomotiv.utils.Container
import org.parsleyj.logkomotiv.utils.Uniquer
import org.parsleyj.logkomotiv.utils.getAndIncrement

/**
 * A Variable is a special kind of term that can be substituted by other terms during the unification process.
 * The unification process itself is the way to find a valid substitution of variables to make the term satisfy certain
 * properties (i.e. to be equal or "just a renaming" of another term).
 */
class Variable(val name: String) : Term {

    override fun eq(y: Term): Boolean {
        return false
    }

    override fun toString(): String {
        return name
    }

    override fun justARenaming(term2: Term): Boolean {
        return term2 is Variable
    }


    override fun populateVarMap(varMap: MutableMap<String, Variable>) {
        if (!varMap.containsKey(name)) {
            varMap[name] = this
        }
    }

    override fun extractQueryStructure(counter: Container<Int>, varPositions: MutableMap<String, MutableList<Int>>) {
        val positions: MutableList<Int>
        if (varPositions.containsKey(name)) {
            positions = varPositions[name]!!
        } else {
            positions = mutableListOf()
            varPositions[name] = positions
        }
        positions.add(counter.getAndIncrement())
    }

    override fun createUniqueVarNames(uniquer: Uniquer<String>, namesMap: MutableMap<String, String>) {
        if (!namesMap.containsKey(name)) {
            namesMap[name] = uniquer.next()
        }
    }

    override fun applySubstitution(subs: Substitution): Term {
        return subs.getOrDefault(name, this)
    }


}

