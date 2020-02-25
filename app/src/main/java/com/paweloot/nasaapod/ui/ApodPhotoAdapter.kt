package com.paweloot.nasaapod.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.paweloot.nasaapod.R
import com.paweloot.nasaapod.data.model.ApodType
import com.paweloot.nasaapod.data.model.Photo
import com.paweloot.nasaapod.util.YouTubeUtils
import kotlinx.android.synthetic.main.list_item_photo.view.*

class ApodPhotoAdapter(private val onClickCallback: (apodPhoto: Photo) -> Unit) :
    RecyclerView.Adapter<ApodPhotoAdapter.ApodPhotoHolder>() {

    var photoList: List<Photo> = listOf()
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
        holder.bind(photoList[position])
        holder.view.setOnClickListener { onClickCallback(photoList[position]) }
    }

    override fun getItemCount() = photoList.size

    class ApodPhotoHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(apodPhoto: Photo) {
            view.apodPhotoTitle.text = apodPhoto.title
            view.apodPhotoDate.text = apodPhoto.date

            val photoUrl =
                when (apodPhoto.apodType) {
                    ApodType.VIDEO -> {
                        view.apodVideoIcon.visibility = View.VISIBLE
                        YouTubeUtils.buildVideoThumbnailUrl(apodPhoto.url)
                    }
                    else -> apodPhoto.url
                }

            loadPhotoFromUrl(photoUrl)
        }

        private fun loadPhotoFromUrl(photoUrl: String) {
            val circularProgressDrawable =
                CircularProgressDrawable(view.context).apply {
                    centerRadius = 50f
                    strokeWidth = 5f
                    start()
                }

            Glide.with(view.context)
                .load(photoUrl)
                .placeholder(circularProgressDrawable)
                .into(view.apodPhoto)
        }
    }
}