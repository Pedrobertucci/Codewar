package com.code.wars.view.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.code.wars.R
import com.code.wars.databinding.ActivityCompletedDetailBinding
import com.code.wars.models.Completed
import com.code.wars.utils.Constants

class CompleteDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCompletedDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_completed_detail)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        
        intent.getParcelableExtra<Completed>(Constants.argsCompleted)?.let { it ->
            binding.item = it
        } ?: finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()

        return super.onOptionsItemSelected(item)
    }
}