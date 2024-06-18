package com.shaban.myapplication.ui.screen.second_screen

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.reaktive.states
import com.badoo.reaktive.base.Consumer
import com.badoo.reaktive.observable.subscribe
import com.shaban.myapplication.data.repository.ImageRepository


class SecondScreenComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    imageRepository: ImageRepository,
    private val event: Consumer<SecondScreen.Event>
) : SecondScreen, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore {
        SecondScreenStoreFactory(
            storeFactory = storeFactory,
            imageRepository = imageRepository
        ).provide()
    }

    private val _models = MutableValue(toModel(store.state))
    override val models: Value<SecondScreen.Model> = _models

    init {
        store.states.subscribe { state ->
            _models.value = toModel(state)
        }
    }

    override fun onClickNext() {
        event.onNext(SecondScreen.Event.Next)
    }

    override fun onClickBack() {
        event.onNext(SecondScreen.Event.Back)
    }

    private fun toModel(state: SecondScreenStore.State): SecondScreen.Model {
        return SecondScreen.Model(
            images = state.images,
            isLoading = state.isLoading
        )
    }
}