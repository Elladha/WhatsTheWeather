package com.example.geoff.whatstheweather.ui.main.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.geoff.whatstheweather.R
import com.example.geoff.whatstheweather.data.model.Detail
import kotlinx.android.synthetic.main.fragment_details_list.*

/**
 * A fragment representing a list of Items.
 */
class DetailsFragment(private val details: List<Detail>) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = DetailsRecyclerViewAdapter(details)
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val dividerItemDecoration =
            DividerItemDecoration(activity, LinearLayoutManager(context).orientation)
        val applicationContext = activity?.applicationContext
        applicationContext?.let { contextApp ->
            ContextCompat.getDrawable(contextApp, R.drawable.line_divider)?.let {
                dividerItemDecoration.setDrawable(
                    it
                )
            }
        }
        listDetails.addItemDecoration(dividerItemDecoration)
    }

    fun updateDetails(details: List<Detail>) {
        (this.details as ArrayList).clear()
        this.details.addAll(details)
        if (this.listDetails != null) {
            this.listDetails.adapter?.notifyDataSetChanged()
        }
    }
}
