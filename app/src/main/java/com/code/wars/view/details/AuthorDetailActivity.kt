package com.code.wars.view.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.code.wars.R
import com.code.wars.databinding.ActivityAuthorDetailBinding
import com.code.wars.models.Challenge
import com.code.wars.utils.Constants

class AuthorDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthorDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_author_detail)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        intent.getParcelableExtra<Challenge>(Constants.argsAuthored)?.let { it ->
            binding.item = it
        } ?: finish()
    }
}