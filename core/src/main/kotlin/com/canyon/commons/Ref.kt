package com.canyon.commons


class Ref<T>(value: T) {
    var value: T? = value

    override fun toString(): String {
        return if (value == null) "" else value!!.toString()
    }
}
