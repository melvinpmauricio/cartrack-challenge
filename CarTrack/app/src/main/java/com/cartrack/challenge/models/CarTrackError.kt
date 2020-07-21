package com.cartrack.challenge.models

data class CarTrackError constructor(val errorCode: Int, val errorMessage: String?) {

    constructor(errorCode: Int) : this(errorCode, null)

    companion object {
        // Exception Codes
        const val ERR_DB_EMPTY_RESULT_SET = 40001
        const val ERR_DB_FAIL_DELETE = 40002
        const val ERR_DB_FAIL_QUERY = 40003
        const val ERR_DB_FAIL_INSERT = 40004

        const val ERR_UNKNOWN_ERROR = 40000
    }
}