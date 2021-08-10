package com.code.wars.view.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.code.wars.R
import com.code.wars.databinding.ActivityChallengeDetailBinding
import com.code.wars.models.Challenge
import com.code.wars.utils.Constants

class ChallengeDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChallengeDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_challenge_detail)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        intent.getParcelableExtra<Challenge>(Constants.argsChallenge)?.let {
           binding.item = it
        } ?: finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            finish()

        return super.onOptionsItemSelected(item)
    }
}