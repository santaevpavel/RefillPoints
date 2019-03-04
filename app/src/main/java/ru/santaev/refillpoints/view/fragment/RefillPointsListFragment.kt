package ru.santaev.refillpoints.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ru.santaev.refillpoints.R
import ru.santaev.refillpoints.databinding.FragmentRefillPointsListBinding

class RefillPointsListFragment : Fragment() {

    private lateinit var binding: FragmentRefillPointsListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_refill_points_list, container, false)
        return binding.root
    }

    companion object {

        fun create(): RefillPointsListFragment = RefillPointsListFragment()
    }
}