package ru.santaev.refillpoints.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import ru.santaev.refillpoints.R
import ru.santaev.refillpoints.RefillPointsApplication
import ru.santaev.refillpoints.databinding.ActivityRefillPointDetailsBinding
import ru.santaev.refillpoints.di.component.DaggerRefillPointDetailsActivityComponent
import ru.santaev.refillpoints.presenter.RefillPointDetailsPresenter
import ru.santaev.refillpoints.view.IRefillPointDetailsView
import javax.inject.Inject

class RefillPointDetailsActivity : AppCompatActivity(), IRefillPointDetailsView {

    @Inject lateinit var presenter: RefillPointDetailsPresenter
    private lateinit var binding: ActivityRefillPointDetailsBinding
    private var refillPointId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadArguments()
        initPresenter()
        initUi()
    }

    override fun showRefillPoint(refillPoint: RefillPointDetailsPresenter.RefillPointDetailsViewModel) {
        binding.content.apply {
            name.value.text = refillPoint.partnerName
            name.title.text = "Partner"
            address.value.text = refillPoint.fullAddress
            address.title.text = "Address"
            phone.value.text = refillPoint.phones
            phone.title.text = "Phone"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    private fun loadArguments() {
        refillPointId = intent.extras?.getLong(keyInput, -1)
            ?: throw IllegalStateException("No activity argument")
        if (refillPointId < 0) {
            throw IllegalStateException("No activity argument")
        }
    }

    private fun initUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_refill_point_details)
    }

    private fun initPresenter() {
        DaggerRefillPointDetailsActivityComponent
            .builder()
            .applicationComponent((applicationContext as RefillPointsApplication).applicationComponent)
            .build()
            .apply {
                inject(this@RefillPointDetailsActivity)
            }
        presenter.view = this
        presenter.setRefillPointId(refillPointId)
    }

    companion object {

        private const val keyInput = "keyInput"

        fun createIntent(context: Context, refillPointId: Long): Intent {
            return Intent(context, RefillPointDetailsActivity::class.java).apply {
                putExtra(keyInput, refillPointId)
            }
        }
    }
}
