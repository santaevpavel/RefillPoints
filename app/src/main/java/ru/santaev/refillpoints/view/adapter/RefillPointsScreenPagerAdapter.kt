package ru.santaev.refillpoints.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import ru.santaev.refillpoints.view.fragment.RefillPointsListFragment
import ru.santaev.refillpoints.view.fragment.RefillPointsMapFragment

class RefillPointsScreenPagerAdapter(
    fragmentManager: FragmentManager
) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getCount(): Int = numberOfPages

    override fun getItem(position: Int): Fragment {
        return when (position) {
            positionMapPage -> RefillPointsMapFragment.create()
            positionListPage -> RefillPointsListFragment.create()
            else -> throw IllegalArgumentException("Unknown page with position = $position")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            positionMapPage -> "Map"
            positionListPage -> "List"
            else -> throw IllegalArgumentException("Unknown page with position = $position")
        }
    }

    companion object {
        private const val numberOfPages = 2
        private const val positionMapPage = 0
        private const val positionListPage = 1
    }
}