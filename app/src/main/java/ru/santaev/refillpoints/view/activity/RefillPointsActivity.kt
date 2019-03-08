package ru.santaev.refillpoints.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.santaev.refillpoints.R
import ru.santaev.refillpoints.RefillPointsApplication
import ru.santaev.refillpoints.databinding.ActivityMainBinding
import ru.santaev.refillpoints.di.component.DaggerRefillPointsActivityComponent
import ru.santaev.refillpoints.di.component.RefillPointsActivityComponent
import ru.santaev.refillpoints.domain.dto.RefillPointDto
import ru.santaev.refillpoints.presenter.RefillPointsPresenter
import ru.santaev.refillpoints.view.IRefillPointsView
import ru.santaev.refillpoints.view.adapter.RefillPointsScreenPagerAdapter
import ru.santaev.refillpoints.view.fragment.RefillPointsListFragment
import javax.inject.Inject

class RefillPointsActivity : AppCompatActivity(), IRefillPointsView {

    lateinit var component: RefillPointsActivityComponent
    @Inject lateinit var presenter: RefillPointsPresenter
    private lateinit var pageAdapter: RefillPointsScreenPagerAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        initPresenter()
        super.onCreate(savedInstanceState)
        initUi()
    }

    override fun passRefillPoints(refillPoints: List<RefillPointDto>) {
        val listFragment: Fragment? = supportFragmentManager
            .fragments
            .firstOrNull { it is RefillPointsListFragment }
        (listFragment as? RefillPointsListFragment)?.setRefillPoints(refillPoints)
    }

    private fun initUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        pageAdapter = RefillPointsScreenPagerAdapter(supportFragmentManager)
        binding.apply {
            viewPager.adapter = pageAdapter
            tabLayout.setupWithViewPager(viewPager)
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
