package com.architect.livedataext

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData

fun EditText.bindTextTwoWay(
    lifecycleOwner: LifecycleOwner,
    liveData: MutableLiveData<String>
): Closeable {
    val readCloseable = liveData.bindNotNull(lifecycleOwner) { value ->
        if (this.text.toString() == value) return@bindNotNull

        this.setText(value)
    }

    val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val str = s.toString()
            if (str == liveData.value) return

            liveData.value = str
        }

        override fun afterTextChanged(s: Editable?) = Unit
    }
    this.addTextChangedListener(watcher)

    val writeCloseable = Closeable {
        this.removeTextChangedListener(watcher)
    }

    return readCloseable + writeCloseable
}


fun EditText.bindTextOneWay(
    lifecycleOwner: LifecycleOwner,
    liveData: MutableLiveData<String>
): Closeable {
    val readCloseable = liveData.bindNotNull(lifecycleOwner) { value ->
        if (this.text.toString() == value) return@bindNotNull

        this.setText(value)
    }

    val watcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val str = s.toString()
            if (str == liveData.value) return

            liveData.value = str
        }

        override fun afterTextChanged(s: Editable?) = Unit
    }
    this.addTextChangedListener(watcher)

    val writeCloseable = Closeable {
        this.removeTextChangedListener(watcher)
    }

    return readCloseable + writeCloseable
}

