package com.yan.anc.repository


/**
 * Created by yan on 2017/11/5.
 */
class ApiResponse<T> {
    var error: String? = null
    var results: T? = null

    var throwable: Throwable? = null

    constructor(error: String?) {
        this.error = error
    }

    constructor(results: T?) {
        this.results = results
    }

    constructor(throwable: Throwable?) {
        this.throwable = throwable
    }

    fun isSuccessful(): Boolean = results != null

}