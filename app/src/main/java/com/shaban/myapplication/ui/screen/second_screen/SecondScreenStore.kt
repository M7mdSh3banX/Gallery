package com.shaban.myapplication.ui.screen.second_screen

import com.arkivanov.mvikotlin.core.store.Store
import com.shaban.myapplication.ui.screen.second_screen.SecondScreenStore.Intent
import com.shaban.myapplication.ui.screen.second_screen.SecondScreenStore.State

internal interface SecondScreenStore : Store<Intent, State, Nothing> {

    sealed class Intent {
        data object LoadImagesFromDisk : Intent()
    }

    data class State(
        val images: List<String> = emptyList(),
        val isLoading: Boolean = false,
    )

    // There isn't usage of label, so pass Nothing
}
