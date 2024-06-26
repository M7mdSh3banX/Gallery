package com.shaban.myapplication.ui.screen.second_screen

import com.arkivanov.decompose.value.Value

interface SecondScreen {
    val models: Value<Model>

    fun onClickNext()

    fun onClickBack()

    data class Model(
        val images: List<String>,
        val isLoading: Boolean,
    )

    sealed class Event {
        data object Next : Event()
        data object Back : Event()
    }
}