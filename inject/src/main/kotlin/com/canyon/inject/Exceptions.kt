package com.canyon.inject

class CreateException(
        val msg: String
) : Exception(msg)

class NotfoundDependencies(
        val msg: String
) : Exception(msg)

class NotFoundBeanException(
        val msg: String
) : Exception(msg)

class InitializeException(
        val msg: String
) : Exception(msg)