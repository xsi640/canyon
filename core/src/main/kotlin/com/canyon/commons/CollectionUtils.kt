package com.canyon.commons

import java.lang.reflect.Field
import java.util.function.Function

object CollectionUtils {

    fun Array<String>.container(text: String, ignoreCase: Boolean): Boolean {
        var result = false
        if (text.isNotEmpty() && this.isNotEmpty()) {
            for (s in this) {
                if (s.equals(text, ignoreCase)) {
                    result = true
                    break
                }
            }
        }
        return result
    }

    fun <T> distinct(list: List<T>): List<T> {
        val result = ArrayList<T>()
        if (list.isNotEmpty()) {
            for (t in list) {
                if (!result.contains(t)) {
                    result.add(t)
                }
            }
        }
        return result
    }

    fun split(text: String, strSplit: String, isDistinct: Boolean): List<String> {
        val result = ArrayList<String>()
        if (text.isEmpty() || strSplit.isEmpty())
            return result

        val array = text.split(strSplit)
        if (array.isNotEmpty()) {
            for (s in array) {
                if (isDistinct && result.contains(s)) {
                    continue
                }
                result.add(s)
            }
        }
        return result
    }
}



inline fun <reified T> List<T>.findOne(propName: String, propValue: Any): T? {
    var result: T? = null
    if (this.isNotEmpty()) {
        val field = T::class.java.getDeclaredField(propName)
        field.isAccessible = true
        for (item in this) {
            val value = field.get(item)
            if (value == propValue) {
                result = item
                break
            }
        }
    }
    return result
}

fun <T> List<T>.find(test: (t: T) -> Boolean, distinct: Boolean): List<T> {
    val result = ArrayList<T>()
    if (this.isEmpty())
        return result
    for (item: T in this) {
        if (test(item)) {
            if (distinct && result.contains(item)) {
                continue
            }
            result.add(item)
        }
    }
    return result
}

fun <T> List<T>.findOne(test: (t: T) -> Boolean): T? {
    var result: T? = null
    if (this.isEmpty())
        return result
    for (item: T in this) {
        if (test(item)) {
            result = item
            break
        }
    }
    return result
}

inline fun <TProperty, reified TObject> List<TObject>.toPropertyList(propName: String, distinct: Boolean): List<TProperty> {
    val result = java.util.ArrayList<TProperty>()
    if (this.isEmpty() || propName.isEmpty())
        return result

    val field: Field = TObject::class.java.getDeclaredField(propName)
    for (item in this) {
        field.isAccessible = true
        @Suppress("UNCHECKED_CAST")
        val value = field.get(item) as TProperty
        if (distinct && result.contains(value)) {
            continue
        }
        result.add(value)
    }
    return result
}

fun <Tin, Tout> List<Tin>.convertAll(func: (tin: Tin) -> Tout, distinct: Boolean): List<Tout> {
    val result = ArrayList<Tout>()
    if (this.isEmpty())
        return result

    for (item in this) {
        val out = func(item)
        if (distinct && result.contains(out))
            continue
        result.add(out)
    }
    return result
}

fun <T> List<T>.toSplitString(strSplit: String): String {
    if (this.isEmpty())
        return ""

    val sb = StringBuilder()
    for (i in this.indices) {
        sb.append(this[i].toString())
        if (i != this.size - 1) {
            sb.append(strSplit)
        }
    }
    return sb.toString()
}

fun <T> Collection<T>.limit(skip: Int, take: Int): List<T> {
    val result = java.util.ArrayList<T>()
    if (skip < this.size && take > 0) {
        var index = 0
        val itr = this.iterator()
        while (itr.hasNext()) {
            val t = itr.next()
            if (index >= skip && index < skip + take) {
                result.add(t)
            }
            index++
        }
    }
    return result
}