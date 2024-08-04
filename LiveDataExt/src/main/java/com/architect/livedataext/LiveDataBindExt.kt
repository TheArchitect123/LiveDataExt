package com.architect.livedataext

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.map

fun <T> LiveData<T>.bind(lifecycleOwner: LifecycleOwner, observer: (T?) -> Unit): Closeable {
    observer(value)
    val androidObserver = Observer<T> { value ->
        observer(value)
    }
    this.observe(lifecycleOwner, androidObserver)

    return Closeable {
        this.removeObserver(androidObserver)
    }
}

fun <T> LiveData<T>.bindNotNull(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit): Closeable {
    return bind(lifecycleOwner) { value ->
        if (value == null) return@bind

        observer(value)
    }
}

fun View.bindVisibleOrGone(
    lifecycleOwner: LifecycleOwner,
    liveData: LiveData<Boolean>
): Closeable {
    return liveData.bindNotNull(lifecycleOwner) { value ->
        this.visibility = if (value) View.VISIBLE else View.GONE
    }
}

fun View.bindVisibleOrInvisible(
    lifecycleOwner: LifecycleOwner,
    liveData: LiveData<Boolean>
): Closeable {
    return liveData.bindNotNull(lifecycleOwner) { value ->
        this.visibility = if (value) View.VISIBLE else View.INVISIBLE
    }
}

fun View.bindEnabled(
    lifecycleOwner: LifecycleOwner,
    liveData: LiveData<Boolean>
): Closeable {
    return liveData.bindNotNull(lifecycleOwner) { this.isEnabled = it }
}

fun <T> MutableLiveData<T>.readOnly(): LiveData<T> = this

fun <T : Throwable> LiveData<T>.throwableMessage(
    mapper: (Throwable) -> String = { it.message.orEmpty() }
): LiveData<String> = map(mapper)


