package com.proAndroid.todoapp.ui.photoGallery

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker.PERMISSION_GRANTED
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

    private val PERMISSION_REQUEST_CODE = 100

    private val photoViewModel by viewModels<PhotoViewModel> {
        requireActivity().application.asTodoApplication()
            .appComponent.photoViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val hasPermission = ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
        if (hasPermission == PERMISSION_GRANTED) {
            Log.d(TAG, "onCreate: we have the permission")
        } else {
            // request a permission
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE)
        }

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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            //lets look at the result
            if (grantResults[0] == PERMISSION_GRANTED) {
                // we have permission
                photoViewModel.reloadImages()
            }
        }
    }

    override fun onClick(photo: Photo) {
        Log.d(TAG, "onClick: $photo")
    }
}