package ru.santaev.refillpoints.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.santaev.refillpoints.R
import ru.santaev.refillpoints.RefillPointsApplication
import ru.santaev.refillpoints.databinding.ActivityMainBinding
import ru.santaev.refillpoints.di.component.DaggerRefillPointsActivityComponent
import ru.santaev.refillpoints.di.component.RefillPointsActivityComponent
import ru.santaev.refillpoints.presenter.RefillPointsMapPresenter
import ru.santaev.refillpoints.view.IRefillPointsView
import ru.santaev.refillpoints.view.adapter.RefillPointsScreenPagerAdapter
import javax.inject.Inject

class RefillPointsActivity : AppCompatActivity(), IRefillPointsView {

    lateinit var component: RefillPointsActivityComponent
    @Inject lateinit var presenter: RefillPointsMapPresenter
    private lateinit var pageAdapter: RefillPointsScreenPagerAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUi()
        initPresenter()
    }

    /*override fun showRefillPoints(refillPoints: List<RefillPointsMapPresenter.RefillPointViewModel>) {
        val mapFragment: Fragment? = supportFragmentManager
            .fragments
            .firstOrNull { it is RefillPointsMapFragment }
        (mapFragment as? RefillPointsMapFragment)?.showRefillPoints(refillPoints)
    }*/

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
            .apply {
                component = this@apply
                inject(this@RefillPointsActivity)
            }

        presenter.view = this
    }
}
