package com.code.wars.utils

import java.io.InputStreamReader

object FilePath {

    fun getContent(path: String) : String {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        val content = reader.readText()
        reader.close()
        return content
    }
}