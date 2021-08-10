package com.code.wars.utils

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


object Converter {

    @BindingAdapter( "languages")
    @JvmStatic
    fun bindLanguages(view: TextView, languages: List<String>) {
        view.text = languages.joinToString()
    }

    @SuppressLint("SimpleDateFormat")
    @BindingAdapter("date")
    @JvmStatic
    fun bindDate(view: TextView, date: String) {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        lateinit var convertedDate: Date
        try {
            convertedDate = sdf.parse(date)
            view.text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(convertedDate)
        } catch (e: ParseException) {
            e.printStackTrace()
            view.text  = ""
        }
    }


    @SuppressLint("SimpleDateFormat")
    @BindingAdapter("data")
    @JvmStatic
    fun fillData(view: TextView, data: String?) {
        view.text = data ?: ""
    }
}