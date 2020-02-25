package com.paweloot.nasaapod.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.paweloot.nasaapod.R
import com.paweloot.nasaapod.data.model.Apod
import com.paweloot.nasaapod.data.model.ApodType
import com.paweloot.nasaapod.util.YouTubeUtils
import kotlinx.android.synthetic.main.list_item_photo.view.*

class ApodPhotoAdapter(private val onClickCallback: (apod: Apod) -> Unit) :
    RecyclerView.Adapter<ApodPhotoAdapter.ApodPhotoHolder>() {

    var apodList: List<Apod> = listOf()
        set(value) {
            if (value != field) {
                field = value
                notifyDataSetChanged()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodPhotoHolder {
        return ApodPhotoHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_photo, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ApodPhotoHolder, position: Int) {
        holder.bind(apodList[position], onClickCallback)
    }

    override fun getItemCount() = apodList.size

    class ApodPhotoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(apod: Apod, onClickCallback: (apod: Apod) -> Unit) {
            with(itemView) {
                apodPhotoTitle.text = apod.title
                apodPhotoDate.text = apod.date
                setOnClickListener { onClickCallback(apod) }
            }

            val photoUrl =
                when (apod.apodType) {
                    ApodType.VIDEO -> {
                        itemView.apodVideoIcon.visibility = View.VISIBLE
                        YouTubeUtils.buildVideoThumbnailUrl(apod.url)
                    }
                    else -> {
                        itemView.apodVideoIcon.visibility = View.GONE
                        apod.url
                    }
                }

            loadPhotoFromUrl(photoUrl)
        }

        private fun loadPhotoFromUrl(photoUrl: String) {
            val circularProgressDrawable =
                CircularProgressDrawable(itemView.context).apply {
                    centerRadius = 50f
                    strokeWidth = 5f
                    start()
                }

            Glide.with(itemView.context)
                .load(photoUrl)
                .placeholder(circularProgressDrawable)
                .into(itemView.apodPhoto)
        }
    }
}