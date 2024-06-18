package com.shaban.myapplication.ui.screen.first_screen

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.reaktive.states
import com.badoo.reaktive.base.Consumer
import com.badoo.reaktive.observable.subscribe
import com.shaban.myapplication.data.repository.ImageRepository

class FirstScreenComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    imageRepository: ImageRepository,
    private val event: Consumer<FirstScreen.Event>
) : FirstScreen, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore {
        FirstScreenStoreFactory(
            storeFactory = storeFactory,
            imageRepository = imageRepository
        ).provide()
    }

    private val _models = MutableValue(toModel(store.state))
    override val models: Value<FirstScreen.Model> = _models

    init {
        store.states.subscribe { state ->
            _models.value = toModel(state)
        }
    }

    override fun onClickNext() {
        event.onNext(FirstScreen.Event.Next)
    }

    private fun toModel(state: FirstScreenStore.State): FirstScreen.Model {
        return FirstScreen.Model(
            images = state.images,
            isLoading = state.isLoading
        )
    }
}