package com.architect.livedataext

import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun CompoundButton.bindChecked(
    lifecycleOwner: LifecycleOwner,
    liveData: LiveData<Boolean>
): Closeable {
    return liveData.bindNotNull(lifecycleOwner) { this.isChecked = it }
}

fun CompoundButton.bindCheckedTwoWay(
    lifecycleOwner: LifecycleOwner,
    liveData: MutableLiveData<Boolean>
): Closeable {
    val readCloseable = liveData.bindNotNull(lifecycleOwner) { value ->
        if (this.isChecked == value) return@bindNotNull

        this.isChecked = value
    }

    val checkListener = CompoundButton.OnCheckedChangeListener { _, isChecked ->
        if (liveData.value == isChecked) return@OnCheckedChangeListener

        liveData.value = isChecked
    }
    setOnCheckedChangeListener(checkListener)

    val writeCloseable = Closeable {
        setOnCheckedChangeListener(null)
    }

    return readCloseable + writeCloseable
}