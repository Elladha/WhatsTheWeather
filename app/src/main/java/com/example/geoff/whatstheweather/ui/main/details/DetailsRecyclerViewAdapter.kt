package com.example.geoff.whatstheweather.ui.main.details


import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.geoff.whatstheweather.R
import com.example.geoff.whatstheweather.data.model.Detail
import com.example.geoff.whatstheweather.inflate
import com.example.geoff.whatstheweather.ui.main.details.dummy.DummyContent.DummyItem
import kotlinx.android.synthetic.main.fragment_details.view.*

/**
 * [RecyclerView.Adapter] that can display a [DummyItem]
 */
class DetailsRecyclerViewAdapter(
    private val mValues: List<Detail>
) : RecyclerView.Adapter<DetailsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = parent.inflate(R.layout.fragment_details, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
//        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.FRANCE)
//        val hourSunrise = Date(item. * 1000)
//        val hourSunset = Date(item.sunset * 1000)

        val img = item.img
        val title = item.detailTitle
        val value = item.detailValue
//        Log.d("Weather", hourSunrise.toString() + hourSunset.toString())

        holder.imgDetail.setImageDrawable(img)
        holder.tvDetailTitle.text = title
        holder.tvDetailValue.text = value.toString()

        with(holder.mView) {
            tag = item
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val imgDetail: ImageView = mView.imgDetails
        val tvDetailTitle: TextView = mView.tvDetailsTitle
        val tvDetailValue: TextView = mView.tvDetailsValue

        override fun toString(): String {
            return super.toString() + " '" + tvDetailTitle.text + "'"
        }
    }
}
