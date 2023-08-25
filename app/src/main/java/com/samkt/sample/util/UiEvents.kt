package com.samkt.sample.util

sealed class UiEvents {
    object PopBackStack : UiEvents()
    data class ShowSnackBar(val message: String) : UiEvents()
}
