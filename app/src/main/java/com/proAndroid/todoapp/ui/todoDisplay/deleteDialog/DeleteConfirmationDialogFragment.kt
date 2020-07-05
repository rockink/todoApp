package com.proAndroid.todoapp.ui.todoDisplay.deleteDialog

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.proAndroid.todoapp.R
import com.proAndroid.todoapp.asTodoApplication
import com.proAndroid.todoapp.service.RemoteTodoService
import kotlinx.android.synthetic.main.fragment_delete_confirmation_dialog.*
import kotlinx.android.synthetic.main.fragment_delete_confirmation_dialog.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeleteConfirmationDialogFragment : DialogFragment() {

    private lateinit var todoService: RemoteTodoService

    override fun onAttach(context: Context) {
        super.onAttach(context)
        todoService = requireActivity().application.asTodoApplication()
            .appComponent
            .todoService()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete_confirmation_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.yesButton.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.Default) {
                    todoService.deleteTodo(deBundalize(requireArguments())) // pass id
                }
                dismiss()
            }
        }

        view.noButton.setOnClickListener {
            //do nothing
            dismiss()
        }
    }

    companion object {
        val ID = "ID"
        fun bundalize(id: Int) = Bundle().apply {
            putInt(ID, id)
        }

        fun deBundalize(bundle: Bundle) = bundle.getInt(ID)
    }
}