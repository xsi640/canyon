package com.canyon.inject

class CreateException(
        msg: String
) : Exception(msg)

class NotfoundDependencies(
        msg: String
) : Exception(msg)

class NotFoundBeanException(
        msg: String
) : Exception(msg)

class InitializeException(
        msg: String
) : Exception(msg)