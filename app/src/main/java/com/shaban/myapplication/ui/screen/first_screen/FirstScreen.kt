package com.shaban.myapplication.ui.screen.first_screen

import com.arkivanov.decompose.value.Value

interface FirstScreen {
    val models: Value<Model>

    fun onClickNext()

    data class Model(
        val images: List<String>,
        val isLoading: Boolean,
    )

    sealed class Event {
        data object Next : Event()
    }
}