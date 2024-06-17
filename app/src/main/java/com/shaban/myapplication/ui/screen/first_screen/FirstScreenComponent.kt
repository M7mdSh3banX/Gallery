package com.shaban.myapplication.ui.screen.first_screen

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.shaban.myapplication.data.repository.ImageRepository

class FirstScreenComponent(
    componentContext: ComponentContext,
    storeFactory: StoreFactory,
    imageRepository: ImageRepository,
    private val output: (FirstScreen.Output) -> Unit
) : FirstScreen, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore {
        FirstScreenStoreProvider(
            storeFactory = storeFactory,
            imageRepository = imageRepository
        ).provide()
    }

    private val _models = MutableValue(toModel(store.state))
    override val models: Value<FirstScreen.Model> = _models

    override fun onClickNext() {
        output(FirstScreen.Output.Next)
    }

    private fun toModel(state: FirstScreenStore.State): FirstScreen.Model {
        return FirstScreen.Model(
            images = state.images,
        )
    }
}