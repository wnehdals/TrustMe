package com.jdm.trustme.util

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.jdm.trustme.databinding.DialogEditTextBinding

class EditTextDialog(val title: String = "", val negativeText: String = "취소", val positiveText: String = "확인", val positiveClick: (EditTextDialog, String) -> Unit = { dialog, text -> }, val negativeClick:(EditTextDialog) -> Unit, val isCancel: Boolean = false): DialogFragment() {
    private lateinit var binding: DialogEditTextBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogEditTextBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            editTextDialogTitle.text = title
            editTextDialogCancel.text = negativeText
            editTextDialogInput.text = positiveText
            editTextDialogCancel.setOnClickListener { negativeClick (this@EditTextDialog) }
            editTextDialogInput.setOnClickListener { positiveClick(this@EditTextDialog, editTextDialogEt.text.toString()) }
        }
        isCancelable = isCancel

    }
    fun getEditString(): String {
        return binding.editTextDialogEt.text.toString()
    }
    companion object {
        const val TAG = "EditTextDialog"
    }
}