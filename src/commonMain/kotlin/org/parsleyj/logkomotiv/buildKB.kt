package org.parsleyj.logkomotiv

import org.parsleyj.kotutils.empty
import org.parsleyj.kotutils.list
import org.parsleyj.logkomotiv.terms.*
import org.parsleyj.logkomotiv.terms.types.Type.Companion.ANY
import org.parsleyj.logkomotiv.terms.types.KotlinType
import org.parsleyj.logkomotiv.terms.types.Type
import org.parsleyj.logkomotiv.utils.Uniquer
import kotlin.reflect.KClass


@DslMarker
annotation class KBBuildersDSL

val placeHolderNameGenerator = Uniquer { "_$it" }

class RuleBuilder(private val kbBuilder: KnowledgeBaseBuilder, val head: Term) {
    operator fun invoke(vararg premises: Term) {
        kbBuilder.rules.add(Rule(head, listOf(*premises)))
    }
}


class KnowledgeBaseBuilder {
    val rules = mutableListOf<Rule>()
    private val importedLibs = mutableMapOf<String, List<Term>>()

    inner class RulePartialBuild {
        operator fun get(head: Term): RuleBuilder {
            return RuleBuilder(this@KnowledgeBaseBuilder, head)
        }
    }

    inner class FactPartialBuild {
        operator fun get(head: Term) {
            this@KnowledgeBaseBuilder.rules.add(Rule(head, list.empty()))
        }
    }

    @KBBuildersDSL
    val rule = RulePartialBuild()

    @KBBuildersDSL
    val fact = FactPartialBuild()

    @KBBuildersDSL
    fun <T : Any> atom(obj: T): Atom<T> {
        return Atom(obj)
    }

    @KBBuildersDSL
    fun nativeFact(nf: NativeFact) {
        rules.add(Rule(nf, listOf()))
    }

    @KBBuildersDSL
    infix fun Relation.from(lib: Pair<String, List<Term>>): NativeFactInvoker {
        importedLibs.getOrPut(lib.first) { lib.second }
        return NativeFactInvoker(lib.first, ANY, this@from.name, this@from.rest().toKotlinList())
    }


    fun build(): KnowledgeBase {
        val result = KnowledgeBase()
        importedLibs.forEach { (_, lib) -> result.rules.addAll(lib.map { Rule(it, list.empty()) }) }
        result.rules.addAll(rules)
        return result
    }
}

@KBBuildersDSL
infix fun String.type(t: Type): Pair<String, Type> {
    return Pair(this, t)
}

@KBBuildersDSL
infix fun String.type(t: KClass<*>): Pair<String, Type> {
    return Pair(this, KotlinType(t))
}

operator fun String.div(t: KClass<*>): Variable {
    return Variable(KotlinType(t), this)
}

operator fun String.div(t: Type): Variable {
    return Variable(t, this)
}

operator fun String.invoke(vararg terms: Term): Relation {
    return RelationImpl(ANY, this, listOf(*terms))
}

operator fun String.unaryPlus(): Atom<String> {
    return Atom(this)
}


@KBBuildersDSL
fun V(name: String): Variable {
    return Variable(name)
}

@KBBuildersDSL
fun V(typed: Pair<String, Type>): Variable {
    return Variable(typed.second, typed.first)
}

@KBBuildersDSL
fun V(): Variable {
    return Variable(ANY, placeHolderNameGenerator.next())
}

@KBBuildersDSL
fun <T : Any> A(t: T): Atom<T> {
    return Atom(t)
}

@KBBuildersDSL
fun knowledgeBase(block: KnowledgeBaseBuilder.() -> Unit): KnowledgeBase {
    val kbb = KnowledgeBaseBuilder()
    kbb.block()
    return kbb.build()
}

fun invokeNative(module: String, name: String, vararg terms: Term): NativeFactInvoker {
    return NativeFactInvoker(module, ANY, name, listOf(*terms))
}



