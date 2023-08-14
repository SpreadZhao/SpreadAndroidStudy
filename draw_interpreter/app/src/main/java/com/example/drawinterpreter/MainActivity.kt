package com.example.drawinterpreter

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.rotateRad
import androidx.compose.ui.tooling.preview.Preview
import com.example.drawinterpreter.model.grammar.*
import com.example.drawinterpreter.model.token.TokenType
import com.example.drawinterpreter.ui.theme.DrawInterpreterTheme
import kotlin.math.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrawInterpreterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    Greeting("Android")
                    ShowCmd()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowCmd(){

    var text by remember {
        mutableStateOf("")
    }
    var scanResult by remember{
        mutableStateOf("")
    }
    var parseResult by remember{
        mutableStateOf("")
    }

    var step by remember {
        mutableStateOf(0)
    }

    var originX by remember {
        mutableStateOf(0.0)
    }

    var originY by remember {
        mutableStateOf(0.0)
    }

    var from by remember {
        mutableStateOf(0)
    }

    var to by remember {
        mutableStateOf(0)
    }

    var draw by remember {
        mutableStateOf(0)
    }

    var scaleX by remember {
        mutableStateOf(0.0)
    }

    var scaleY by remember {
        mutableStateOf(0.0)
    }

    var rot by remember {
        mutableStateOf(0.0)
    }

    Column {

        LazyColumn {
            item{
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    label = { Text("Input Command") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
//                Button(
//                    onClick = {
//                        // 将所有换行符删除
//                        val statements = text.replace("\\n".toRegex(), "").split(";")
//                        Log.d("itpt", "statements: $statements")
//                        Scanner.getCmd(statements)
//                        Scanner.doScan()
//                        scanResult = Scanner.getResultAsString()
//                        Scanner.clearAll()
//                    }
//                ) {
//                    Text("Execute Scan")
//                }

                fun isInt(num: Double): Boolean{
                    val intNum = num.toInt()
                    return num % intNum == 0.0
                }

                Button(
                    onClick = {

                        //重置解释结果，以待下一次运算
                        Parser.interpretResult.resetAll()
                        // 将语句取出，删掉所有换行符，按分号分隔成每个句子
                        val statements = text.replace("\\n".toRegex(), "").split(";")
                        Log.d("itpt", "statements: $statements")
                        // 将所有句子传递给Scanner
                        Scanner.getCmd(statements)
                        // 执行词法分析，输出Token序列
                        Scanner.doScan()
                        // 将Token序列从Scanner传递给Parser
                        Parser.getAllTokensFromScanner(Scanner.allTokens)
                        // Parser分析Token序列，生成执行结果interpretResult
                        Parser.doParse()
                        //parseResult = Parser.testResult()

                        // 如果解释成功(没有报错)
                        if(Parser.interpretResult.isInterpretSuccessful){
                            // 分析结果滞空，表示解释成功后应该开始画图，删掉之前的报错信息
                            parseResult = ""

                            /*
                             * 给前端界面记录的数据赋值，从interpretResult中取
                             */
                            step = if(isInt(Parser.interpretResult.step)) Parser.interpretResult.step.toInt() else 0
                            from = if(isInt(Parser.interpretResult.from)) Parser.interpretResult.from.toInt() else 0
                            to = if(isInt(Parser.interpretResult.to)) Parser.interpretResult.to.toInt() else 0
                            originX = if(Parser.interpretResult.originX == Double.MIN_VALUE) 0.0 else Parser.interpretResult.originX
                            originY = if(Parser.interpretResult.originY == Double.MIN_VALUE) 0.0 else Parser.interpretResult.originY
                            scaleX = if(Parser.interpretResult.scaleX == Double.MIN_VALUE) 1.0 else Parser.interpretResult.scaleX
                            scaleY = if(Parser.interpretResult.scaleY == Double.MIN_VALUE) 1.0 else Parser.interpretResult.scaleY
                            rot = if(Parser.interpretResult.rot == Double.MIN_VALUE) 0.0 else Parser.interpretResult.rot

                            // 触发Canvas的画图函数，没有实际意义
                            draw++


                            Log.d("itpt-main-res", "origin x: $originX")
                            Log.d("itpt-main-res", "origin y: $originY")
                            Log.d("itpt-main-res", "rot: $rot")
                            Log.d("itpt-main-res", "scale x: $scaleX")
                            Log.d("itpt-main-res", "scale y: $scaleY")
                            Log.d("itpt-main-res", "from : $from")
                            Log.d("itpt-main-res", "to : $to")
                            Log.d("itpt-main-res", "step: $step")
                            Log.d("itpt-main-res", "draw x: ${Parser.interpretResult.drawX}")
                            Log.d("itpt-main-res", "x is param: ${Parser.interpretResult.isDrawXParam}")
                            Log.d("itpt-main-res", "x is param exp: ${Parser.interpretResult.isDrawXParamExp}")
                            Log.d("itpt-main-res", "draw y: ${Parser.interpretResult.drawY}")
                            Log.d("itpt-main-res", "y is param: ${Parser.interpretResult.isDrawYParam}")
                            Log.d("itpt-main-res", "y is param exp: ${Parser.interpretResult.isDrawYParamExp}")
                        }else{
                            // 如果解释失败，拿到Parser生成的报错信息，传递给前端
                            parseResult = Parser.interpretResult.errorMessage
                        }
                        // 重置Scanner和Parser的成员，以待下一次画图
                        Scanner.clearAll()
                        Parser.clearAll()
                    }
                ) {
                    Text("draw")
                }

//                Button(onClick = { scanResult = "" }) {
//                    Text(text = "Clear Scan Result")
//                }
//
//                Button(onClick = { parseResult = "" ; Parser.interpretResult.resetAll()}) {
//                    Text(text = "Clear Parse Result")
//                }

            Column {
//                Card(
//                    modifier = Modifier
//                        .wrapContentHeight()
//                        .wrapContentWidth()
//                ) {
//                    Text(text = "Scan Result: ")
//                }
//                Card {
//                    Text(text = scanResult)
//                }
                Card {
                    Text(text = parseResult)
                }
            }


            } // end item



        } // end lazy column

        Canvas(
            modifier = Modifier
                .wrapContentSize(),

            onDraw = {
                // 触发点，无实际意义
                val go = draw

                // 绘制from - to + 1个点，在循环中给出每个点的坐标偏移量
                val pointList = ArrayList<Offset>(step)
                var i = from
                while(i < to){
                    val offset = getCurrentOffset(originX, originY, scaleX, scaleY, rot, i)
                    Log.d("itpt-main-testoffset", "x: ${offset.x}, y: ${offset.y}")
                    pointList.add(offset)
                    i++
                }

                // 绘制原点，只有一个
                val origin = mutableListOf(Offset(originX.toFloat(), originY.toFloat()))
                Log.d("itpt-main", "draw point")
                drawPoints(
                    color = Color.Yellow,
                    points = origin,
                    pointMode = PointMode.Points,
                    strokeWidth = 15f
                )

                // 旋转特定弧度，旋转中心是origin[0]，也就是原点
                rotateRad(
                    radians = rot.toFloat(),
                    pivot = origin[0]
                ){
                    drawPoints(
                        color = Color.Red,
                        points = pointList,
                        pointMode = PointMode.Points,
                        strokeWidth = step.toFloat()
                    )
                }
            }
        )
    }

}

fun getCurrentOffset(originX: Double, originY: Double, scaleX: Double, scaleY: Double, rot: Double, i: Int):Offset {

    val cal = CurrentParamCalculator(i.toDouble())

    return if(Parser.interpretResult.isDrawXParamExp && !Parser.interpretResult.isDrawYParamExp){
        // 横坐标带参数，纵坐标不带
        if(Parser.interpretResult.isDrawYParam){
            // 纵坐标是单个参数
            Log.d("itpt-main-offset", "x: ${relativeAndScale(originX, cal.calX(), scaleX)}, y: ${relativeAndScale(originY, i.toDouble(), scaleY)}")
            Offset(relativeAndScale(originX, cal.calX(), scaleX), relativeAndScale(originY, i.toDouble(), scaleY))
        }else{
            // 横坐标是参数表达式，纵坐标是常数
            Offset(relativeAndScale(originX, cal.calX(), scaleX), relativeAndScale(originY, Parser.interpretResult.drawY, scaleY))
        }
    }else if(Parser.interpretResult.isDrawXParamExp && Parser.interpretResult.isDrawYParamExp){
        // 横纵坐标都是参数表达式
        Log.d("itpt-main-offset", "x: ${relativeAndScale(originX, cal.calX(), scaleX)}, y: ${relativeAndScale(originY, cal.calY(), scaleY)}")
        Offset(relativeAndScale(originX, cal.calX(), scaleX), relativeAndScale(originY, cal.calY(), scaleY))
    }else if(!Parser.interpretResult.isDrawXParamExp && Parser.interpretResult.isDrawYParamExp){
        // 横坐标不带，纵坐标带参数
        if(Parser.interpretResult.isDrawXParam){
            // 横坐标是单个参数，纵坐标是参数表达式
            Offset(relativeAndScale(originX, i.toDouble(), scaleX), relativeAndScale(originY, cal.calY(), scaleY))
        }else{
            // 横坐标是常数，纵坐标是参数表达式
            Log.d("itpt-main-offset", "x: ${relativeAndScale(originX, Parser.interpretResult.drawX, scaleX)}, y: ${relativeAndScale(originY, cal.calY(), scaleY)}")
            Offset(relativeAndScale(originX, Parser.interpretResult.drawX, scaleX), relativeAndScale(originY, cal.calY(), scaleY))
        }
    }else{
        // 横纵坐标都不带参数
        if(Parser.interpretResult.isDrawXParam){
            // 横坐标是单个参数
            if(Parser.interpretResult.isDrawYParam){
                // 横纵坐标都是单个参数
                Log.d("itpt-main-offset", "x: ${relativeAndScale(originX, i.toDouble(), scaleX)}, y: ${relativeAndScale(originY, i.toDouble(), scaleY)}")
                Offset(relativeAndScale(originX, i.toDouble(), scaleX), relativeAndScale(originY, i.toDouble(), scaleY))
            }else{
                // 横坐标是单个参数，纵坐标是常数
                Log.d("itpt-main-offset", "x: ${relativeAndScale(originX, i.toDouble(), scaleX)}, y: ${relativeAndScale(originY, Parser.interpretResult.drawY, scaleY)}")
                Offset(relativeAndScale(originX, i.toDouble(), scaleX), relativeAndScale(originY, Parser.interpretResult.drawY, scaleY))

            }
        }else{
            // 横坐标是常数
            if(Parser.interpretResult.isDrawYParam){
                // 横坐标是常数，纵坐标是单个参数
                Log.d("itpt-main-offset", "x: ${relativeAndScale(originX, Parser.interpretResult.drawX, scaleX)}, y: ${relativeAndScale(originY, i.toDouble(), scaleY)}")
                Offset(relativeAndScale(originX, Parser.interpretResult.drawX, scaleX), relativeAndScale(originY, i.toDouble(), scaleY))
            }else{
                // 横纵坐标都是常数
                Offset(relativeAndScale(originX, Parser.interpretResult.drawX, scaleX), relativeAndScale(originY, Parser.interpretResult.drawY, scaleY))
            }
        }
    }

}

fun relativeAndScale(ori: Double, calres: Double, scale: Double) = (ori + calres * scale).toFloat()

class CurrentParamCalculator(
    private val currentParamVal: Double = 0.0
){

    fun calX() = calculateExp(Parser.interpretResult.paramNodeX)
    fun calY() = calculateExp(Parser.interpretResult.paramNodeY)

    private fun calculateExp(node: ExprNode): Double{
        return postOrder(node)
    }

    private fun postOrder(node: ExprNode): Double{
        when(node){
            is BinExprNode -> {
                return when(node.token.type){
                    TokenType.PLUS -> postOrder(node.left) + postOrder(node.right)
                    TokenType.MINUS -> postOrder(node.left) - postOrder(node.right)
                    TokenType.MUL -> postOrder(node.left) * postOrder(node.right)
                    TokenType.DIV -> postOrder(node.left) / postOrder(node.right)
                    TokenType.POWER -> postOrder(node.left).pow(postOrder(node.right))
                    else -> Double.MIN_VALUE
                }
            }
            is UniExprNode -> {
                return when(node.token.originStr){
                    "SIN" -> sin(postOrder(node.child))
                    "COS" -> cos(postOrder(node.child))
                    "TAN" -> tan(postOrder(node.child))
                    "LN" -> ln(postOrder(node.child))
                    "SQRT" -> sqrt(postOrder(node.child))
                    "EXP" -> exp(postOrder(node.child))
                    else -> Double.MIN_VALUE
                }
            }
            is ParamExprNode -> return currentParamVal
            is NumExprNode -> return node.value
            else -> return Double.MIN_VALUE
        }
    }
}



@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    DrawInterpreterTheme {
        Greeting("Android")
    }
}