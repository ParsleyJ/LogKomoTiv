@file:Suppress("DuplicatedCode")

package org.parsleyj.logkomotiv.backtracking

import org.parsleyj.kotutils.*
import org.parsleyj.logkomotiv.KnowledgeBase
import org.parsleyj.logkomotiv.terms.Atom
import org.parsleyj.logkomotiv.terms.Term
import org.parsleyj.logkomotiv.utils.Container


class Query(val knowledgeBase: KnowledgeBase, vararg val queriesArg: Term) : Iterable<Map<String, Term>>{


    override fun iterator(): Iterator<Map<String, Term>> {
        return backwardChainingAsk(knowledgeBase, listOf(*queriesArg))
                .itFilter { !it.isFailure }
                .itMap { it.substitution?.clean(*queriesArg)?.bindings?: mapOf()}
                .iterator()
    }

    inline fun <reified T1:Any> values1(): Iterable<T1> {
        return knowledgeBase.search1<T1>(*queriesArg).itMap { it._1 }
    }

    inline fun <reified T1:Any> atoms1(): Iterable<T1> {
        return knowledgeBase.search1<Atom<T1>>(*queriesArg)
                .itMap { it._1.wrappedValue }
    }

    inline fun <reified T1:Any, reified T2:Any> values2(): Iterable<Tuple2<T1, T2>> {
        return knowledgeBase.search2(*queriesArg)
    }

    inline fun <reified T1:Any, reified T2:Any> atoms2(): Iterable<Tuple2<T1, T2>> {
        return knowledgeBase.search2<Atom<T1>, Atom<T2>>(*queriesArg)
                .itMap { tuple(it._1.wrappedValue, it._2.wrappedValue) }
    }

    inline fun <reified T1:Any, reified T2:Any, reified T3:Any> values3(): Iterable<Tuple3<T1, T2, T3>> {
        return knowledgeBase.search3<T1, T2, T3>(*queriesArg)
    }


    inline fun <reified T1:Any, reified T2:Any, reified T3:Any> atoms3(): Iterable<Tuple3<T1, T2, T3>> {
        return knowledgeBase.search3<Atom<T1>, Atom<T2>, Atom<T3>>(*queriesArg)
                .itMap { tuple(it._1.wrappedValue, it._2.wrappedValue, it._3.wrappedValue) }
    }


    inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any> values4(): Iterable<Tuple4<T1, T2, T3, T4>> {
        return knowledgeBase.search4<T1, T2, T3, T4>(*queriesArg)
    }


    inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any> atoms4(): Iterable<Tuple4<T1, T2, T3, T4>> {
        return knowledgeBase.search4<Atom<T1>, Atom<T2>, Atom<T3>, Atom<T4>>(*queriesArg)
                .itMap { tuple(it._1.wrappedValue, it._2.wrappedValue, it._3.wrappedValue, it._4.wrappedValue) }
    }


    inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any> values5(): Iterable<Tuple5<T1, T2, T3, T4, T5>> {
        return knowledgeBase.search5<T1, T2, T3, T4, T5>(*queriesArg)
    }


    inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any> atoms5(): Iterable<Tuple5<T1, T2, T3, T4, T5>> {
        return knowledgeBase.search5<Atom<T1>, Atom<T2>, Atom<T3>, Atom<T4>, Atom<T5>>(*queriesArg)
                .itMap { tuple(it._1.wrappedValue, it._2.wrappedValue, it._3.wrappedValue, it._4.wrappedValue, it._5.wrappedValue) }
    }

    inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any> values6(): Iterable<Tuple6<T1, T2, T3, T4, T5, T6>> {
        return knowledgeBase.search6<T1, T2, T3, T4, T5, T6>(*queriesArg)
    }


    inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any> atoms6(): Iterable<Tuple6<T1, T2, T3, T4, T5, T6>> {
        return knowledgeBase.search6<Atom<T1>, Atom<T2>, Atom<T3>, Atom<T4>, Atom<T5>, Atom<T6>>(*queriesArg)
                .itMap { tuple(it._1.wrappedValue, it._2.wrappedValue, it._3.wrappedValue, it._4.wrappedValue, it._5.wrappedValue, it._6.wrappedValue) }
    }


    inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any> values7(): Iterable<Tuple7<T1, T2, T3, T4, T5, T6, T7>> {
        return knowledgeBase.search7<T1, T2, T3, T4, T5, T6, T7>(*queriesArg)
    }


    inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any> atoms7(): Iterable<Tuple7<T1, T2, T3, T4, T5, T6, T7>> {
        return knowledgeBase.search7<Atom<T1>, Atom<T2>, Atom<T3>, Atom<T4>, Atom<T5>, Atom<T6>, Atom<T7>>(*queriesArg)
                .itMap { tuple(it._1.wrappedValue, it._2.wrappedValue, it._3.wrappedValue, it._4.wrappedValue, it._5.wrappedValue, it._6.wrappedValue, it._7.wrappedValue) }
    }


    inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any, reified T8:Any> values8(): Iterable<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> {
        return knowledgeBase.search8<T1, T2, T3, T4, T5, T6, T7, T8>(*queriesArg)
    }


    inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any, reified T8:Any> atoms8(): Iterable<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> {
        return knowledgeBase.search8<Atom<T1>, Atom<T2>, Atom<T3>, Atom<T4>, Atom<T5>, Atom<T6>, Atom<T7>, Atom<T8>>(*queriesArg)
                .itMap { tuple(it._1.wrappedValue, it._2.wrappedValue, it._3.wrappedValue, it._4.wrappedValue, it._5.wrappedValue, it._6.wrappedValue, it._7.wrappedValue, it._8.wrappedValue) }
    }


    inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any, reified T8:Any, reified T9:Any> values9(): Iterable<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> {
        return knowledgeBase.search9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(*queriesArg)
    }


    inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any, reified T8:Any, reified T9:Any> atoms9(): Iterable<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> {
        return knowledgeBase.search9<Atom<T1>, Atom<T2>, Atom<T3>, Atom<T4>, Atom<T5>, Atom<T6>, Atom<T7>, Atom<T8>, Atom<T9>>(*queriesArg)
                .itMap { tuple(it._1.wrappedValue, it._2.wrappedValue, it._3.wrappedValue, it._4.wrappedValue, it._5.wrappedValue, it._6.wrappedValue, it._7.wrappedValue, it._8.wrappedValue, it._9.wrappedValue) }
    }


    inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any, reified T8:Any, reified T9:Any, reified T10:Any> values10(): Iterable<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> {
        return knowledgeBase.search10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>(*queriesArg)
    }


    inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any, reified T8:Any, reified T9:Any, reified T10:Any> atoms10(): Iterable<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> {
        return knowledgeBase.search10<Atom<T1>, Atom<T2>, Atom<T3>, Atom<T4>, Atom<T5>, Atom<T6>, Atom<T7>, Atom<T8>, Atom<T9>, Atom<T10>>(*queriesArg)
                .itMap { tuple(it._1.wrappedValue, it._2.wrappedValue, it._3.wrappedValue, it._4.wrappedValue, it._5.wrappedValue, it._6.wrappedValue, it._7.wrappedValue, it._8.wrappedValue, it._9.wrappedValue, it._10.wrappedValue) }
    }


}

operator fun KnowledgeBase.get(vararg queries: Term) = Query(this, *queries)

fun getFirstOccurrencePosition(vararg queries: Term): List<String> {
    val vars = mutableMapOf<String, MutableList<Int>>()
    val counter = Container(0)

    for (q in queries) {
        q.extractQueryStructure(counter, vars)
    }

    return vars.map { (k, v) -> k to v }.sortedBy { it.second[0] }.map { it.first }
}


inline fun <reified T1:Any> KnowledgeBase.search1(vararg queries: Term): Iterable<Tuple1<T1>> {
    val firstOccurrences = getFirstOccurrencePosition(*queries)
    return backwardChainingAsk(this, listOf(*queries))
            .itFilter { !it.isFailure }.itMap {
                val term1 = it.substitution!![firstOccurrences[0]]
                if (term1 is T1) {
                    Tuple1(term1 as T1)
                } else {
                    null
                }
            }.itExcludeNulls()
}


inline fun <reified T1:Any, reified T2:Any> KnowledgeBase.search2(vararg queries: Term): Iterable<Tuple2<T1, T2>> {
    val firstOccurrences = getFirstOccurrencePosition(*queries)
    return backwardChainingAsk(this, listOf(*queries))
            .itFilter { !it.isFailure }.itMap {
                val term1 = it.substitution!![firstOccurrences[0]]
                val term2 = it.substitution!![firstOccurrences[1]]
                if (term1 is T1 &&
                        term2 is T2) {
                    Tuple2(term1 as T1,
                            term2 as T2)
                } else {
                    null
                }
            }.itExcludeNulls()
}


inline fun <reified T1:Any, reified T2:Any, reified T3:Any> KnowledgeBase.search3(vararg queries: Term): Iterable<Tuple3<T1, T2, T3>> {
    val firstOccurrences = getFirstOccurrencePosition(*queries)
    return backwardChainingAsk(this, listOf(*queries))
            .itFilter { !it.isFailure }.itMap {
                val term1 = it.substitution!![firstOccurrences[0]]
                val term2 = it.substitution!![firstOccurrences[1]]
                val term3 = it.substitution!![firstOccurrences[2]]
                if (term1 is T1 &&
                        term2 is T2 &&
                        term3 is T3) {
                    Tuple3(term1 as T1,
                            term2 as T2,
                            term3 as T3)
                } else {
                    null
                }
            }.itExcludeNulls()
}


inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any> KnowledgeBase.search4(vararg queries: Term): Iterable<Tuple4<T1, T2, T3, T4>> {
    val firstOccurrences = getFirstOccurrencePosition(*queries)
    return backwardChainingAsk(this, listOf(*queries))
            .itFilter { !it.isFailure }.itMap {
                val term1 = it.substitution!![firstOccurrences[0]]
                val term2 = it.substitution!![firstOccurrences[1]]
                val term3 = it.substitution!![firstOccurrences[2]]
                val term4 = it.substitution!![firstOccurrences[3]]
                if (term1 is T1 &&
                        term2 is T2 &&
                        term3 is T3 &&
                        term4 is T4) {
                    Tuple4(term1 as T1,
                            term2 as T2,
                            term3 as T3,
                            term4 as T4)
                } else {
                    null
                }
            }.itExcludeNulls()
}


inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any> KnowledgeBase.search5(vararg queries: Term): Iterable<Tuple5<T1, T2, T3, T4, T5>> {
    val firstOccurrences = getFirstOccurrencePosition(*queries)
    return backwardChainingAsk(this, listOf(*queries))
            .itFilter { !it.isFailure }.itMap {
                val term1 = it.substitution!![firstOccurrences[0]]
                val term2 = it.substitution!![firstOccurrences[1]]
                val term3 = it.substitution!![firstOccurrences[2]]
                val term4 = it.substitution!![firstOccurrences[3]]
                val term5 = it.substitution!![firstOccurrences[4]]
                if (term1 is T1 &&
                        term2 is T2 &&
                        term3 is T3 &&
                        term4 is T4 &&
                        term5 is T5) {
                    Tuple5(term1 as T1,
                            term2 as T2,
                            term3 as T3,
                            term4 as T4,
                            term5 as T5)
                } else {
                    null
                }
            }.itExcludeNulls()
}


inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any> KnowledgeBase.search6(vararg queries: Term): Iterable<Tuple6<T1, T2, T3, T4, T5, T6>> {
    val firstOccurrences = getFirstOccurrencePosition(*queries)
    return backwardChainingAsk(this, listOf(*queries))
            .itFilter { !it.isFailure }.itMap {
                val term1 = it.substitution!![firstOccurrences[0]]
                val term2 = it.substitution!![firstOccurrences[1]]
                val term3 = it.substitution!![firstOccurrences[2]]
                val term4 = it.substitution!![firstOccurrences[3]]
                val term5 = it.substitution!![firstOccurrences[4]]
                val term6 = it.substitution!![firstOccurrences[5]]
                if (term1 is T1 &&
                        term2 is T2 &&
                        term3 is T3 &&
                        term4 is T4 &&
                        term5 is T5 &&
                        term6 is T6) {
                    Tuple6(term1 as T1,
                            term2 as T2,
                            term3 as T3,
                            term4 as T4,
                            term5 as T5,
                            term6 as T6)
                } else {
                    null
                }
            }.itExcludeNulls()
}


inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any> KnowledgeBase.search7(vararg queries: Term): Iterable<Tuple7<T1, T2, T3, T4, T5, T6, T7>> {
    val firstOccurrences = getFirstOccurrencePosition(*queries)
    return backwardChainingAsk(this, listOf(*queries))
            .itFilter { !it.isFailure }.itMap {
                val term1 = it.substitution!![firstOccurrences[0]]
                val term2 = it.substitution!![firstOccurrences[1]]
                val term3 = it.substitution!![firstOccurrences[2]]
                val term4 = it.substitution!![firstOccurrences[3]]
                val term5 = it.substitution!![firstOccurrences[4]]
                val term6 = it.substitution!![firstOccurrences[5]]
                val term7 = it.substitution!![firstOccurrences[6]]
                if (term1 is T1 &&
                        term2 is T2 &&
                        term3 is T3 &&
                        term4 is T4 &&
                        term5 is T5 &&
                        term6 is T6 &&
                        term7 is T7) {
                    Tuple7(term1 as T1,
                            term2 as T2,
                            term3 as T3,
                            term4 as T4,
                            term5 as T5,
                            term6 as T6,
                            term7 as T7)
                } else {
                    null
                }
            }.itExcludeNulls()
}


inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any, reified T8:Any> KnowledgeBase.search8(vararg queries: Term): Iterable<Tuple8<T1, T2, T3, T4, T5, T6, T7, T8>> {
    val firstOccurrences = getFirstOccurrencePosition(*queries)
    return backwardChainingAsk(this, listOf(*queries))
            .itFilter { !it.isFailure }.itMap {
                val term1 = it.substitution!![firstOccurrences[0]]
                val term2 = it.substitution!![firstOccurrences[1]]
                val term3 = it.substitution!![firstOccurrences[2]]
                val term4 = it.substitution!![firstOccurrences[3]]
                val term5 = it.substitution!![firstOccurrences[4]]
                val term6 = it.substitution!![firstOccurrences[5]]
                val term7 = it.substitution!![firstOccurrences[6]]
                val term8 = it.substitution!![firstOccurrences[7]]
                if (term1 is T1 &&
                        term2 is T2 &&
                        term3 is T3 &&
                        term4 is T4 &&
                        term5 is T5 &&
                        term6 is T6 &&
                        term7 is T7 &&
                        term8 is T8) {
                    Tuple8(term1 as T1,
                            term2 as T2,
                            term3 as T3,
                            term4 as T4,
                            term5 as T5,
                            term6 as T6,
                            term7 as T7,
                            term8 as T8)
                } else {
                    null
                }
            }.itExcludeNulls()
}


inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any, reified T8:Any, reified T9:Any> KnowledgeBase.search9(vararg queries: Term): Iterable<Tuple9<T1, T2, T3, T4, T5, T6, T7, T8, T9>> {
    val firstOccurrences = getFirstOccurrencePosition(*queries)
    return backwardChainingAsk(this, listOf(*queries))
            .itFilter { !it.isFailure }.itMap {
                val term1 = it.substitution!![firstOccurrences[0]]
                val term2 = it.substitution!![firstOccurrences[1]]
                val term3 = it.substitution!![firstOccurrences[2]]
                val term4 = it.substitution!![firstOccurrences[3]]
                val term5 = it.substitution!![firstOccurrences[4]]
                val term6 = it.substitution!![firstOccurrences[5]]
                val term7 = it.substitution!![firstOccurrences[6]]
                val term8 = it.substitution!![firstOccurrences[7]]
                val term9 = it.substitution!![firstOccurrences[8]]
                if (term1 is T1 &&
                        term2 is T2 &&
                        term3 is T3 &&
                        term4 is T4 &&
                        term5 is T5 &&
                        term6 is T6 &&
                        term7 is T7 &&
                        term8 is T8 &&
                        term9 is T9) {
                    Tuple9(term1 as T1,
                            term2 as T2,
                            term3 as T3,
                            term4 as T4,
                            term5 as T5,
                            term6 as T6,
                            term7 as T7,
                            term8 as T8,
                            term9 as T9)
                } else {
                    null
                }
            }.itExcludeNulls()
}


inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any, reified T8:Any, reified T9:Any, reified T10:Any> KnowledgeBase.search10(vararg queries: Term): Iterable<Tuple10<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10>> {
    val firstOccurrences = getFirstOccurrencePosition(*queries)
    return backwardChainingAsk(this, listOf(*queries))
            .itFilter { !it.isFailure }.itMap {
                val term1 = it.substitution!![firstOccurrences[0]]
                val term2 = it.substitution!![firstOccurrences[1]]
                val term3 = it.substitution!![firstOccurrences[2]]
                val term4 = it.substitution!![firstOccurrences[3]]
                val term5 = it.substitution!![firstOccurrences[4]]
                val term6 = it.substitution!![firstOccurrences[5]]
                val term7 = it.substitution!![firstOccurrences[6]]
                val term8 = it.substitution!![firstOccurrences[7]]
                val term9 = it.substitution!![firstOccurrences[8]]
                val term10 = it.substitution!![firstOccurrences[9]]
                if (term1 is T1 &&
                        term2 is T2 &&
                        term3 is T3 &&
                        term4 is T4 &&
                        term5 is T5 &&
                        term6 is T6 &&
                        term7 is T7 &&
                        term8 is T8 &&
                        term9 is T9 &&
                        term10 is T10) {
                    Tuple10(term1 as T1,
                            term2 as T2,
                            term3 as T3,
                            term4 as T4,
                            term5 as T5,
                            term6 as T6,
                            term7 as T7,
                            term8 as T8,
                            term9 as T9,
                            term10 as T10)
                } else {
                    null
                }
            }.itExcludeNulls()
}


inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any, reified T8:Any, reified T9:Any, reified T10:Any, reified T11:Any> KnowledgeBase.search11(vararg queries: Term): Iterable<Tuple11<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11>> {
    val firstOccurrences = getFirstOccurrencePosition(*queries)
    return backwardChainingAsk(this, listOf(*queries))
            .itFilter { !it.isFailure }.itMap {
                val term1 = it.substitution!![firstOccurrences[0]]
                val term2 = it.substitution!![firstOccurrences[1]]
                val term3 = it.substitution!![firstOccurrences[2]]
                val term4 = it.substitution!![firstOccurrences[3]]
                val term5 = it.substitution!![firstOccurrences[4]]
                val term6 = it.substitution!![firstOccurrences[5]]
                val term7 = it.substitution!![firstOccurrences[6]]
                val term8 = it.substitution!![firstOccurrences[7]]
                val term9 = it.substitution!![firstOccurrences[8]]
                val term10 = it.substitution!![firstOccurrences[9]]
                val term11 = it.substitution!![firstOccurrences[10]]
                if (term1 is T1 &&
                        term2 is T2 &&
                        term3 is T3 &&
                        term4 is T4 &&
                        term5 is T5 &&
                        term6 is T6 &&
                        term7 is T7 &&
                        term8 is T8 &&
                        term9 is T9 &&
                        term10 is T10 &&
                        term11 is T11) {
                    Tuple11(term1 as T1,
                            term2 as T2,
                            term3 as T3,
                            term4 as T4,
                            term5 as T5,
                            term6 as T6,
                            term7 as T7,
                            term8 as T8,
                            term9 as T9,
                            term10 as T10,
                            term11 as T11)
                } else {
                    null
                }
            }.itExcludeNulls()
}


inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any, reified T8:Any, reified T9:Any, reified T10:Any, reified T11:Any, reified T12:Any> KnowledgeBase.search12(vararg queries: Term): Iterable<Tuple12<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12>> {
    val firstOccurrences = getFirstOccurrencePosition(*queries)
    return backwardChainingAsk(this, listOf(*queries))
            .itFilter { !it.isFailure }.itMap {
                val term1 = it.substitution!![firstOccurrences[0]]
                val term2 = it.substitution!![firstOccurrences[1]]
                val term3 = it.substitution!![firstOccurrences[2]]
                val term4 = it.substitution!![firstOccurrences[3]]
                val term5 = it.substitution!![firstOccurrences[4]]
                val term6 = it.substitution!![firstOccurrences[5]]
                val term7 = it.substitution!![firstOccurrences[6]]
                val term8 = it.substitution!![firstOccurrences[7]]
                val term9 = it.substitution!![firstOccurrences[8]]
                val term10 = it.substitution!![firstOccurrences[9]]
                val term11 = it.substitution!![firstOccurrences[10]]
                val term12 = it.substitution!![firstOccurrences[11]]
                if (term1 is T1 &&
                        term2 is T2 &&
                        term3 is T3 &&
                        term4 is T4 &&
                        term5 is T5 &&
                        term6 is T6 &&
                        term7 is T7 &&
                        term8 is T8 &&
                        term9 is T9 &&
                        term10 is T10 &&
                        term11 is T11 &&
                        term12 is T12) {
                    Tuple12(term1 as T1,
                            term2 as T2,
                            term3 as T3,
                            term4 as T4,
                            term5 as T5,
                            term6 as T6,
                            term7 as T7,
                            term8 as T8,
                            term9 as T9,
                            term10 as T10,
                            term11 as T11,
                            term12 as T12)
                } else {
                    null
                }
            }.itExcludeNulls()
}


inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any, reified T8:Any, reified T9:Any, reified T10:Any, reified T11:Any, reified T12:Any, reified T13:Any> KnowledgeBase.search13(vararg queries: Term): Iterable<Tuple13<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13>> {
    val firstOccurrences = getFirstOccurrencePosition(*queries)
    return backwardChainingAsk(this, listOf(*queries))
            .itFilter { !it.isFailure }.itMap {
                val term1 = it.substitution!![firstOccurrences[0]]
                val term2 = it.substitution!![firstOccurrences[1]]
                val term3 = it.substitution!![firstOccurrences[2]]
                val term4 = it.substitution!![firstOccurrences[3]]
                val term5 = it.substitution!![firstOccurrences[4]]
                val term6 = it.substitution!![firstOccurrences[5]]
                val term7 = it.substitution!![firstOccurrences[6]]
                val term8 = it.substitution!![firstOccurrences[7]]
                val term9 = it.substitution!![firstOccurrences[8]]
                val term10 = it.substitution!![firstOccurrences[9]]
                val term11 = it.substitution!![firstOccurrences[10]]
                val term12 = it.substitution!![firstOccurrences[11]]
                val term13 = it.substitution!![firstOccurrences[12]]
                if (term1 is T1 &&
                        term2 is T2 &&
                        term3 is T3 &&
                        term4 is T4 &&
                        term5 is T5 &&
                        term6 is T6 &&
                        term7 is T7 &&
                        term8 is T8 &&
                        term9 is T9 &&
                        term10 is T10 &&
                        term11 is T11 &&
                        term12 is T12 &&
                        term13 is T13) {
                    Tuple13(term1 as T1,
                            term2 as T2,
                            term3 as T3,
                            term4 as T4,
                            term5 as T5,
                            term6 as T6,
                            term7 as T7,
                            term8 as T8,
                            term9 as T9,
                            term10 as T10,
                            term11 as T11,
                            term12 as T12,
                            term13 as T13)
                } else {
                    null
                }
            }.itExcludeNulls()
}


inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any, reified T8:Any, reified T9:Any, reified T10:Any, reified T11:Any, reified T12:Any, reified T13:Any, reified T14:Any> KnowledgeBase.search14(vararg queries: Term): Iterable<Tuple14<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14>> {
    val firstOccurrences = getFirstOccurrencePosition(*queries)
    return backwardChainingAsk(this, listOf(*queries))
            .itFilter { !it.isFailure }.itMap {
                val term1 = it.substitution!![firstOccurrences[0]]
                val term2 = it.substitution!![firstOccurrences[1]]
                val term3 = it.substitution!![firstOccurrences[2]]
                val term4 = it.substitution!![firstOccurrences[3]]
                val term5 = it.substitution!![firstOccurrences[4]]
                val term6 = it.substitution!![firstOccurrences[5]]
                val term7 = it.substitution!![firstOccurrences[6]]
                val term8 = it.substitution!![firstOccurrences[7]]
                val term9 = it.substitution!![firstOccurrences[8]]
                val term10 = it.substitution!![firstOccurrences[9]]
                val term11 = it.substitution!![firstOccurrences[10]]
                val term12 = it.substitution!![firstOccurrences[11]]
                val term13 = it.substitution!![firstOccurrences[12]]
                val term14 = it.substitution!![firstOccurrences[13]]
                if (term1 is T1 &&
                        term2 is T2 &&
                        term3 is T3 &&
                        term4 is T4 &&
                        term5 is T5 &&
                        term6 is T6 &&
                        term7 is T7 &&
                        term8 is T8 &&
                        term9 is T9 &&
                        term10 is T10 &&
                        term11 is T11 &&
                        term12 is T12 &&
                        term13 is T13 &&
                        term14 is T14) {
                    Tuple14(term1 as T1,
                            term2 as T2,
                            term3 as T3,
                            term4 as T4,
                            term5 as T5,
                            term6 as T6,
                            term7 as T7,
                            term8 as T8,
                            term9 as T9,
                            term10 as T10,
                            term11 as T11,
                            term12 as T12,
                            term13 as T13,
                            term14 as T14)
                } else {
                    null
                }
            }.itExcludeNulls()
}


inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any, reified T8:Any, reified T9:Any, reified T10:Any, reified T11:Any, reified T12:Any, reified T13:Any, reified T14:Any, reified T15:Any> KnowledgeBase.search15(vararg queries: Term): Iterable<Tuple15<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15>> {
    val firstOccurrences = getFirstOccurrencePosition(*queries)
    return backwardChainingAsk(this, listOf(*queries))
            .itFilter { !it.isFailure }.itMap {
                val term1 = it.substitution!![firstOccurrences[0]]
                val term2 = it.substitution!![firstOccurrences[1]]
                val term3 = it.substitution!![firstOccurrences[2]]
                val term4 = it.substitution!![firstOccurrences[3]]
                val term5 = it.substitution!![firstOccurrences[4]]
                val term6 = it.substitution!![firstOccurrences[5]]
                val term7 = it.substitution!![firstOccurrences[6]]
                val term8 = it.substitution!![firstOccurrences[7]]
                val term9 = it.substitution!![firstOccurrences[8]]
                val term10 = it.substitution!![firstOccurrences[9]]
                val term11 = it.substitution!![firstOccurrences[10]]
                val term12 = it.substitution!![firstOccurrences[11]]
                val term13 = it.substitution!![firstOccurrences[12]]
                val term14 = it.substitution!![firstOccurrences[13]]
                val term15 = it.substitution!![firstOccurrences[14]]
                if (term1 is T1 &&
                        term2 is T2 &&
                        term3 is T3 &&
                        term4 is T4 &&
                        term5 is T5 &&
                        term6 is T6 &&
                        term7 is T7 &&
                        term8 is T8 &&
                        term9 is T9 &&
                        term10 is T10 &&
                        term11 is T11 &&
                        term12 is T12 &&
                        term13 is T13 &&
                        term14 is T14 &&
                        term15 is T15) {
                    Tuple15(term1 as T1,
                            term2 as T2,
                            term3 as T3,
                            term4 as T4,
                            term5 as T5,
                            term6 as T6,
                            term7 as T7,
                            term8 as T8,
                            term9 as T9,
                            term10 as T10,
                            term11 as T11,
                            term12 as T12,
                            term13 as T13,
                            term14 as T14,
                            term15 as T15)
                } else {
                    null
                }
            }.itExcludeNulls()
}


inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any, reified T8:Any, reified T9:Any, reified T10:Any, reified T11:Any, reified T12:Any, reified T13:Any, reified T14:Any, reified T15:Any, reified T16:Any> KnowledgeBase.search16(vararg queries: Term): Iterable<Tuple16<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16>> {
    val firstOccurrences = getFirstOccurrencePosition(*queries)
    return backwardChainingAsk(this, listOf(*queries))
            .itFilter { !it.isFailure }.itMap {
                val term1 = it.substitution!![firstOccurrences[0]]
                val term2 = it.substitution!![firstOccurrences[1]]
                val term3 = it.substitution!![firstOccurrences[2]]
                val term4 = it.substitution!![firstOccurrences[3]]
                val term5 = it.substitution!![firstOccurrences[4]]
                val term6 = it.substitution!![firstOccurrences[5]]
                val term7 = it.substitution!![firstOccurrences[6]]
                val term8 = it.substitution!![firstOccurrences[7]]
                val term9 = it.substitution!![firstOccurrences[8]]
                val term10 = it.substitution!![firstOccurrences[9]]
                val term11 = it.substitution!![firstOccurrences[10]]
                val term12 = it.substitution!![firstOccurrences[11]]
                val term13 = it.substitution!![firstOccurrences[12]]
                val term14 = it.substitution!![firstOccurrences[13]]
                val term15 = it.substitution!![firstOccurrences[14]]
                val term16 = it.substitution!![firstOccurrences[15]]
                if (term1 is T1 &&
                        term2 is T2 &&
                        term3 is T3 &&
                        term4 is T4 &&
                        term5 is T5 &&
                        term6 is T6 &&
                        term7 is T7 &&
                        term8 is T8 &&
                        term9 is T9 &&
                        term10 is T10 &&
                        term11 is T11 &&
                        term12 is T12 &&
                        term13 is T13 &&
                        term14 is T14 &&
                        term15 is T15 &&
                        term16 is T16) {
                    Tuple16(term1 as T1,
                            term2 as T2,
                            term3 as T3,
                            term4 as T4,
                            term5 as T5,
                            term6 as T6,
                            term7 as T7,
                            term8 as T8,
                            term9 as T9,
                            term10 as T10,
                            term11 as T11,
                            term12 as T12,
                            term13 as T13,
                            term14 as T14,
                            term15 as T15,
                            term16 as T16)
                } else {
                    null
                }
            }.itExcludeNulls()
}


inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any, reified T8:Any, reified T9:Any, reified T10:Any, reified T11:Any, reified T12:Any, reified T13:Any, reified T14:Any, reified T15:Any, reified T16:Any, reified T17:Any> KnowledgeBase.search17(vararg queries: Term): Iterable<Tuple17<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17>> {
    val firstOccurrences = getFirstOccurrencePosition(*queries)
    return backwardChainingAsk(this, listOf(*queries))
            .itFilter { !it.isFailure }.itMap {
                val term1 = it.substitution!![firstOccurrences[0]]
                val term2 = it.substitution!![firstOccurrences[1]]
                val term3 = it.substitution!![firstOccurrences[2]]
                val term4 = it.substitution!![firstOccurrences[3]]
                val term5 = it.substitution!![firstOccurrences[4]]
                val term6 = it.substitution!![firstOccurrences[5]]
                val term7 = it.substitution!![firstOccurrences[6]]
                val term8 = it.substitution!![firstOccurrences[7]]
                val term9 = it.substitution!![firstOccurrences[8]]
                val term10 = it.substitution!![firstOccurrences[9]]
                val term11 = it.substitution!![firstOccurrences[10]]
                val term12 = it.substitution!![firstOccurrences[11]]
                val term13 = it.substitution!![firstOccurrences[12]]
                val term14 = it.substitution!![firstOccurrences[13]]
                val term15 = it.substitution!![firstOccurrences[14]]
                val term16 = it.substitution!![firstOccurrences[15]]
                val term17 = it.substitution!![firstOccurrences[16]]
                if (term1 is T1 &&
                        term2 is T2 &&
                        term3 is T3 &&
                        term4 is T4 &&
                        term5 is T5 &&
                        term6 is T6 &&
                        term7 is T7 &&
                        term8 is T8 &&
                        term9 is T9 &&
                        term10 is T10 &&
                        term11 is T11 &&
                        term12 is T12 &&
                        term13 is T13 &&
                        term14 is T14 &&
                        term15 is T15 &&
                        term16 is T16 &&
                        term17 is T17) {
                    Tuple17(term1 as T1,
                            term2 as T2,
                            term3 as T3,
                            term4 as T4,
                            term5 as T5,
                            term6 as T6,
                            term7 as T7,
                            term8 as T8,
                            term9 as T9,
                            term10 as T10,
                            term11 as T11,
                            term12 as T12,
                            term13 as T13,
                            term14 as T14,
                            term15 as T15,
                            term16 as T16,
                            term17 as T17)
                } else {
                    null
                }
            }.itExcludeNulls()
}


inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any, reified T8:Any, reified T9:Any, reified T10:Any, reified T11:Any, reified T12:Any, reified T13:Any, reified T14:Any, reified T15:Any, reified T16:Any, reified T17:Any, reified T18:Any> KnowledgeBase.search18(vararg queries: Term): Iterable<Tuple18<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18>> {
    val firstOccurrences = getFirstOccurrencePosition(*queries)
    return backwardChainingAsk(this, listOf(*queries))
            .itFilter { !it.isFailure }.itMap {
                val term1 = it.substitution!![firstOccurrences[0]]
                val term2 = it.substitution!![firstOccurrences[1]]
                val term3 = it.substitution!![firstOccurrences[2]]
                val term4 = it.substitution!![firstOccurrences[3]]
                val term5 = it.substitution!![firstOccurrences[4]]
                val term6 = it.substitution!![firstOccurrences[5]]
                val term7 = it.substitution!![firstOccurrences[6]]
                val term8 = it.substitution!![firstOccurrences[7]]
                val term9 = it.substitution!![firstOccurrences[8]]
                val term10 = it.substitution!![firstOccurrences[9]]
                val term11 = it.substitution!![firstOccurrences[10]]
                val term12 = it.substitution!![firstOccurrences[11]]
                val term13 = it.substitution!![firstOccurrences[12]]
                val term14 = it.substitution!![firstOccurrences[13]]
                val term15 = it.substitution!![firstOccurrences[14]]
                val term16 = it.substitution!![firstOccurrences[15]]
                val term17 = it.substitution!![firstOccurrences[16]]
                val term18 = it.substitution!![firstOccurrences[17]]
                if (term1 is T1 &&
                        term2 is T2 &&
                        term3 is T3 &&
                        term4 is T4 &&
                        term5 is T5 &&
                        term6 is T6 &&
                        term7 is T7 &&
                        term8 is T8 &&
                        term9 is T9 &&
                        term10 is T10 &&
                        term11 is T11 &&
                        term12 is T12 &&
                        term13 is T13 &&
                        term14 is T14 &&
                        term15 is T15 &&
                        term16 is T16 &&
                        term17 is T17 &&
                        term18 is T18) {
                    Tuple18(term1 as T1,
                            term2 as T2,
                            term3 as T3,
                            term4 as T4,
                            term5 as T5,
                            term6 as T6,
                            term7 as T7,
                            term8 as T8,
                            term9 as T9,
                            term10 as T10,
                            term11 as T11,
                            term12 as T12,
                            term13 as T13,
                            term14 as T14,
                            term15 as T15,
                            term16 as T16,
                            term17 as T17,
                            term18 as T18)
                } else {
                    null
                }
            }.itExcludeNulls()
}


inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any, reified T8:Any, reified T9:Any, reified T10:Any, reified T11:Any, reified T12:Any, reified T13:Any, reified T14:Any, reified T15:Any, reified T16:Any, reified T17:Any, reified T18:Any, reified T19:Any> KnowledgeBase.search19(vararg queries: Term): Iterable<Tuple19<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19>> {
    val firstOccurrences = getFirstOccurrencePosition(*queries)
    return backwardChainingAsk(this, listOf(*queries))
            .itFilter { !it.isFailure }.itMap {
                val term1 = it.substitution!![firstOccurrences[0]]
                val term2 = it.substitution!![firstOccurrences[1]]
                val term3 = it.substitution!![firstOccurrences[2]]
                val term4 = it.substitution!![firstOccurrences[3]]
                val term5 = it.substitution!![firstOccurrences[4]]
                val term6 = it.substitution!![firstOccurrences[5]]
                val term7 = it.substitution!![firstOccurrences[6]]
                val term8 = it.substitution!![firstOccurrences[7]]
                val term9 = it.substitution!![firstOccurrences[8]]
                val term10 = it.substitution!![firstOccurrences[9]]
                val term11 = it.substitution!![firstOccurrences[10]]
                val term12 = it.substitution!![firstOccurrences[11]]
                val term13 = it.substitution!![firstOccurrences[12]]
                val term14 = it.substitution!![firstOccurrences[13]]
                val term15 = it.substitution!![firstOccurrences[14]]
                val term16 = it.substitution!![firstOccurrences[15]]
                val term17 = it.substitution!![firstOccurrences[16]]
                val term18 = it.substitution!![firstOccurrences[17]]
                val term19 = it.substitution!![firstOccurrences[18]]
                if (term1 is T1 &&
                        term2 is T2 &&
                        term3 is T3 &&
                        term4 is T4 &&
                        term5 is T5 &&
                        term6 is T6 &&
                        term7 is T7 &&
                        term8 is T8 &&
                        term9 is T9 &&
                        term10 is T10 &&
                        term11 is T11 &&
                        term12 is T12 &&
                        term13 is T13 &&
                        term14 is T14 &&
                        term15 is T15 &&
                        term16 is T16 &&
                        term17 is T17 &&
                        term18 is T18 &&
                        term19 is T19) {
                    Tuple19(term1 as T1,
                            term2 as T2,
                            term3 as T3,
                            term4 as T4,
                            term5 as T5,
                            term6 as T6,
                            term7 as T7,
                            term8 as T8,
                            term9 as T9,
                            term10 as T10,
                            term11 as T11,
                            term12 as T12,
                            term13 as T13,
                            term14 as T14,
                            term15 as T15,
                            term16 as T16,
                            term17 as T17,
                            term18 as T18,
                            term19 as T19)
                } else {
                    null
                }
            }.itExcludeNulls()
}


inline fun <reified T1:Any, reified T2:Any, reified T3:Any, reified T4:Any, reified T5:Any, reified T6:Any, reified T7:Any, reified T8:Any, reified T9:Any, reified T10:Any, reified T11:Any, reified T12:Any, reified T13:Any, reified T14:Any, reified T15:Any, reified T16:Any, reified T17:Any, reified T18:Any, reified T19:Any, reified T20:Any> KnowledgeBase.search20(vararg queries: Term): Iterable<Tuple20<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20>> {
    val firstOccurrences = getFirstOccurrencePosition(*queries)
    return backwardChainingAsk(this, listOf(*queries))
            .itFilter { !it.isFailure }.itMap {
                val term1 = it.substitution!![firstOccurrences[0]]
                val term2 = it.substitution!![firstOccurrences[1]]
                val term3 = it.substitution!![firstOccurrences[2]]
                val term4 = it.substitution!![firstOccurrences[3]]
                val term5 = it.substitution!![firstOccurrences[4]]
                val term6 = it.substitution!![firstOccurrences[5]]
                val term7 = it.substitution!![firstOccurrences[6]]
                val term8 = it.substitution!![firstOccurrences[7]]
                val term9 = it.substitution!![firstOccurrences[8]]
                val term10 = it.substitution!![firstOccurrences[9]]
                val term11 = it.substitution!![firstOccurrences[10]]
                val term12 = it.substitution!![firstOccurrences[11]]
                val term13 = it.substitution!![firstOccurrences[12]]
                val term14 = it.substitution!![firstOccurrences[13]]
                val term15 = it.substitution!![firstOccurrences[14]]
                val term16 = it.substitution!![firstOccurrences[15]]
                val term17 = it.substitution!![firstOccurrences[16]]
                val term18 = it.substitution!![firstOccurrences[17]]
                val term19 = it.substitution!![firstOccurrences[18]]
                val term20 = it.substitution!![firstOccurrences[19]]
                if (term1 is T1 &&
                        term2 is T2 &&
                        term3 is T3 &&
                        term4 is T4 &&
                        term5 is T5 &&
                        term6 is T6 &&
                        term7 is T7 &&
                        term8 is T8 &&
                        term9 is T9 &&
                        term10 is T10 &&
                        term11 is T11 &&
                        term12 is T12 &&
                        term13 is T13 &&
                        term14 is T14 &&
                        term15 is T15 &&
                        term16 is T16 &&
                        term17 is T17 &&
                        term18 is T18 &&
                        term19 is T19 &&
                        term20 is T20) {
                    Tuple20(term1 as T1,
                            term2 as T2,
                            term3 as T3,
                            term4 as T4,
                            term5 as T5,
                            term6 as T6,
                            term7 as T7,
                            term8 as T8,
                            term9 as T9,
                            term10 as T10,
                            term11 as T11,
                            term12 as T12,
                            term13 as T13,
                            term14 as T14,
                            term15 as T15,
                            term16 as T16,
                            term17 as T17,
                            term18 as T18,
                            term19 as T19,
                            term20 as T20)
                } else {
                    null
                }
            }.itExcludeNulls()
}


// main used to generate the contents of this source file

//
//fun main() {
//    for(i in 1..20){
//        println("inline fun <${(1..i).mapToString().prepend("reified T").joinWithSeparator(", ")}>" +
//                "KnowledgeBase.search$i(vararg queries:Term)" +
//                ":Iterable<Tuple$i<${(1..i).mapToString().prepend("T").joinWithSeparator(", ")}>>{\n" +
//                "val firstOccurrences = getFirstOccurrencePosition(*queries)\n" +
//                "return backwardChainingAsk(this, listOf(*queries))\n" +
//                ".itFilter{!it.isFailure}.itMap{\n" +
//                (1..i).map { "val term$it = it.substitution[firstOccurrences[${it-1}]]" }.joinWithSeparator("\n") +
//                "\nif("+(1..i).map{"term$it is T$it"}.joinWithSeparator(" && \n")+"){\n" +
//                "Tuple$i("+(1..i).map{"term$it as T$it"}.joinWithSeparator(",\n") +")\n"+
//                "}else{\n"+
//                "null\n"+
//                "}\n"+
//                "}.itExcludeNulls()\n" +
//                "}\n\n\n\n")
//    }
//
//    for (i in 1..20) println(
//            "inline operator fun <${(1..i).map { "reified T$it" }.reduce { it1, it2 -> "$it1, $it2" }}>" +
//                    "invoke(${(1..i).map { "v$it:String" }.reduce { it1, it2 -> "$it1, $it2" }}" +
//                    ", block:(${(1..i).map { "T$it" }.reduce { it1, it2 -> "$it1, $it2" }})->Unit){\n" +
//                    "for(solution in backwardChainingAsk(knowledgeBase, queries)){\n" +
//                    "if(!solution.isFailure){\n" +
//                    (1..i).map { "val term$it = solution.substitution.get(v$it)" }.reduce { it1, it2 -> "$it1\n$it2" } +
//                    "\nif(${(1..i).map { "term$it is T$it" }.reduce { it1, it2 -> "$it1 && \n $it2" }}){\n" +
//                    "block(${(1..i).map { "term$it" }.reduce { it1, it2 -> "$it1, $it2" }})\n" +
//                    "}\n" +
//                    "}\n" +
//                    "}\n" +
//                    "}\n\n\n\n"
//    )

//    for (i in 2..20) {
//        println("inline fun <${(1..i).mapToString().prepend("reified T").joinWithSeparator(", ")}>" +
//                "values$i(): Iterable<Tuple$i<${(1..i).mapToString().prepend("T").joinWithSeparator(", ")}>> {\n" +
//                "return knowledgeBase.search$i<${(1..i).mapToString().prepend("T").joinWithSeparator(", ")}>" +
//                "(*queriesArg)\n" +
//                "}\n\n\n\n")
//        println("inline fun <${(1..i).mapToString().prepend("reified T").joinWithSeparator(", ")}>" +
//                "atoms$i(): Iterable<Tuple$i<${(1..i).mapToString().prepend("T").joinWithSeparator(", ")}>> {\n" +
//                "return knowledgeBase.search$i<${(1..i).itMap { "Atom<T$it>" }.joinWithSeparator(", ")}>" +
//                "(*queriesArg)\n" +
//                ".itMap{ tuple(${(1..i).map { "it._$it.wrappedValue" }.joinWithSeparator(", ")}) }\n" +
//                "}\n\n\n\n")
//    }
//}