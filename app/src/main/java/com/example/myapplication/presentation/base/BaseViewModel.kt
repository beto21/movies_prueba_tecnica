package com.example.myapplication.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
open class BaseViewModel : ViewModel() {


    companion object {
        val TAG: String = this::class.java.simpleName
    }





}