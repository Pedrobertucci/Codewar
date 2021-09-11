package com.code.wars.view

import android.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.*

internal class DebouncingQueryTextListener(
    lifecycle: Lifecycle,
    private val onDebouncingQueryTextChange: (String?) -> Unit
) : SearchView.OnQueryTextListener {
    private var debouncePeriod: Long = 500

    private val coroutineScope = lifecycle.coroutineScope

    private var searchJob: Job? = null

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            newText?.let {
                delay(debouncePeriod)
                onDebouncingQueryTextChange(newText)
            }
        }
        return false
    }
}