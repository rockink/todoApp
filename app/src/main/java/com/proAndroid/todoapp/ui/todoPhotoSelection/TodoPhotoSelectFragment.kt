package com.proAndroid.todoapp.ui.todoPhotoSelection

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.proAndroid.todoapp.EditTodoComponent
import com.proAndroid.todoapp.asTodoApplication
import com.proAndroid.todoapp.ui.editTodo.EditTodoFragment
import com.proAndroid.todoapp.ui.editTodo.EditTodoViewModel
import com.proAndroid.todoapp.ui.photoGallery.Photo
import com.proAndroid.todoapp.ui.photoGallery.PhotoGalleryFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoPhotoSelectFragment : PhotoGalleryFragment() {

    private lateinit var editTodoComponent: EditTodoComponent
    private val editViewModel by viewModels<EditTodoViewModel> {
        editTodoComponent.editTodoViewModelFactory()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        editTodoComponent = requireActivity().application.asTodoApplication()
            .appComponent
            .editTodoComponentBuilder()
            .bindTodoId(deBundalize(requireArguments()))
            .build()
    }

    override fun onClick(photo: Photo) {
        super.onClick(photo)
        editViewModel.getTodo()
            .observe(viewLifecycleOwner, Observer { todo ->
                lifecycleScope.launch(Dispatchers.Default) {
                    editViewModel.updateTodo(
                        todo.copy(
                            imageResource = photo.uri
                        )
                    )
                    // go back to previous screen
                    findNavController().popBackStack()
                }
            })
    }


    companion object {
        val ID = "ID"
        fun bundalize(id: Int) = Bundle().apply {
            putInt(ID, id)
        }

        fun deBundalize(bundle: Bundle) = bundle.getInt(ID)
    }

}