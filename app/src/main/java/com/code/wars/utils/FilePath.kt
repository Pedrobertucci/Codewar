package com.code.wars.utils

import java.io.InputStreamReader
import java.lang.Exception

object FilePath {

    fun getContent(path: String) : String {
        return try {
            val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
            val content = reader.readText()
            reader.close()
            content
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}