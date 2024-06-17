package com.shaban.myapplication.ui.navigation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.shaban.myapplication.ui.screen.first_screen.FirstScreen
import com.shaban.myapplication.ui.screen.second_screen.SecondScreen
import com.shaban.myapplication.ui.screen.third_screen.ThirdScreen

interface Root {

    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        data class FirstScreenChild(val component: FirstScreen) : Child()
        data class SecondScreenChild(val component: SecondScreen) : Child()
        data class ThirdScreenChild(val component: ThirdScreen) : Child()
    }
}