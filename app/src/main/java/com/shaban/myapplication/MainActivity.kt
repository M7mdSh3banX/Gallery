package com.shaban.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.mvikotlin.logging.store.LoggingStoreFactory
import com.arkivanov.mvikotlin.timetravel.store.TimeTravelStoreFactory
import com.shaban.myapplication.data.repository.ImageRepositoryImpl
import com.shaban.myapplication.ui.navigation.Root
import com.shaban.myapplication.ui.navigation.RootComponent
import com.shaban.myapplication.ui.screen.first_screen.FirstScreenUi
import com.shaban.myapplication.ui.screen.second_screen.SecondScreenUi
import com.shaban.myapplication.ui.screen.third_screen.ThirdScreenUi
import com.shaban.myapplication.ui.theme.NewChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val root = createRoot(defaultComponentContext())

        setContent {
            NewChallengeTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    RootContent(root)
                }
            }
        }
    }

    private fun createRoot(componentContext: ComponentContext): Root {
        return RootComponent(
            componentContext = componentContext,
            storeFactory = LoggingStoreFactory(TimeTravelStoreFactory()),   // Configure store factories for logging and time travel
            imageRepository = ImageRepositoryImpl()
        )
    }
}


@Composable
private fun RootContent(component: Root) {
    Children(
        stack = component.childStack,
        animation = stackAnimation(slide()),    // Stack animation between screens.
    ) {
        // Display content based on the current instance of Root.Child.
        when (val child = it.instance) {
            is Root.Child.FirstScreenChild -> FirstScreenUi(component = child.component)
            is Root.Child.SecondScreenChild -> SecondScreenUi(component = child.component)
            is Root.Child.ThirdScreenChild -> ThirdScreenUi(component = child.component)
        }
    }
}
