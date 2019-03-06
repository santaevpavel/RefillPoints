package ru.santaev.refillpoints.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.santaev.refillpoints.R
import ru.santaev.refillpoints.RefillPointsApplication
import ru.santaev.refillpoints.databinding.ActivityMainBinding
import ru.santaev.refillpoints.di.component.DaggerRefillPointsActivityComponent
import ru.santaev.refillpoints.presenter.RefillPointsMapPresenter
import ru.santaev.refillpoints.view.IRefillPointsView
import ru.santaev.refillpoints.view.adapter.RefillPointsScreenPagerAdapter
import ru.santaev.refillpoints.view.fragment.RefillPointsMapFragment
import javax.inject.Inject

class RefillPointsActivity : AppCompatActivity(), IRefillPointsView {

    @Inject lateinit var presenter: RefillPointsMapPresenter
    private lateinit var pageAdapter: RefillPointsScreenPagerAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        initPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.loadRefillPoints(
            lat = 55.018803,
            lng = 82.933952,
            radius = 1000
        )
    }

    override fun setRefillPoints(refillPoints: List<RefillPointsMapPresenter.RefillPointViewModel>) {
        val mapFragment: Fragment? = supportFragmentManager
            .fragments
            .firstOrNull { it is RefillPointsMapFragment }
        (mapFragment as? RefillPointsMapFragment)?.setRefillPoints(refillPoints)
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
