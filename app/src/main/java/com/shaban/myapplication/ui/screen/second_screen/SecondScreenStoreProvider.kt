package com.shaban.myapplication.ui.screen.second_screen

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.shaban.myapplication.data.repository.ImageRepository
import com.shaban.myapplication.ui.screen.second_screen.SecondScreenStore.Intent
import com.shaban.myapplication.ui.screen.second_screen.SecondScreenStore.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class SecondScreenStoreProvider(
    private val storeFactory: StoreFactory,
    private val imageRepository: ImageRepository
) {

    fun provide(): SecondScreenStore =
        object : SecondScreenStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "SecondScreenStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data class ImagesLoaded(val images: List<String>) : Msg()
        data object ShowLoading : Msg()
        data object HideLoading : Msg()
    }

    private inner class ExecutorImpl : ReaktiveExecutor<Intent, Unit, State, Msg, Nothing>() {
        private val coroutineScope = CoroutineScope(Dispatchers.Main)
        override fun executeAction(action: Unit) {
            loadImagesFromDisk()
        }

        override fun executeIntent(intent: Intent) {
            when (intent) {
                is Intent.LoadImagesFromDisk -> loadImagesFromDisk()
            }
        }

        private fun loadImagesFromDisk() {
            dispatch(Msg.ShowLoading)
            coroutineScope.launch {
                val images = withContext(Dispatchers.IO) {
                    imageRepository.loadImagesFromDisk()
                }
                dispatch(Msg.ImagesLoaded(images))
                dispatch(Msg.HideLoading)
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.ImagesLoaded -> copy(images = msg.images)
                is Msg.ShowLoading -> copy(isLoading = true)
                is Msg.HideLoading -> copy(isLoading = false)
            }
    }
}
