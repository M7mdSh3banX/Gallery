package com.shaban.myapplication.ui.screen.third_screen

import com.arkivanov.decompose.value.Value

interface ThirdScreen {
    val models: Value<Model>

    fun onClickBack()

    data class Model(
        val images: List<Int>
    )

    sealed class Output {
        data object Back : Output()
    }
}