package ru.santaev.refillpoints.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.santaev.refillpoints.R
import ru.santaev.refillpoints.RefillPointsApplication
import ru.santaev.refillpoints.databinding.ActivityMainBinding
import ru.santaev.refillpoints.di.component.DaggerRefillPointsActivityComponent
import ru.santaev.refillpoints.presenter.RefillPointsMapPresenter
import ru.santaev.refillpoints.view.IRefillPointsView
import ru.santaev.refillpoints.view.adapter.RefillPointsScreenPagerAdapter
import javax.inject.Inject

class RefillPointsActivity : AppCompatActivity(), IRefillPointsView {

    @Inject lateinit var presenter: RefillPointsMapPresenter
    private lateinit var pageAdapter: RefillPointsScreenPagerAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        initPresenter()

        presenter.loadRefillPoints(
            lat = 55.755786,
            lng = 37.617633,
            radius = 1000
        )
    }

    override fun setRefillPoints() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun initUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        pageAdapter = RefillPointsScreenPagerAdapter(supportFragmentManager)
        binding.apply {
            viewPager.adapter = pageAdapter
        }
    }

    private fun initPresenter() {
        DaggerRefillPointsActivityComponent
            .builder()
            .applicationComponent((applicationContext as RefillPointsApplication).applicationComponent)
            .build()
            .inject(this)
        presenter.view = this
    }
}
