package org.parsleyj.logkomotiv.terms

import org.parsleyj.logkomotiv.terms.types.KotlinType
import org.parsleyj.logkomotiv.terms.types.Type
import org.parsleyj.logkomotiv.utils.Container
import org.parsleyj.logkomotiv.utils.Uniquer
import kotlin.reflect.KClass

/**
 * An atom is a ground term which represents a single piece of unsplittable data.
 * It does not contain variables.
 *
 * @param <T> the type of the wrapped data
 */
class Atom<T:Any> : Term {
    /**
     * Returns the wrapped value of this Atom
     */
    val wrappedValue: T
    private val clazz: KClass<*>
    override val type: Type

    /**
     * Creates an Atom wrapping the value `lit`. The type is the [KotlinType] extracted by using .getClass on
     * the value.
     *
     * @param lit the value to be wrapped
     */
    constructor(lit: T) {
        wrappedValue = lit
        clazz = lit::class
        type = KotlinType(clazz)
    }

    /**
     * Creates an Atom wrapping the value `lit`. The type is the [KotlinType] obtained by the `clazz`
     * argument.
     *
     * @param lit   the value to be wrapped
     * @param clazz the type used to create the [KotlinType] used as type of this Atom term.
     */
    constructor(lit: T, clazz: KClass<*>) {
        wrappedValue = lit
        this.clazz = clazz
        type = KotlinType(clazz)
    }

    /**
     * Returns the kotlin class of the type of this atom.
     */
    fun getType(): KClass<*> {
        return clazz
    }

    override fun createUniqueVarNames(uniquer: Uniquer<String>, namesMap: MutableMap<String, String>) {
        //No variables, do nothing
    }

    override fun populateVarTypes(typesMap: MutableMap<String, Type>) {
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