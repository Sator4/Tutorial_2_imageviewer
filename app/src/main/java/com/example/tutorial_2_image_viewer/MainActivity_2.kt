package com.example.tutorial_2_image_viewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity_2 : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            Master(Modifier.fillMaxSize())
        }
    }
}


private fun checkForDot(equation: String): Boolean {
    for (i in equation.length-1 downTo 0){
        if (equation[i] in "+-*/()"){
            break
        }
        if (equation[i] == '.'){
            return true
        }
    }
    return false
}



@Composable
fun Master(modifier: Modifier = Modifier){
    val equation = remember { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = equation.value, modifier = Modifier.defaultMinSize(minHeight = 20.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Keyboard(
            onButtonClicked = { equation.value = buttonClick(equation.value, it) }
        )
    }
}

@Composable
fun Keyboard(
    modifier: Modifier = Modifier,
    onButtonClicked: (String) -> Unit,
){
    Row {
        NumberPad(onButtonClicked = onButtonClicked)
        OperationKeys(onButtonClicked = onButtonClicked)
    }
}

@Composable
fun NumberPad(
    modifier: Modifier = Modifier,
    onButtonClicked: (String) -> Unit
){
    Column (
        modifier = Modifier.fillMaxWidth(0.6f)
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(
                modifier = Modifier.defaultMinSize(1.dp, 1.dp),
                onClick = { onButtonClicked("1") },
            ) {
                Text(text = "1")
            }
            Button(
                modifier = Modifier.defaultMinSize(1.dp, 1.dp),
                onClick = { onButtonClicked("2") },
            ) {
                Text(text = "2")
            }
            Button(
                modifier = Modifier.defaultMinSize(1.dp, 1.dp),
                onClick = { onButtonClicked("3") },
            ) {
                Text(text = "3")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(
                modifier = Modifier.defaultMinSize(1.dp, 1.dp),
                onClick = { onButtonClicked("4") },
            ) {
                Text(text = "4")
            }
            Button(
                modifier = Modifier.defaultMinSize(1.dp, 1.dp),
                onClick = { onButtonClicked("5") },
            ) {
                Text(text = "5")
            }
            Button(
                modifier = Modifier.defaultMinSize(1.dp, 1.dp),
                onClick = { onButtonClicked("6") },
            ) {
                Text(text = "6")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(
                modifier = Modifier.defaultMinSize(1.dp, 1.dp),
                onClick = { onButtonClicked("7") },
            ) {
                Text(text = "7")
            }
            Button(
                modifier = Modifier.defaultMinSize(1.dp, 1.dp),
                onClick = { onButtonClicked("8") },
            ) {
                Text(text = "8")
            }
            Button(
                modifier = Modifier.defaultMinSize(1.dp, 1.dp),
                onClick = { onButtonClicked("9") },
            ) {
                Text(text = "9")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(
                modifier = Modifier.defaultMinSize(1.dp, 1.dp),
                onClick = { onButtonClicked("C") },
            ) {
                Text(text = "C")
            }
            Button(
                modifier = Modifier.defaultMinSize(1.dp, 1.dp),
                onClick = { onButtonClicked("0") },
            ) {
                Text(text = "0")
            }
            Button(
                modifier = Modifier.defaultMinSize(1.dp, 1.dp),
                onClick = { onButtonClicked(".") },
            ) {
                Text(text = ".")
            }
        }
    }
}


@Composable
fun OperationKeys(
    modifier: Modifier = Modifier,
    onButtonClicked: (String) -> Unit
){
    Column{
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(
                modifier = Modifier.defaultMinSize(1.dp, 1.dp),
                onClick = { onButtonClicked("+") },
            ) {
                Text(text = "+")
            }
            Button(
                modifier = Modifier.defaultMinSize(1.dp, 1.dp),
                onClick = { onButtonClicked("-") },
            ) {
                Text(text = "-")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(
                modifier = Modifier.defaultMinSize(1.dp, 1.dp),
                onClick = { onButtonClicked("*") },
            ) {
                Text(text = "*")
            }
            Button(
                modifier = Modifier.defaultMinSize(1.dp, 1.dp),
                onClick = { onButtonClicked("/") },
            ) {
                Text(text = "/")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(
                modifier = Modifier.defaultMinSize(1.dp, 1.dp),
                onClick = { onButtonClicked("(") },
            ) {
                Text(text = "(")
            }
            Button(
                modifier = Modifier.defaultMinSize(1.dp, 1.dp),
                onClick = { onButtonClicked(")") },
            ) {
                Text(text = ")")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            Button(
                onClick = { onButtonClicked("=") },
            ) {
                Text(text = "=")
            }
        }
    }
}


private fun buttonClick(equation: String, symbol: String): String {
    if (symbol == "C"){
        return ""
    }
    if (symbol == "="){
        return computeEquation(equation)
    }
    if (symbol in "+-*/" && (equation == "" || equation.last() !in "0123456879)")){
        return equation
    }
    if (symbol == ")" && equation.count{it == '('} <= equation.count{it == ')'}){
        return equation
    }
    if (symbol == "(" && !equation.isEmpty() && equation.last() !in "+-*/("){
        return equation
    }
    if (symbol == "."){
        if (equation == "" || equation == "0"){
            return "0."
        }
        if (checkForDot(equation)){
            return equation
        }
    }


    return equation + symbol
}

private fun computeEquation(equation: String) : String{
    val equationArray = arrayListOf<String>()
    val priorityArray = arrayListOf<Pair<Int, Int>>()
    var cur_el = ""
    var bracket_coef = 0

    for (i in equation){
        if (i in "+-*/()"){
            if (cur_el != ""){
                equationArray.add(cur_el)
                cur_el = ""
            }
            equationArray.add(i.toString())
            continue
        }
        cur_el += i
    }
    if (cur_el != ""){
        equationArray.add(cur_el)
    }
    if (equationArray.last() in "+-*/()"){
        equationArray.removeAt(equationArray.size-1)
    }

    for (i in 0..equationArray.size-1){
        if (equationArray[i] !in "+-*/()"){
            priorityArray.add(Pair(-1, i))
            continue
        }
        if (equationArray[i] == "("){
            bracket_coef += 2
            continue
        }
        if (equationArray[i] == ")"){
            bracket_coef -= 2
            continue
        }
        if (equationArray[i] in "+-"){
            priorityArray.add(Pair(bracket_coef, i))
        }
        else if (equationArray[i] in "*/"){
            priorityArray.add(Pair(bracket_coef + 1, i))
        }
    }

    while (priorityArray.size > 1){
        var max_priority = -1
        var max_priority_index = -1
        for (i in 0..priorityArray.size-1){
            if (priorityArray[i].first > max_priority){
                max_priority = priorityArray[i].first
                max_priority_index = i
            }
        }
        val el_to_overwrite_index = priorityArray[max_priority_index].second
        val operand_1_index = priorityArray[max_priority_index-1].second
        val operand_2_index = priorityArray[max_priority_index+1].second
        val operation = equationArray[priorityArray[max_priority_index].second]
        val operand_1 = equationArray[operand_1_index].toDouble()
        val operand_2 = equationArray[operand_2_index].toDouble()
        var result = 0.0
        if (operation == "+"){
            result = operand_1 + operand_2
        } else if (operation == "-"){
            result = operand_1 - operand_2
        } else if (operation == "*"){
            result = operand_1 * operand_2
        } else if (operation == "/"){
            result = operand_1 / operand_2
        }
        priorityArray[max_priority_index] = Pair(-1, priorityArray[max_priority_index].second)
        priorityArray.removeAt(max_priority_index-1)
        priorityArray.removeAt(max_priority_index)
        equationArray[el_to_overwrite_index] = result.toString()
    }

    return equationArray[priorityArray[0].second]
}