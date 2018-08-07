package com.canyon.core

import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl

object Reflection {
    fun getSuperInterfaceInnerClass(clazz: Class<*>, interfaceClass: Class<*>): Class<*>? {
        var result: Class<*>? = null
        if (interfaceClass.isAssignableFrom(clazz)) {
            clazz.genericInterfaces.forEach {
                if ((it as ParameterizedTypeImpl).rawType == interfaceClass) {
                    val type = it.actualTypeArguments[0]
                    if (type != null)
                        result = type as Class<*>
                }
            }
        }
        return result
    }
}