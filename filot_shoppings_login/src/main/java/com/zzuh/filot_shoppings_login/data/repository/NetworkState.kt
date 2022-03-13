package com.zzuh.filot_shoppings_login.data.repository

enum class Status {
    RUNNING,
    SUCCESS,
    LOADFAILED,
    LOGINFAILED,
    CHECKINGCODE,
    EXIST_EMAIL,
}

class NetworkState(val status: Status, val msg: String) {
    companion object {

        val LOADED: NetworkState
        val LOADING: NetworkState
        val ERROR: NetworkState
        val LOGINFAIL: NetworkState
        val CHECKINGCODE: NetworkState
        val EXIST_EMAIL: NetworkState

        init {
            LOADED = NetworkState(Status.SUCCESS, "Success")
            LOADING = NetworkState(Status.RUNNING, "Running")
            ERROR = NetworkState(Status.LOADFAILED, "Something went wrong")
            LOGINFAIL = NetworkState(Status.LOGINFAILED, "check email and password")
            CHECKINGCODE = NetworkState(Status.CHECKINGCODE, "check the verify code")
            EXIST_EMAIL = NetworkState(Status.EXIST_EMAIL, "exist the email")
        }
    }
}