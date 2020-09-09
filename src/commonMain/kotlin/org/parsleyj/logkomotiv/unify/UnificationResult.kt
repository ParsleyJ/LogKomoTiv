package org.parsleyj.logkomotiv.unify



/**
 * Class for objects that represent a result of an unification process.
 * An unification result may be a failure or may be a success.
 * In the latter case, the result value might contain a set of modifications (variable substitutions) in order to
 * unify one term with another.
 */
class UnificationResult {
    /**
     * The substitutions of a successful unification.
     */
    var substitution: Substitution?

    /**
     * Creates a new result representing a failure.
     */
    private constructor() {
        substitution = null
    }

    /**
     * Creates a new successful result containing the specified substitutions.
     *
     * @param substitution the substitutions
     */
    constructor(substitution: Substitution) {
        this.substitution = substitution
    }

    /**
     * Returns true if this result represents an unification failure, false otherwise.
     *
     * @return true if this result represents an unification failure, false otherwise.
     */
    val isFailure: Boolean
        get() {
            return substitution == null
        }

    /**
     * Creates a copy of this result.
     *
     * @return a copy of this result.
     */
    fun copy(): UnificationResult {
        return if (isFailure) {
            FAILURE
        } else {
            val s = substitution
            if (s != null) {
                UnificationResult(s.copy())
            } else {
                UnificationResult()
            }
        }
    }

    companion object {
        /**
         * Special Unification result value representing a failed unification (i.e. where there is no valid substitution
         * of variables in order to successfully unify two terms).
         */
        val FAILURE = UnificationResult()

        /**
         * Creates a new successful result with no substitutions
         *
         * @return a new successful result with no substitutions
         */
        fun empty(): UnificationResult {
            return UnificationResult(Substitution())
        }
    }
}