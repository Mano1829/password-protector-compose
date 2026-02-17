package com.mine.passwordprotector.ui.view


import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mine.passwordprotector.ui.theme.Black
import com.mine.passwordprotector.ui.theme.White
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.PI
import kotlin.math.roundToInt


@Composable
fun CircularSeekBar(
    modifier: Modifier = Modifier,
    canvasSize : Dp = 300.dp, // width & height (must be equal)
    min: Float = 0f,
    max: Float = 100f,
    initialValue: Float = 50f,
    strokeWidth: Dp = 20.dp,
    title: String = "Volume",
    progressColor: Color = Color(0xFF2962FF),
    backgroundColor: Color = Color.LightGray,
    thumbColor: Color = Color.White,
    onValueChange: (Float) -> Unit
) {

    var value by remember { mutableStateOf(initialValue.coerceIn(min, max)) }

    val animatedValue by animateFloatAsState(
        targetValue = value,
        animationSpec = tween(100),
        label = ""
    )

    val sweepAngle = 360f

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Box(
            modifier = Modifier
                .size(canvasSize),
            contentAlignment = Alignment.Center
        ) {

            Canvas(
                modifier = Modifier
                    .matchParentSize()
                    .pointerInput(Unit) {
                        detectDragGestures { change, _ ->

                         //   val center = size.center
                            val center = Offset(size.width / 2f, size.height / 2f)
                            val touch = change.position

                            val angle = Math.toDegrees(
                                atan2(
                                    (touch.y - center.y).toDouble(),
                                    (touch.x - center.x).toDouble()
                                )
                            ).toFloat()

                            val fixedAngle = (angle + 360f + 90f) % 360f
                            val percent = fixedAngle / 360f

                            value = (min + percent * (max - min))
                                .coerceIn(min, max)

                            onValueChange(value)
                        }
                    }
            ) {

                val stroke = Stroke(
                    width = strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )

                val diameter = size.minDimension
                val radius = diameter / 2

                // Background circle
                drawArc(
                    color = backgroundColor,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = stroke
                )

                val progressSweep = ((animatedValue - min) / (max - min)) * 360f

                // Progress arc
                drawArc(
                    color = progressColor,
                    startAngle = -90f,
                    sweepAngle = progressSweep,
                    useCenter = false,
                    style = stroke
                )

                // Thumb position
                val thumbAngle = Math.toRadians(
                    (progressSweep - 90f).toDouble()
                )

                val thumbX = center.x + radius * cos(thumbAngle).toFloat()
                val thumbY = center.y + radius * sin(thumbAngle).toFloat()

                // Thumb circle indicator
                drawCircle(
                    color = thumbColor,
                    radius = strokeWidth.toPx() / 1.5f,
                    center = Offset(thumbX, thumbY)
                )
            }

            // Center Title + Value
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
             //   Log.e("TAG" , "animatedValue :: $animatedValue")
                Text(
                    text = title,
                    color = White,
                    fontSize = 18.sp ,
                    fontWeight = FontWeight.W500
                )
                Spacer(Modifier.height(15.dp))
                Text(
                    text = animatedValue.roundToInt().toString(),
                    fontWeight = FontWeight.Bold ,
                    fontSize = 35.sp ,
                    color = White ,
                )
            }
        }
    }
}