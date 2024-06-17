package com.shaban.myapplication.ui.screen.first_screen

import com.arkivanov.decompose.value.Value

interface FirstScreen {
    val models: Value<Model>

    fun onClickNext()

    data class Model(
        val images: List<String>
    )

    sealed class Output {
        data object Next : Output()
    }
}