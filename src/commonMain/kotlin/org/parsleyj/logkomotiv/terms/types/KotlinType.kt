package org.parsleyj.logkomotiv.terms.types

import org.parsleyj.logkomotiv.terms.Term
import org.parsleyj.logkomotiv.terms.Variable
import kotlin.reflect.KClass

/**
 * Data type defined by a Kotlin class or interface.
 */
open class KotlinType(val clazz: KClass<*>) : Type {

    override fun isInstance(arg: Term) = when (arg) {
        is Variable -> this.isCompatible(arg.type)
        else -> clazz.isInstance(arg.kotlinify())
    }

    //for now, types are compatible only if their underlying class is the same
    override fun isCompatible(type: Type) = when(type){
        is KotlinType -> this.clazz == type.clazz
        else -> false
    }

    override fun toString() = "$clazz"


    companion object {
        val INT = object : KotlinType(Int::class) {
            override fun toString() = "INT"
        }

        val STRING = object : KotlinType(String::class){
            override fun toString() = "STRING"
        }
    }


}