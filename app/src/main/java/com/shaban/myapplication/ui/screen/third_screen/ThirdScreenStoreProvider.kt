package com.shaban.myapplication.ui.screen.third_screen

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.shaban.myapplication.data.repository.ImageRepository
import com.shaban.myapplication.ui.screen.third_screen.ThirdScreenStore.Intent
import com.shaban.myapplication.ui.screen.third_screen.ThirdScreenStore.State

internal class ThirdScreenStoreProvider(
    private val storeFactory: StoreFactory,
    private val imageRepository: ImageRepository
) {

    fun provide(): ThirdScreenStore =
        object : ThirdScreenStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "ThirdScreenStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data class ImagesLoaded(val images: List<Int>) : Msg()
    }

    private inner class ExecutorImpl : ReaktiveExecutor<Intent, Unit, State, Msg, Nothing>() {
        override fun executeAction(action: Unit) {
            loadImagesFromResources()
        }

        override fun executeIntent(intent: Intent) {
            when (intent) {
                is Intent.LoadImagesFromResources -> loadImagesFromResources()
            }
        }

        private fun loadImagesFromResources() {
            val images = imageRepository.loadImagesFromResources()
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
