package ru.santaev.refillpoints.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.santaev.refillpoints.R
import ru.santaev.refillpoints.databinding.ActivityRefillPointDetailsBinding
import ru.santaev.refillpoints.domain.dto.RefillPointDto
import ru.santaev.refillpoints.view.IRefillPointsView
import ru.santaev.refillpoints.view.fragment.RefillPointsListFragment
import java.io.Serializable

class RefillPointDetailsActivity : AppCompatActivity(), IRefillPointsView {

    private lateinit var binding: ActivityRefillPointDetailsBinding
    private lateinit var refillPoint: RefillPointDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        refillPoint = intent.extras?.getSerializable(keyInput) as RefillPointDetails?
            ?: throw IllegalStateException("No activity argument")
        initUi()
    }

    override fun passRefillPoints(refillPoints: List<RefillPointDto>) {
        val listFragment: Fragment? = supportFragmentManager
            .fragments
            .firstOrNull { it is RefillPointsListFragment }
        (listFragment as? RefillPointsListFragment)?.setRefillPoints(refillPoints)
    }

    private fun initUi() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_refill_point_details)
        binding.content.apply {
            name.value.text = refillPoint.partnerName
            name.title.text = "Partner"
            address.value.text = refillPoint.fullAddress
            address.title.text = "Address"
            phone.value.text = refillPoint.phones
            phone.title.text = "Phone"
        }
    }

    class RefillPointDetails(
        val id: Long,
        val partnerName: String,
        val location: Location,
        val workHours: String?,
        val phones: String?,
        val addressInfo: String?,
        val fullAddress: String
    ): Serializable

    data class Location(
        val lat: Double,
        val lng: Double
    ): Serializable

    companion object {

        private const val keyInput = "keyInput"

        fun createIntent(context: Context, refillPoint: RefillPointDetails): Intent {
            return Intent(context, RefillPointDetailsActivity::class.java).apply {
                putExtra(keyInput, refillPoint)
            }
        }
    }
}
