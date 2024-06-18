package com.shaban.myapplication.ui.screen.second_screen

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.shaban.myapplication.data.repository.ImageRepository

class SecondScreenComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    imageRepository: ImageRepository,
    private val output: (SecondScreen.Output) -> Unit
) : SecondScreen, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore {
        SecondScreenStoreProvider(
            storeFactory = storeFactory,
            imageRepository = imageRepository
        ).provide()
    }

    private val _models = MutableValue(toModel(store.state))
    override val models: Value<SecondScreen.Model> = _models

    override fun onClickNext() {
        output(SecondScreen.Output.Next)
    }

    override fun onClickBack() {
        output(SecondScreen.Output.Back)
    }

    private fun toModel(state: SecondScreenStore.State): SecondScreen.Model {
        return SecondScreen.Model(
            images = state.images,
            isLoading = state.isLoading
        )
    }
}