package com.example.myapplication.utils

import android.util.Log
import com.example.myapplication.error.ResourceError
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset

class ErrorUtils {

    companion object {
        private val TAG = ErrorUtils::class.java.name


        /**
         * Encapsula el error en un modelo.
         * @param request Petición al servidor.
         * @param response Respuesta del servidor.
         * @return [ResourceError].
         */
        fun getResourceError(request: Request, response: Response): ResourceError? {
            var req = "-"
            var res = "-"
            try {
                if (response.body != null) {
                    res = response.body!!.string()
                }
            } catch (e: Exception) {
                Log.w(TAG, "Error al obtener los datos del response")
            }
            try {
                if (request.body != null) {
                    val buffer = Buffer()
                    request.body!!.writeTo(buffer)
                    req = buffer.readString(Charset.defaultCharset())
                }
            } catch (e: Exception) {
                Log.w(TAG, "Error al obtener lso datos el request")
            }

            return ResourceError(request.method, request.url.toString(), req, res)
        }
    }
}