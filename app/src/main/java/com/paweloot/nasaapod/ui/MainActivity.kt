package com.paweloot.nasaapod.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.paweloot.nasaapod.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private val adapter = ApodPhotoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        photoListRecyclerView.layoutManager = LinearLayoutManager(this)
        photoListRecyclerView.adapter = adapter

        viewModel.state.observe(this, Observer { updateState(it) })
    }

    private fun updateState(state: MainViewModel.ViewState) {
        adapter.photoList = state.data
        photoListProgressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE

        if (state.errorMsg.isNotEmpty()) {
            showErrorSnackbar()
        }
    }

    private fun showErrorSnackbar() {
        Snackbar.make(contentMain, "An error occured", Snackbar.LENGTH_INDEFINITE)
            .show()
    }
}