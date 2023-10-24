package com.example.tutorial_2_image_viewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.tutorial_2_image_viewer.ui.theme.Tutorial_2_imageviewerTheme

class MainActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent {
            val names: List<String> = List(20) { "$it" }
            Tutorial_2_imageviewerTheme() {
                Master(modifier = Modifier.fillMaxSize(), names)
            }
        }
    }
}

object screenState {
    val onboard = "onboard"
    val greetList = "greetingsList"
}


@Composable
fun Master(modifier: Modifier = Modifier, names: List<String>){

    val show_state = rememberSaveable { mutableStateOf(screenState.onboard) }

    when (show_state.value) {
        screenState.onboard -> OnboardScreen(onOpen = { show_state.value = screenState.greetList})
        screenState.greetList -> GreetingsList(names = names)
    }

}

@Composable
fun OnboardScreen(
    modifier: Modifier = Modifier,
    onOpen: () -> Unit,
){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome to app")
        Button(
            modifier = Modifier.padding(24.dp),
            onClick = onOpen
        ) {
            Text(text = "Open list")
        }
    }
}

@Composable
fun GreetingsList(modifier: Modifier = Modifier, names: List<String>){
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.background
    ){
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
            items (items = names){ i ->
                Greeting(name = i)
            }
        }
    }
}

@Composable
fun Greeting(name: String){
    val pressed = remember { mutableStateOf(false) }
    val extra_space by animateDpAsState(
        if (pressed.value) 24.dp else 0.dp
    )

    Surface(
        color = Color(0xff4d4d73),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(20.dp)){
            Column(modifier = Modifier
                .weight(1f)
                .padding(vertical = extra_space)) {
                Text(text = "Hello", color = Color.White)
                Text(text = name, color = Color.White, style = MaterialTheme.typography.h4)
            }
            Button(
                onClick = { pressed.value = !pressed.value }
            ) {
                Text(text = pressed.value.toString())
            }
        }
    }
}


