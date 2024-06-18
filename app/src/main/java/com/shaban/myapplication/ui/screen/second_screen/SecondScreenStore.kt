package com.shaban.myapplication.ui.screen.second_screen

import com.arkivanov.mvikotlin.core.store.Store
import com.shaban.myapplication.ui.screen.second_screen.SecondScreenStore.State

internal interface SecondScreenStore : Store<Nothing, State, Nothing> {

    // There isn't usage of Intent, so pass Nothing

    data class State(
        val images: List<String> = emptyList(),
        val isLoading: Boolean = false,
    )

    // There isn't usage of Label, so pass Nothing
}
