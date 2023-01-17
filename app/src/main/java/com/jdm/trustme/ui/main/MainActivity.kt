package com.jdm.trustme.ui.main

import android.content.Intent
import androidx.activity.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import com.jdm.trustme.R
import com.jdm.trustme.base.BaseActivity
import com.jdm.trustme.databinding.ActivityMainBinding
import com.jdm.data.model.entity.Store
import com.jdm.data.model.entity.response.Feed
import com.jdm.trustme.ui.main.adapter.FeedAdapter
import com.jdm.trustme.ui.main.adapter.SelectedStoreAdapter
import com.jdm.trustme.ui.write.WriteActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val layoutId: Int
        get() = R.layout.activity_main
    private val viewModel: MainViewModel by viewModels()
    private val selectedStoreAdapter by lazy { SelectedStoreAdapter(this, this::onClickSelectedStoreItem) }
    private val feedAdapter by lazy { FeedAdapter(this,this::onClickFeedItem ) }
    private val concatAdapter = ConcatAdapter()

    override fun subscribe() {
        with(viewModel) {
            storeData.observe(this@MainActivity) {
                selectedStoreAdapter.addData(it)
            }
            feedData.observe(this@MainActivity) {
                feedAdapter.addData(it)
            }
        }

    }

    override fun initView() {
        viewModel.getAllStoreList()
        viewModel.getFeedList()
        with(binding) {
            mainStoreRv.adapter = selectedStoreAdapter
            mainFoodRv.adapter = feedAdapter
        }

    }


    override fun initEvent() {
        with(binding) {
            fab.setOnClickListener {
                Intent(this@MainActivity, WriteActivity::class.java).also { startActivity(it) }
            }
        }
    }
    fun onClickSelectedStoreItem(item: Store, pos: Int) {

    }
    fun onClickFeedItem(item: Feed, pos: Int) {

    }
}