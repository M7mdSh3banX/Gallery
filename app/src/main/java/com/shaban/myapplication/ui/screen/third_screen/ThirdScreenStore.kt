package com.shaban.myapplication.ui.screen.third_screen

import com.arkivanov.mvikotlin.core.store.Store
import com.shaban.myapplication.ui.screen.third_screen.ThirdScreenStore.State

internal interface ThirdScreenStore : Store<Nothing, State, Nothing> {

    // There isn't usage of Intent, so pass Nothing

    data class State(
        val images: List<Int> = emptyList(),
        val isLoading: Boolean = false,
    )

    // There isn't usage of label, so pass Nothing
}
