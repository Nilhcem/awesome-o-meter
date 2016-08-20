package com.nilhcem.clickclick.core.utils

object Preconditions {

    fun <T> checkNotNull(reference: T?, errorMessage: String? = null): T {
        if (reference == null) {
            throw NullPointerException(errorMessage)
        }
        return reference
    }

    fun checkArgument(expression: Boolean, errorMessage: String? = null) {
        if (!expression) {
            throw IllegalArgumentException(errorMessage)
        }
    }
}
