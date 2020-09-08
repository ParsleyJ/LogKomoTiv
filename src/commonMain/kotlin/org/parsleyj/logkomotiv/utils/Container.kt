package org.parsleyj.logkomotiv.utils

data class Container<T>(var value:T)

fun Container<Int>.getAndIncrement(): Int {
    val v = this.value
    this.value+=1
    return v
}
