package org.parsleyj.logkomotiv.unify

import org.parsleyj.logkomotiv.terms.Term
import org.parsleyj.logkomotiv.terms.Variable

/**
 * Support data structure containing variable substitutions for the unification process and the automatic renaming
 * process.
 */
class Substitution {
    val bindings: MutableMap<String, Term> = HashMap()
    private val strongBindings: MutableSet<String> = HashSet()

    /**
     * Puts a binding between a variable and a term.
     *
     * @param name the name of the variable to which the term is bound.
     * @param what the term to be bound to the variable.
     */
    fun put(name: String, what: Term) {
        if (bindings.containsKey(name) && strongBindings.contains(name)) {
            return
        }
        bindings[name] = what
        strongBindings.add(name)
    }

    /**
     * Checks whether this substitution object contains a binding for the variable with specified name.
     *
     * @param name the name of the variable
     * @return true if this substitution object contains a binding for the variable with specified name.
     */
    operator fun contains(name: String): Boolean {
        return bindings.containsKey(name)
    }

    /**
     * Returns the term that would be substituted to the variable with specified name, when this substitution is
     * applied, or null if there is no such binding entry in this substitution.
     *
     * @param name the name of the variable
     * @return the term that would be substituted to the variable with specified name, when this substitution is
     * applied, or null if there is no such binding entry in this substitution.
     */
    operator fun get(name: String): Term? {
        return bindings[name]
    }

    /**
     * Returns the term that would be substituted to the variable with specified name, when this substitution is
     * applied, or the default provided value if there is no such binding entry in this substitution.
     *
     * @param name the name of the variable
     * @return the term that would be substituted to the variable with specified name, when this substitution is
     * applied, or the default provided value if there is no such binding entry in this substitution.
     */
    fun getOrDefault(name: String, def: Term): Term {
        return bindings.getOrElse(name) {def}
    }

    /**
     * Creates a copy of this substitution object, with the same bindings
     *
     * @return the substitution copy
     */
    fun copy(): Substitution {
        val s = Substitution()
        bindings.forEach { (key: String, value: Term) -> s.bindings[key] = value }
        s.strongBindings.addAll(strongBindings)
        return s
    }

    /**
     * Given a certain goal, it cleans this substitution of all the bindings not involved in the achievement of
     * the goals.
     * @param goal the goal
     * @return substitution cleaned of all the bindings not involved in the achievement of the goals.
     */
    fun clean(vararg goals: Term): Substitution {
        val varMap: MutableMap<String, Variable> = mutableMapOf()
        goals.forEach { it.populateVarMap(varMap) }
        val copy = copy()
        val stringTermHashMap: HashMap<String, Term> = HashMap(copy.bindings)
        stringTermHashMap.forEach { (k: String, _) ->
            if (!varMap.containsKey(k)) {
                copy.bindings.remove(k)
                copy.strongBindings.remove(k)
            }
        }
        return copy
    }



    override fun toString(): String {
        return bindings.toString()
    }

    companion object {
        /**
         * Creates a new substitution, where each variable is substituted with an other one with specified type and name.
         *
         * @param namesMap the map old var name -> new var name for each variable
         * @return the new Substitution object
         */
        fun varNameSubstitution(namesMap: Map<String, String>): Substitution {
            val substitution = Substitution()
            namesMap.forEach { (k: String?, v: String?) ->
                substitution.put(
                    k, Variable(v)
                )
            }
            return substitution
        }
    }
}