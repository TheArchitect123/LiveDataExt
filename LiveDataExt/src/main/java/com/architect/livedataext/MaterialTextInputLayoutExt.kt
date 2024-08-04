package com.architect.livedataext


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.bindError(
    lifecycleOwner: LifecycleOwner,
    liveData: LiveData<String?>
): Closeable {
    return liveData.bind(lifecycleOwner) {
        error = it
        isErrorEnabled = it != null
    }
}