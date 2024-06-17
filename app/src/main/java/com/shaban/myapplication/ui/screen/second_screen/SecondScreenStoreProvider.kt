package com.shaban.myapplication.ui.screen.second_screen

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.shaban.myapplication.data.repository.ImageRepository
import com.shaban.myapplication.ui.screen.second_screen.SecondScreenStore.Intent
import com.shaban.myapplication.ui.screen.second_screen.SecondScreenStore.State

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
    }

    private inner class ExecutorImpl : ReaktiveExecutor<Intent, Unit, State, Msg, Nothing>() {
        override fun executeAction(action: Unit) {
            loadImagesFromDisk()
        }

        override fun executeIntent(intent: Intent) {
            when (intent) {
                is Intent.LoadImagesFromDisk -> loadImagesFromDisk()
            }
        }

        private fun loadImagesFromDisk() {
            val images = imageRepository.loadImagesFromDisk()
            dispatch(Msg.ImagesLoaded(images))
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.ImagesLoaded -> copy(images = msg.images)
            }
    }
}