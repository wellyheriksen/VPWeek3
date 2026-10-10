package com.example.contoh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.ceil

class Latihan2Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CatClickerApp()
        }
    }
}

@Composable
fun CatClickerApp() {
    var koin by remember { mutableStateOf(0) }
    var koinPerPencet by remember { mutableStateOf(1) }
    var harga by remember { mutableStateOf(10) }
    var lagiPencet by remember { mutableStateOf(false) }

    val koinNanti = ceil(koinPerPencet * 1.5).toInt()

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.latarbelakang),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = "Your Coins",
                fontSize = 24.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "$koin",
                fontSize = 56.sp,
                color = Color(0xFF00FF00),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "$koinPerPencet coins per tap",
                fontSize = 16.sp,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = "Tap the Cat!",
                fontSize = 22.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            val gambarKucing = if (lagiPencet) R.drawable.mangap else R.drawable.kucing
            val teksKucing = if (lagiPencet) "Meow!" else "Purr~"

            Image(
                painter = painterResource(id = gambarKucing),
                contentDescription = "Cat Clicker",
                modifier = Modifier
                    .size(200.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onPress = {
                                lagiPencet = true
                                koin += koinPerPencet
                                tryAwaitRelease()
                                lagiPencet = false
                            }
                        )
                    }
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = teksKucing,
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            Card(
                modifier = Modifier.fillMaxWidth(0.9f),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Give Me Your Coin",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Next upgrade: +$koinNanti coins per tap",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    val bisaBeli = koin >= harga

                    Button(
                        onClick = {
                            if (bisaBeli) {
                                koin -= harga
                                harga *= 2
                                koinPerPencet = koinNanti
                            }
                        },
                        enabled = bisaBeli,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50),
                            disabledContainerColor = Color(0xFFBDBDBD)
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        val teksTombol = if (bisaBeli) {
                            "Pay for $harga coins"
                        } else {
                            "Find ${harga - koin} more coins"
                        }

                        Text(
                            text = teksTombol,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CatClickerAppPreview() {
    CatClickerApp()
}
