package org.parsleyj.logkomotiv

import org.parsleyj.kotutils.empty
import org.parsleyj.kotutils.list
import org.parsleyj.logkomotiv.terms.*
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
        return NativeFactInvoker(lib.first, this@from.name, this@from.rest().toKotlinList())
    }

    @KBBuildersDSL
    fun import(lib: Pair<String, List<Term>>) {
        importedLibs.getOrPut(lib.first) { lib.second }
    }


    fun build(): KnowledgeBase {
        val result = KnowledgeBase()
        importedLibs.forEach { (_, lib) -> result.rules.addAll(lib.map { Rule(it, list.empty()) }) }
        result.rules.addAll(rules)
        return result
    }
}

@KBBuildersDSL
infix fun Relation.from(lib: Pair<String, List<Term>>): NativeFactInvoker {
    return NativeFactInvoker(lib.first, this@from.name, this@from.rest().toKotlinList())
}


operator fun String.invoke(vararg terms: Term): Relation {
    return RelationImpl(this, listOf(*terms))
}

operator fun String.unaryPlus(): Atom<String> {
    return Atom(this)
}


@KBBuildersDSL
fun v(name: String): Variable {
    return Variable(name)
}

@KBBuildersDSL
fun v(): Variable {
    return Variable(placeHolderNameGenerator.next())
}

@KBBuildersDSL
fun <T : Any> a(t: T): Atom<T> {
    return Atom(t)
}

@KBBuildersDSL
fun knowledgeBase(block: KnowledgeBaseBuilder.() -> Unit): KnowledgeBase {
    val kbb = KnowledgeBaseBuilder()
    kbb.block()
    return kbb.build()
}

fun invokeNative(module: String, name: String, vararg terms: Term): NativeFactInvoker {
    return NativeFactInvoker(module, name, listOf(*terms))
}



