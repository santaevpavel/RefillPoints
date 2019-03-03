package ru.santaev.refillpoints.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.santaev.refillpoints.R
import ru.santaev.refillpoints.databinding.ActivityMainBinding
import ru.santaev.refillpoints.view.adapter.RefillPointsScreenPagerAdapter

class RefillPointsActivity : AppCompatActivity() {

    private lateinit var pageAdapter: RefillPointsScreenPagerAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
    }

    private fun initUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        pageAdapter = RefillPointsScreenPagerAdapter(supportFragmentManager)
        binding.apply {
            viewPager.adapter = pageAdapter
        }
    }

}
