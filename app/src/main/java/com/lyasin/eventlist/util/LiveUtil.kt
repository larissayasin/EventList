package com.lyasin.eventlist.util

data class LiveUtil<T>(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: Throwable? = null,
    val data: T? = null
)