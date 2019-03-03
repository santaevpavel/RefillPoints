package ru.santaev.refillpoints.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.santaev.refillpoints.R
import ru.santaev.refillpoints.databinding.FragmentRefillPointsMapBinding

class RefillPointsMapFragment : Fragment() {

    private lateinit var binding: FragmentRefillPointsMapBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_refill_points_map, container, false)
        return binding.root
    }

    companion object {

        fun create(): RefillPointsMapFragment = RefillPointsMapFragment()
    }
}