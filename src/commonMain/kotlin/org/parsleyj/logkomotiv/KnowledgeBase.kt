package org.parsleyj.logkomotiv

import org.parsleyj.kotutils.empty
import org.parsleyj.kotutils.joinWithSeparator
import org.parsleyj.kotutils.list
import org.parsleyj.logkomotiv.terms.Term

/**
 * Object containing a generic knowledge base of facts and rules.
 */
class KnowledgeBase {
    /**
     * The rules in this knowledge base
     */
    val rules: MutableList<Rule> = mutableListOf()


    /**
     * Force-adds some facts to this knowledge base
     * @param facts the facts to be added
     */
    fun addFact(vararg facts: Term) {
        facts.forEach { fact->rules.add(Rule(fact, list.empty())) }
    }


    /**
     * Creates a new knowledge base with all the contents of this one.
     * @return the copy of this knowledge base
     */
    fun copy(): KnowledgeBase {
        val kb = KnowledgeBase()
        kb.rules.addAll(rules)
        return kb
    }

    override fun toString(): String {
        return "Rules(${rules.size}):\n" +
                rules.map{"$it"}.joinWithSeparator(".\n") +
                "\n\n"
    }
}