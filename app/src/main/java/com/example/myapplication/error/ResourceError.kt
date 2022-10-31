package com.example.myapplication.error

class ResourceError(
    var url: String? = null,
    var method: String? = null,
    var request: String? = null,
    var response: String? = null,
    var code: Int = 0,
) {

    override fun toString(): String {
        return """ResourceError {
	url='$url', 
	method='$method', 
	code='$code', 
	request='$request', 
	response='$response'
}"""
    }
}