package com.proAndroid.todoapp.ui.photoGallery

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.proAndroid.todoapp.R
import com.proAndroid.todoapp.asTodoApplication

/**
 * A fragment representing a list of Items.
 */
open class PhotoGalleryFragment : Fragment(), PhotoRecyclerViewAdapter.InteractionListener {

    private lateinit var mAdapter: PhotoRecyclerViewAdapter
    private var columnCount = 3
    private val TAG = this::class.java.canonicalName

    private val photoViewModel by viewModels<PhotoViewModel> {
        requireActivity().application.asTodoApplication()
            .appComponent.photoViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAdapter = PhotoRecyclerViewAdapter(
            mutableListOf(),
            Glide.with(this),
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo_gallery_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = GridLayoutManager(context, columnCount)
                adapter = mAdapter
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photoViewModel.photos
            .observe(viewLifecycleOwner, Observer {
                mAdapter.update(it)
            })
    }

    override fun onClick(photo: Photo) {
        Log.d(TAG, "onClick: $photo")
    }
}