package com.example.myapplication.extensions

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


fun <T> FragmentActivity.collectLastestLyfeCycleFlow(flow: Flow<T>, collect: suspend (T) -> Unit) {
    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(collect)
        }
    }
}

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Activity.showAlertDialog( action: () -> Unit, title:String, message:String) {
    AlertDialog.Builder(this)
        .setTitle("Location Permission Needed")
        .setMessage("This app needs the Location permission, please accept to use location functionality")
        .setPositiveButton("OK") { _, _ ->
            action()
        }.create()
        .show()
}

