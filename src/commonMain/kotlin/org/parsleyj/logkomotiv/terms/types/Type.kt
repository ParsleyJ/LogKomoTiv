package org.parsleyj.logkomotiv.terms.types

import org.parsleyj.logkomotiv.terms.Term


/**
 * Interface for Type objects, used to describe compatibility relationships between Terms and Variables.
 */
interface Type {
    /**
     * Returns true only if, by definition of this [Type], the `arg` term is compatible.
     */
    fun isInstance(arg: Term): Boolean

    /**
     * Returns true only if, by definition of this [Type], istances of the `type` argument are considered
     * also istances of this type.
     */
    fun isCompatible(type: Type): Boolean


    companion object {
        val ANY: Type = object :Type{
            override fun isInstance(arg: Term) = true
            override fun isCompatible(type: Type) = true
        }
    }

}