package com.paweloot.nasaapod.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.paweloot.nasaapod.R
import com.paweloot.nasaapod.data.model.Apod
import com.paweloot.nasaapod.data.model.ApodType
import kotlinx.android.synthetic.main.fragment_apod_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApodListFragment : Fragment() {

    private val viewModel: ApodViewModel by viewModel()

    private val adapter = ApodPhotoAdapter(this::onApodClicked).apply {
        setHasStableIds(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_apod_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        photoListRecyclerView.layoutManager = LinearLayoutManager(context)
        photoListRecyclerView.adapter = adapter

        // Disable default animations to prevent blinking on expand/collapse
        (photoListRecyclerView.itemAnimator as DefaultItemAnimator).supportsChangeAnimations = false

        viewModel.state.observe(viewLifecycleOwner, Observer { updateState(it) })
    }

    private fun onApodClicked(apod: Apod) {
        if (apod.apodType == ApodType.VIDEO) {
            val youtubeIntent = Intent(Intent.ACTION_VIEW, Uri.parse(apod.url))
            startActivity(youtubeIntent)
        }
    }

    private fun updateState(state: ApodViewModel.ViewState) {
        adapter.apodList = state.data
        photoListProgressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        if (state.errorMsg.isNotEmpty()) {
            showErrorSnackbar()
        }
    }

    private fun showErrorSnackbar() {
        Snackbar.make(
            fragmentApodListContent,
            getString(R.string.snackbar_error_msg),
            Snackbar.LENGTH_INDEFINITE
        )
            .show()
    }
}