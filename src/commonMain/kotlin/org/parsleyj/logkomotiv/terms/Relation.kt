package org.parsleyj.logkomotiv.terms


interface Relation : Struct {
    /**
     * Returns the name of this relation in the Prolog's "predicate style" (name/arity) format.
     *
     * @return the name of this relation in the Prolog's "predicate style" (name/arity) format.
     */
    val predicateStyleName: String
        get() = getPredicateStyleName(name, length() - 1)

    override fun eq(y: Term): Boolean {
        if (y is Relation) {
            if (name != y.name) {
                return false
            }
        }
        return super.eq(y)
    }

    val name: String

    companion object {
        /**
         * Returns the name of a relation in the Prolog's "predicate style" (name/arity) format.
         *
         * @param name  name of the relation
         * @param arity arity of the relation
         * @return the name of a relation in the Prolog's "predicate style" (name/arity) format.
         */
        fun getPredicateStyleName(name: String, arity: Int): String {
            return "$name/$arity"
        }

        const val RELATION_DIR = "RELATION"
    }
}