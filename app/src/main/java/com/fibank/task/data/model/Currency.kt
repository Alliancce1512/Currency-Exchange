package com.fibank.task.data.model

data class Currency(
    val code    : String,
    val name    : String
) {
    override fun toString(): String = "$code - $name"
}