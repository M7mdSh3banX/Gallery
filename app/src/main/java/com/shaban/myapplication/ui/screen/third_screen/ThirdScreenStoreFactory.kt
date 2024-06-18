package com.shaban.myapplication.ui.screen.third_screen

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.shaban.myapplication.data.repository.ImageRepository
import com.shaban.myapplication.ui.screen.third_screen.ThirdScreenStore.State

internal class ThirdScreenStoreFactory(
    private val storeFactory: StoreFactory,
    private val imageRepository: ImageRepository
) {

    fun provide(): ThirdScreenStore =
        object : ThirdScreenStore, Store<Nothing, State, Nothing> by storeFactory.create(
            name = "ThirdScreenStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Action.LoadImagesFromResources),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data class ImagesLoaded(val images: List<Int>) : Msg()
        data object ShowLoading : Msg()
        data object HideLoading : Msg()
    }

    private sealed interface Action {
        data object LoadImagesFromResources : Action
    }

    private inner class ExecutorImpl : ReaktiveExecutor<Nothing, Action, State, Msg, Nothing>() {
        override fun executeAction(action: Action) {
            when (action) {
                Action.LoadImagesFromResources -> loadImagesFromResources()
            }
        }

        private fun loadImagesFromResources() {
            dispatch(Msg.ShowLoading)
            val images = imageRepository.loadImagesFromResources()
            dispatch(Msg.ImagesLoaded(images))
            dispatch(Msg.HideLoading)
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.ImagesLoaded -> copy(images = msg.images)
                Msg.HideLoading -> copy(isLoading = true)
                Msg.ShowLoading -> copy(isLoading = false)
            }
    }
}
