package com.example.myapplication.presentation.base

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

typealias Inflate<VB> = (LayoutInflater, ViewGroup?, Boolean) -> VB

abstract class BaseFragment<T : ViewModel, VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {
    abstract val vm: T
    private var _binding: VB? = null
    abstract val TAG: String
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupVM()
    }


    protected open fun setupUI() = Unit

    protected open fun setupVM() = Unit

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    protected fun getOrientation(): Int {
        return activity?.resources?.configuration?.orientation ?: Configuration.ORIENTATION_PORTRAIT
    }






}