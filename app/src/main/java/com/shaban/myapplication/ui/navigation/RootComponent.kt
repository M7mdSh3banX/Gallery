package com.shaban.myapplication.ui.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.badoo.reaktive.base.Consumer
import com.shaban.myapplication.data.repository.ImageRepository
import com.shaban.myapplication.ui.screen.first_screen.FirstScreen
import com.shaban.myapplication.ui.screen.first_screen.FirstScreenComponent
import com.shaban.myapplication.ui.screen.second_screen.SecondScreen
import com.shaban.myapplication.ui.screen.second_screen.SecondScreenComponent
import com.shaban.myapplication.ui.screen.third_screen.ThirdScreen
import com.shaban.myapplication.ui.screen.third_screen.ThirdScreenComponent
import kotlinx.serialization.Serializable

/**
 * RootComponent handles the navigation and composition of screens using Decompose architecture.
 *
 * @param componentContext ComponentContext provided by Decompose.
 * @property imageRepository Repository for fetching image data.
 * @property storeFactory Factory for creating MVKotlin stores.
 */
class RootComponent(
    componentContext: ComponentContext,
    private val imageRepository: ImageRepository,
    private val storeFactory: StoreFactory
) : Root, ComponentContext by componentContext {

    // Manages the navigation state and transitions between screens.
    private val navigation = StackNavigation<Configuration>()

    // Creates a stack of child components based on navigation events and configurations.
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
                    event = Consumer(::onFirstScreenEvent)
                )
            )

            Configuration.SecondScreen -> Root.Child.SecondScreenChild(
                SecondScreenComponent(
                    componentContext = context,
                    storeFactory = storeFactory,
                    imageRepository = imageRepository,
                    event = Consumer(::onSecondScreenEvent)
                )
            )

            Configuration.ThirdScreen -> Root.Child.ThirdScreenChild(
                ThirdScreenComponent(
                    componentContext = context,
                    storeFactory = storeFactory,
                    imageRepository = imageRepository,
                    event = Consumer(::onThirdScreenEvent)
                )
            )
        }
    }

    private fun onFirstScreenEvent(event: FirstScreen.Event) {
        when (event) {
            FirstScreen.Event.Next -> navigation.push(Configuration.SecondScreen)
        }
    }

    private fun onSecondScreenEvent(event: SecondScreen.Event) {
        when (event) {
            SecondScreen.Event.Next -> navigation.push(Configuration.ThirdScreen)
            SecondScreen.Event.Back -> navigation.pop()
        }
    }

    private fun onThirdScreenEvent(event: ThirdScreen.Event) {
        when (event) {
            ThirdScreen.Event.Back -> navigation.pop()
        }
    }

    /**
     * Sealed class defining the possible configurations/screens managed by RootComponent.
     * `Serialization` ensures that configurations (Configuration objects)
     * can be serialized into a format that can be stored and deserialized back into their original form.
     */
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

/**
 * Creates a Consumer instance that executes the given block of code when it receives a value
 * @param T The type of value the Consumer will handle.
 * @param block A lambda function to be executed when the Consumer receives a value.
 * @return A Consumer instance that executes the provided block of code.
 *
 * `Usage`:
 * This utility function simplifies the creation of a Consumer by allowing you to pass a lambda function
 * instead of implementing the Consumer interface manually. It is often used to handle events or data emissions
 * in a reactive stream.
 */

inline fun <T> Consumer(crossinline block: (T) -> Unit): Consumer<T> {
    return object : Consumer<T> {
        override fun onNext(value: T) {
            block(value)
        }
    }
}
