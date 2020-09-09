package org.parsleyj.logkomotiv.terms

import org.parsleyj.logkomotiv.utils.Container
import org.parsleyj.logkomotiv.utils.Uniquer
import kotlin.reflect.KClass

/**
 * An atom is a ground term which represents a single piece of unsplittable data.
 * It does not contain variables.
 *
 * @param <T> the type of the wrapped data
 *
 * @constructor Creates an Atom wrapping the value `lit`. The type is the [KotlinType] extracted by using .getClass on
 * the value.
 *
 * @param lit the value to be wrapped
 */
class Atom<T:Any>(lit: T) : Term {
    /**
     * Returns the wrapped value of this Atom
     */
    val wrappedValue: T = lit
    private val clazz: KClass<*>

    init {
        clazz = lit::class
    }


    override fun createUniqueVarNames(uniquer: Uniquer<String>, namesMap: MutableMap<String, String>) {
        //No variables, do nothing
    }

    override fun populateVarMap(varMap: MutableMap<String, Variable>) {
        //No variables, do nothing
    }

    override fun extractQueryStructure(counter: Container<Int>, varPositions: MutableMap<String, MutableList<Int>>) {
        //No variables, do nothing
    }

    /**
     * Returns true only if y is an instance of Atom and the wrapped values are equal.
     * @param y the term to which this term will be checked against.
     * @return true only if y is an instance of Atom and the wrapped values are equal.
     */
    override fun eq(y: Term): Boolean {
        return if (y is Atom<*>) {
            wrappedValue == (y).wrappedValue
        } else false
    }

    override fun kotlinify(): T {
        return wrappedValue
    }

    override fun toString(): String {
        return "«$wrappedValue»"
    }
}