package com.shaban.myapplication.ui.screen.first_screen

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.reaktive.ReaktiveExecutor
import com.badoo.reaktive.coroutinesinterop.singleFromCoroutine
import com.badoo.reaktive.scheduler.computationScheduler
import com.badoo.reaktive.scheduler.mainScheduler
import com.badoo.reaktive.single.observeOn
import com.badoo.reaktive.single.subscribeOn
import com.shaban.myapplication.data.repository.ImageRepository
import com.shaban.myapplication.ui.screen.first_screen.FirstScreenStore.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal class FirstScreenStoreFactory(
    private val storeFactory: StoreFactory,
    private val imageRepository: ImageRepository
) {

    fun provide(): FirstScreenStore =
        object : FirstScreenStore, Store<Nothing, State, Nothing> by storeFactory.create(
            name = "FirstScreenStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Action.LoadImagesFromInternet),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data class ImagesLoaded(val images: List<String>) : Msg()
        data object ShowLoading : Msg()
        data object HideLoading : Msg()
    }

    private sealed interface Action {
        data object LoadImagesFromInternet : Action
    }

    private inner class ExecutorImpl : ReaktiveExecutor<Nothing, Action, State, Msg, Nothing>() {
        override fun executeAction(action: Action) {
            when (action) {
                Action.LoadImagesFromInternet -> loadImagesFromInternet()
            }
        }

        private fun loadImagesFromInternet() {
            dispatch(Msg.ShowLoading)
            singleFromCoroutine {
                withContext(Dispatchers.IO) {
                    imageRepository.loadImagesFromInternet()
                }
            }.subscribeOn(computationScheduler)
                .observeOn(mainScheduler)
                .subscribeScoped { images ->
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