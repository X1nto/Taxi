package com.xinto.taxi.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.xinto.taxi.Destination
import com.xinto.taxi.Taxi
import com.xinto.taxi.demo.ui.BackstackScreen
import com.xinto.taxi.demo.ui.RegularScreen
import com.xinto.taxi.demo.ui.theme.TaxiTheme
import com.xinto.taxi.demo.ui.widget.ListItem
import com.xinto.taxi.rememberNavigator
import kotlinx.parcelize.Parcelize

sealed interface SampleDestination : Destination {

    @Parcelize
    object ChooseScreen : SampleDestination

    @Parcelize
    object RegularSample : SampleDestination

    @Parcelize
    object BackstackSample : SampleDestination
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaxiTheme {
                MainContent {
                    finish()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainContent(
    finishActivity: () -> Unit,
) {
    val navigator = rememberNavigator<SampleDestination>(initial = SampleDestination.ChooseScreen)
    val topBarBehaviour = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    BackHandler {
        if (navigator.currentDestination is SampleDestination.ChooseScreen) {
            finishActivity()
        }
        navigator.replace(SampleDestination.ChooseScreen)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SmallTopAppBar(
                title = { Text("Taxi samples") },
                scrollBehavior = topBarBehaviour
            )
        }
    ) { paddingValues ->
        Taxi(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            navigator = navigator,
            transitionSpec = { fadeIn() with fadeOut() },
        ) {
            when (it) {
                is SampleDestination.ChooseScreen -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .nestedScroll(topBarBehaviour.nestedScrollConnection),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        item {
                            ListItem(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { navigator.replace(SampleDestination.RegularSample) }
                            ) {
                                Text("Regular navigation")
                            }
                        }
                        item {
                            ListItem(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { navigator.replace(SampleDestination.BackstackSample) }
                            ) {
                                Text("Backstack navigation")
                            }
                        }
                    }
                }
                is SampleDestination.RegularSample -> {
                    RegularScreen()
                }
                is SampleDestination.BackstackSample -> {
                    BackstackScreen()
                }
            }
        }
    }
}