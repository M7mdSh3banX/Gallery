package com.shaban.myapplication.ui.screen.third_screen

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.badoo.reaktive.base.Consumer
import com.shaban.myapplication.data.repository.ImageRepository

class ThirdScreenComponent(
    componentContext: ComponentContext,
    imageRepository: ImageRepository,
    storeFactory: StoreFactory,
    private val event: Consumer<ThirdScreen.Event>
) : ThirdScreen, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore {
        ThirdScreenStoreFactory(
            storeFactory = storeFactory,
            imageRepository = imageRepository
        ).provide()
    }

    private val _models = MutableValue(toModel(store.state))
    override val models: Value<ThirdScreen.Model> = _models

    override fun onClickBack() {
        event.onNext(ThirdScreen.Event.Back)
    }

    private fun toModel(state: ThirdScreenStore.State): ThirdScreen.Model {
        return ThirdScreen.Model(
            images = state.images,
            isLoading = state.isLoading
        )
    }
}