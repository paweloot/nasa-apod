package com.paweloot.nasaapod.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.paweloot.nasaapod.R
import com.paweloot.nasaapod.data.model.Apod
import com.paweloot.nasaapod.data.model.ApodType
import kotlinx.android.synthetic.main.list_item_photo.view.*

class ApodPhotoAdapter(private val onClickCallback: (apod: Apod) -> Unit) :
    RecyclerView.Adapter<ApodPhotoAdapter.ApodPhotoHolder>() {

    private var expandedPosition = RecyclerView.NO_POSITION
    private var previousExpandedPosition = RecyclerView.NO_POSITION

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

        val isExpanded = position == expandedPosition
        holder.itemView.expandableExplanation.visibility =
            if (isExpanded) View.VISIBLE else View.GONE

        if (isExpanded)
            previousExpandedPosition = expandedPosition

        holder.itemView.apodTitleView.setOnClickListener {
            expandedPosition = if (isExpanded) RecyclerView.NO_POSITION else position
            holder.itemView.expandArrow.setImageResource(
                if (isExpanded) R.drawable.ic_baseline_keyboard_arrow_down_24
                else R.drawable.ic_baseline_keyboard_arrow_up_24
            )

            TransitionManager.beginDelayedTransition(holder.itemView.apodCardView)
            notifyItemChanged(previousExpandedPosition)
            notifyItemChanged(position)
        }
    }

    override fun getItemId(position: Int): Long {
        return apodList[position].hashCode().toLong()
    }

    override fun getItemCount() = apodList.size

    class ApodPhotoHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(apod: Apod, onClickCallback: (apod: Apod) -> Unit) {
            with(itemView) {
                apodPhotoTitle.text = apod.title
                apodPhotoDate.text = apod.date
                apodExplanation.text = apod.explanation

                apodPhoto.setOnClickListener { onClickCallback(apod) }
                setVideoIconVisibility(apod)
            }

            loadPhotoFromUrl(apod.thumbnailUrl)
        }

        private fun setVideoIconVisibility(apod: Apod) {
            itemView.apodVideoIcon.visibility =
                if (apod.apodType == ApodType.VIDEO) View.VISIBLE
                else View.GONE
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
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(circularProgressDrawable)
                .into(itemView.apodPhoto)
        }
    }
}