package com.shaban.myapplication.ui.screen.first_screen

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.shaban.myapplication.data.repository.ImageRepository
import com.shaban.myapplication.ui.screen.first_screen.FirstScreenStore.Intent
import com.shaban.myapplication.ui.screen.first_screen.FirstScreenStore.State

internal class FirstScreenStoreProvider(
    private val storeFactory: StoreFactory,
    private val imageRepository: ImageRepository
) {

    fun provide(): FirstScreenStore =
        object : FirstScreenStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "FirstScreenStore",
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
            loadImagesFromInternet()
        }

        override fun executeIntent(intent: Intent) {
            when (intent) {
                is Intent.LoadImagesFromInternet -> loadImagesFromInternet()
            }
        }

        private fun loadImagesFromInternet() {
            val images = imageRepository.loadImagesFromInternet()
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