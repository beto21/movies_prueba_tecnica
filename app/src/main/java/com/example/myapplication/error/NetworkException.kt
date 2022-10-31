package com.example.myapplication.error

import java.io.IOException

class NetworkException : IOException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)

}