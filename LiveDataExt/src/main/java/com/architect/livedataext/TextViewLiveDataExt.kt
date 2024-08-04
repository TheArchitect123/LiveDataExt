package com.architect.livedataext

import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

fun TextView.bindText(
    lifecycleOwner: LifecycleOwner,
    liveData: LiveData<String>
): Closeable {
    return liveData.bindNotNull(lifecycleOwner) { this.text = it }
}