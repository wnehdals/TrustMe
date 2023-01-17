package com.jdm.trustme.ui.write

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import com.jdm.trustme.R
import com.jdm.trustme.base.BaseFragment
import com.jdm.trustme.databinding.FragmentSelectStoreBinding
import com.jdm.data.model.entity.Store
import com.jdm.trustme.util.ColorUtil
import com.jdm.trustme.view.EditTextDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectStoreFragment : BaseFragment<FragmentSelectStoreBinding>() {
    override val layoutId: Int
        get() = R.layout.fragment_select_store
    private val storeListAdapter = StoreListAdapter()
    private val viewModel: WriteViewModel by activityViewModels()
    override fun initView() {
        storeListAdapter.apply {
            onClick = this@SelectStoreFragment::onClickStoreItem
        }
        binding.selectStoreRv.adapter = storeListAdapter
        viewModel.getAllStoreList()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        backButtonCallBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (requireActivity() as WriteActivity).backPressedFragment(SelectStoreFragment.TAG)
                viewModel.selectedGallery.clear()
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, backButtonCallBack)
    }

    override fun initEvent() {
        with(binding) {
            selectStoreBackButton.setOnClickListener {
                (requireActivity() as WriteActivity).backPressedFragment(SelectStoreFragment.TAG)
                viewModel.selectedGallery.clear()
                requireActivity().finish()
            }
        }

    }

    override fun subscribe() {
        viewModel.storeData.observe(viewLifecycleOwner) {
            storeListAdapter.submitList(it)
        }
    }
    private fun onClickStoreItem(store: Store) {
        if(store.id == -1L) {
            val editTextDialog = EditTextDialog(title = getString(R.string.str_please_input_store_name), positiveText = getString(R.string.str_input),
                positiveClick = { dialog, name ->
                    val newStore = Store(id = 0, name = name, img = ColorUtil.getRandomIdx())
                    viewModel.insertStore(newStore)
                    dialog.dismiss()

            }, negativeClick = {
                it.dismiss()
            }, isCancel = false)
            editTextDialog.show(parentFragmentManager, EditTextDialog.TAG)
        } else {
            viewModel.selectStore = store
            (requireActivity() as WriteActivity).addFragment(WriteFragment.TAG)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SelectStoreFragment()
        const val TAG = "SelectStoreFragment"
    }
}