package com.shaban.myapplication.ui.screen.second_screen

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
import com.shaban.myapplication.ui.screen.second_screen.SecondScreenStore.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Every Store has up to three components: `Bootstrapper`, `Executor` and `Reducer`
 */
internal class SecondScreenStoreFactory(
    private val storeFactory: StoreFactory,
    private val imageRepository: ImageRepository
) {
    /**
     * `Bootstrapper`.
     *
     * The `Bootstrapper` produces Actions that are processed by the `Executor`.
     * The `Bootstrapper` is executed always on the `main thread`,
     * `Actions` must be also dispatched only on the `main thread`.
     */
    fun provide(): SecondScreenStore =
        object : SecondScreenStore, Store<Nothing, State, Nothing> by storeFactory.create(
            name = "SecondScreenStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Action.LoadImagesFromDisk),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ) {}

    private sealed class Msg {
        data class ImagesLoaded(val images: List<String>) : Msg()
        data object ShowLoading : Msg()
        data object HideLoading : Msg()
    }

    private sealed interface Action {
        data object LoadImagesFromDisk : Action
    }

    /**
     * `Executor`.
     *
     * This is the place for `business logic`, all `asynchronous` operations also happen here.
     * `Executor` accepts and processes `Intents` from the outside world and `Actions` from inside the Store.
     */
    private inner class ExecutorImpl : ReaktiveExecutor<Nothing, Action, State, Msg, Nothing>() {
        override fun executeAction(action: Action) {
            // This is essential for handling initial actions from the Bootstrapper
            when (action) {
                Action.LoadImagesFromDisk -> loadImagesFromDisk()
            }
        }

        private fun loadImagesFromDisk() {
            dispatch(Msg.ShowLoading)
            singleFromCoroutine {
                withContext(Dispatchers.IO) {
                    imageRepository.loadImagesFromDisk()
                }
            }.subscribeOn(computationScheduler)
                .observeOn(mainScheduler)
                .subscribeScoped { images ->
                    dispatch(Msg.ImagesLoaded(images))
                    dispatch(Msg.HideLoading)
                }
        }
    }

    /**
     * `Reducer`
     * accepts a Message from the `Executor` and the `current State` of the `Store` and returns a `new State`.
     * The `Reducer` is called for every `Message` produced by the `Executor` and the `new State`.
     * The `Reducer` is always called on the `main thread`.
     */
    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.ImagesLoaded -> copy(images = msg.images)
                is Msg.ShowLoading -> copy(isLoading = true)
                is Msg.HideLoading -> copy(isLoading = false)
            }
    }
}
