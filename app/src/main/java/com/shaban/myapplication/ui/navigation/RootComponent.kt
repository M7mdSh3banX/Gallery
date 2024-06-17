package com.shaban.myapplication.ui.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.shaban.myapplication.data.repository.ImageRepository
import com.shaban.myapplication.ui.screen.first_screen.FirstScreen
import com.shaban.myapplication.ui.screen.first_screen.FirstScreenComponent
import com.shaban.myapplication.ui.screen.second_screen.SecondScreen
import com.shaban.myapplication.ui.screen.second_screen.SecondScreenComponent
import com.shaban.myapplication.ui.screen.third_screen.ThirdScreen
import com.shaban.myapplication.ui.screen.third_screen.ThirdScreenComponent
import kotlinx.serialization.Serializable

class RootComponent(
    componentContext: ComponentContext,
    private val imageRepository: ImageRepository,
    private val storeFactory: StoreFactory
) : Root, ComponentContext by componentContext {

    private val navigation = StackNavigation<Configuration>()
    private val stack = childStack(
        source = navigation,
        serializer = Configuration.serializer(),
        initialConfiguration = Configuration.FirstScreen,
        handleBackButton = true,
        childFactory = ::createChild
    )
    override val childStack: Value<ChildStack<*, Root.Child>> = stack

    private fun createChild(
        config: Configuration,
        context: ComponentContext
    ): Root.Child {
        return when (config) {
            Configuration.FirstScreen -> Root.Child.FirstScreenChild(
                FirstScreenComponent(
                    componentContext = context,
                    storeFactory = storeFactory,
                    imageRepository = imageRepository,
                    output = {
                        when (it) {
                            FirstScreen.Output.Next -> navigation.push(Configuration.SecondScreen)
                        }
                    }
                )
            )

            Configuration.SecondScreen -> Root.Child.SecondScreenChild(
                SecondScreenComponent(
                    componentContext = context,
                    storeFactory = storeFactory,
                    imageRepository = imageRepository,
                    output = {
                        when (it) {
                            SecondScreen.Output.Next -> navigation.push(Configuration.ThirdScreen)
                            SecondScreen.Output.Back -> navigation.pop()
                        }
                    }
                )
            )

            Configuration.ThirdScreen -> Root.Child.ThirdScreenChild(
                ThirdScreenComponent(
                    componentContext = context,
                    storeFactory = storeFactory,
                    imageRepository = imageRepository,
                    output = {
                        when (it) {
                            ThirdScreen.Output.Back -> navigation.pop()
                        }
                    }
                )
            )
        }
    }

    @Serializable
    private sealed class Configuration {
        @Serializable
        data object FirstScreen : Configuration()

        @Serializable
        data object SecondScreen : Configuration()

        @Serializable
        data object ThirdScreen : Configuration()
    }
}