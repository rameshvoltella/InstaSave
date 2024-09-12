package com.open.instafun.errors


interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}
