package com.shaban.myapplication.ui.screen.first_screen

import com.arkivanov.mvikotlin.core.store.Store
import com.shaban.myapplication.ui.screen.first_screen.FirstScreenStore.Intent
import com.shaban.myapplication.ui.screen.first_screen.FirstScreenStore.State

internal interface FirstScreenStore : Store<Intent, State, Nothing> {

    sealed class Intent {
        data object LoadImagesFromInternet : Intent()
    }

    data class State(
        val images: List<String> = emptyList()
    )

    // There isn't usage of label, so pass Nothing
}
