package com.shaban.myapplication.ui.screen.third_screen

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.shaban.myapplication.data.repository.ImageRepository
import com.shaban.myapplication.ui.screen.second_screen.SecondScreen
import com.shaban.myapplication.ui.screen.second_screen.SecondScreenStore

class ThirdScreenComponent(
    componentContext: ComponentContext,
    imageRepository: ImageRepository,
    storeFactory: StoreFactory,
    private val output: (ThirdScreen.Output) -> Unit
) : ThirdScreen, ComponentContext by componentContext {
    private val store = instanceKeeper.getStore {
        ThirdScreenStoreProvider(
            storeFactory = storeFactory,
            imageRepository = imageRepository
        ).provide()
    }

    private val _models = MutableValue(toModel(store.state))
    override val models: Value<ThirdScreen.Model> = _models

    override fun onClickBack() {
        output(ThirdScreen.Output.Back)
    }

    private fun toModel(state: ThirdScreenStore.State): ThirdScreen.Model {
        return ThirdScreen.Model(
            images = state.images,
        )
    }
}