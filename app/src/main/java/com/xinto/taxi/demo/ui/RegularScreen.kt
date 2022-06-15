package com.xinto.taxi.demo.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.xinto.taxi.Taxi
import com.xinto.taxi.demo.R
import com.xinto.taxi.rememberNavigator

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RegularScreen() {
    val navigator = rememberNavigator<RegularDestination>(initial = RegularDestination.ScreenOne)
    
    val timeHasCome = remember { "The time has come and so have I" }
    val nero = painterResource(id = R.drawable.nero_has_come)
    
    Taxi(
        modifier = Modifier.fillMaxSize(),
        navigator = navigator, 
        transitionSpec = { fadeIn() with fadeOut() },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
            when (it) {
                is RegularDestination.ScreenOne -> {
                    ProvideTextStyle(MaterialTheme.typography.headlineSmall) {
                        Text(timeHasCome)
                    }

                    Button(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        onClick = { navigator.replace(RegularDestination.ScreenTwo) }
                    ) {
                        Text(text = "To screen 2")
                    }
                }
                is RegularDestination.ScreenTwo -> {
                    Image(
                        painter = nero,
                        contentDescription = "teen with anger issues"
                    )

                    Button(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        onClick = { navigator.replace(RegularDestination.ScreenOne) }
                    ) {
                        Text(text = "To screen 1")
                    }
                }
            }
        }
    }
}