package com.clivewatts.pawpawscroll.di

import com.clivewatts.pawpawscroll.data.repository.RepositoryGoodBoysImpl
import com.clivewatts.pawpawscroll.data.service.getDogCeoClient
import com.clivewatts.pawpawscroll.network.RetrofitInstance
import com.clivewatts.pawpawscroll.ui.viewmodel.DogListViewModel
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { Gson() }
    single { RetrofitInstance.retrofit() }
    single { getDogCeoClient(get()) }
    single { RepositoryGoodBoysImpl(get()) }
    viewModel { DogListViewModel(androidContext()) }
}