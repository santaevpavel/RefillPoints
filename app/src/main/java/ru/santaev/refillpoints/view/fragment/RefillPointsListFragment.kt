package ru.santaev.refillpoints.view.fragment

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.santaev.refillpoints.R
import ru.santaev.refillpoints.databinding.FragmentRefillPointsListBinding
import ru.santaev.refillpoints.databinding.RefillPointsListItemBinding
import ru.santaev.refillpoints.domain.dto.RefillPointDto
import ru.santaev.refillpoints.presenter.RefillPointsListPresenter
import ru.santaev.refillpoints.presenter.RefillPointsListPresenter.RefillPointViewModel
import ru.santaev.refillpoints.view.IRefillPointsListView
import ru.santaev.refillpoints.view.activity.RefillPointDetailsActivity
import ru.santaev.refillpoints.view.activity.RefillPointsActivity
import ru.santaev.refillpoints.view.adapter.SimpleRecyclerViewAdapter
import javax.inject.Inject


class RefillPointsListFragment : Fragment(), IRefillPointsListView {

    @Inject
    lateinit var presenter: RefillPointsListPresenter
    private lateinit var binding: FragmentRefillPointsListBinding
    private val adapter = SimpleRecyclerViewAdapter<RefillPointViewModel, RefillPointsListItemBinding>(
        layoutResId = R.layout.refill_points_list_item,
        binder = { item, holder ->
            holder.binding.apply {
                point = item
                name.setTypeface(null, if (item.isViewed) Typeface.NORMAL else Typeface.BOLD)
                address.setTypeface(null, if (item.isViewed) Typeface.NORMAL else Typeface.BOLD)
                root.setOnClickListener { openRefillPointDetails(item) }
            }

        },
        viewCreator = {}
    )

    fun setRefillPoints(refillPoints: List<RefillPointDto>) {
        presenter.setRefillPoints(refillPoints)
    }

    override fun showRefillPoints(refillPoints: List<RefillPointViewModel>) {
        adapter.items = refillPoints
    }

    override fun openRefillPointDetails(point: RefillPointViewModel) {
        val context = context ?: return
        startActivity(RefillPointDetailsActivity.createIntent(context, point.id))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_refill_points_list, container, false)
        initUi()
        return binding.root
    }

    private fun initUi() {
        binding.apply {
            list.adapter = adapter
            list.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    private fun initPresenter() {
        (activity as RefillPointsActivity)
            .component
            .inject(this)
        presenter.view = this
    }

    companion object {

        fun create(): RefillPointsListFragment = RefillPointsListFragment()
    }
}