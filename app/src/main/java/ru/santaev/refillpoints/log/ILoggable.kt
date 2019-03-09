package ru.santaev.refillpoints.log

import android.util.Log

interface ILoggable {

    fun log(
        level: Level,
        tag: String,
        message: String,
        throwable: Throwable? = null
    ) {
        when (level) {
            Level.DEBUG -> Log.d(tag, message, throwable)
        }
    }

    fun log(
        message: String,
        throwable: Throwable? = null
    ) {
        log(
            level = Level.DEBUG,
            tag = tag(),
            message = message,
            throwable = throwable
        )
    }

    fun tag(): String = this::class.java.simpleName

    enum class Level {
        DEBUG
    }
}