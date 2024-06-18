package com.shaban.myapplication.ui.screen.first_screen

import com.arkivanov.mvikotlin.core.store.Store
import com.shaban.myapplication.ui.screen.first_screen.FirstScreenStore.State


internal interface FirstScreenStore : Store<Nothing, State, Nothing> {

    // There isn't usage of Intent, so pass Nothing

    data class State(
        val images: List<String> = emptyList(),
        val isLoading: Boolean = false,
    )

    // There isn't usage of label, so pass Nothing
}
