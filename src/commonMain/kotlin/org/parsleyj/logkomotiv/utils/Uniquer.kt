package org.parsleyj.logkomotiv.utils

/**
 * Class that generates a sequence of unique identifiers of type T.
 */
class Uniquer<T>(private val generator: (Long)->T) : Iterator<T> {
    private var counter: Long = 0

    private fun nextUID(): Long {
        return counter++
    }

    override fun hasNext(): Boolean {
        return counter < Long.MAX_VALUE
    }

    override fun next(): T {
        return generator(nextUID())
    }
}