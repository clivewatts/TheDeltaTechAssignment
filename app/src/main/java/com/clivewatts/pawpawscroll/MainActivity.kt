package com.clivewatts.pawpawscroll

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.clivewatts.pawpawscroll.databinding.ActivityMainBinding
import com.clivewatts.pawpawscroll.ui.DogListItemAdapter
import com.clivewatts.pawpawscroll.ui.viewmodel.DogListViewModel
import com.clivewatts.pawpawscroll.utils.DownloadUtils
import com.clivewatts.pawpawscroll.utils.GridSpacingItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView

import androidx.annotation.NonNull
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.clivewatts.pawpawscroll.utils.FullPhotoPopupWindow


class MainActivity : AppCompatActivity() {

    private val viewModel: DogListViewModel by viewModel { parametersOf() }
    var rownCount = 0

    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : DogListItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        adapter = DogListItemAdapter(mutableListOf(), this, viewModel)
//        binding.doggoList.addItemDecoration(GridSpacingItemDecoration(4, 10, true))
        binding.doggoList.adapter = adapter

        binding.doggoList.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        initScrollListener()
        installSplashScreen()

        setContentView(binding.root)
        configureViewModelBindings()

    }

    private fun configureViewModelBindings() {
        viewModel.doggoList.observe(this) {
            rownCount = it?.size?.minus(9) ?: 0
            adapter.updateDataSet(it)
        }

        viewModel.downloadImage.observe(this) {
            DownloadUtils.downloadFile("${System.currentTimeMillis()}_doggo.jpg", "Downloading your doggo", it, this)
        }

        viewModel.shareUrl.observe(this) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, it)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        viewModel.viewImage.observe(this) {
            FullPhotoPopupWindow(this, R.layout.fullscreen_popup_image, binding.doggoList, it, null)
        }
    }
    private fun initScrollListener() {
        binding.doggoList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val gridLayoutManager = recyclerView.layoutManager as StaggeredGridLayoutManager?
                gridLayoutManager?.findLastCompletelyVisibleItemPositions(null).apply {
                    if (this?.get(1) ?: -1 == rownCount)
                        viewModel.loadMoreDoggos(50)
                }
            }
        })
    }



}