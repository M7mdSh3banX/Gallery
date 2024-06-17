package com.shaban.myapplication.ui.screen.third_screen

import com.arkivanov.mvikotlin.core.store.Store
import com.shaban.myapplication.ui.screen.third_screen.ThirdScreenStore.Intent
import com.shaban.myapplication.ui.screen.third_screen.ThirdScreenStore.State

internal interface ThirdScreenStore : Store<Intent, State, Nothing> {

    sealed class Intent {
        data object LoadImagesFromResources : Intent()
    }

    data class State(
        val images: List<Int> = emptyList()
    )

    // There isn't usage of label, so pass Nothing
}
