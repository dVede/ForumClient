package com.example.forum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

typealias Predicate = (value: String?) -> Boolean

class LiveDataValidator(private val liveData: LiveData<String>) {
    private val validationRules = mutableListOf<Predicate>()
    private val errorMessages = mutableListOf<String>()

    var error = MutableLiveData<String?>()

    fun isValid(): Boolean {
        for (i in 0 until validationRules.size) {
            if (validationRules[i](liveData.value)) {
                emitErrorMessage(errorMessages[i])
                return false
            }
        }
        emitErrorMessage(null)
        return true
    }

    private fun emitErrorMessage(messageRes: String?) {
        error.value = messageRes
    }

    fun addRule(errorMsg: String, predicate: Predicate) {
        validationRules.add(predicate)
        errorMessages.add(errorMsg)
    }
}