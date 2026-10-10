package com.example.contoh

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.ceil

class Latihan3Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ColorWordMatchingApp()
        }
    }
}

enum class GameState {
    START, COUNTDOWN, RUNNING, GAME_OVER
}

enum class Mode {
    COLOR, TEXT
}

enum class Choice {
    INK, WORD
}

enum class ColorName(val label: String, val color: Color) {
    RED("RED", Color(0xFFE53935)),
    BLUE("BLUE", Color(0xFF1E88E5)),
    GREEN("GREEN", Color(0xFF4CAF50)),
    ORANGE("ORANGE", Color(0xFFFB8C00)),
    PURPLE("PURPLE", Color(0xFF8E24AA)),
    YELLOW("YELLOW", Color(0xFFFBC02D))
}

@Composable
fun ColorWordMatchingApp() {
    var status by remember { mutableStateOf(GameState.START) }
    var modeAktif by remember { mutableStateOf(Mode.COLOR) }
    var skor by remember { mutableStateOf(0) }
    var salah by remember { mutableStateOf(0) }

    var skorBest by remember { mutableStateOf(0) }

    var kata by remember { mutableStateOf(ColorName.RED) }
    var warnaTinta by remember { mutableStateOf(ColorName.BLUE) }
    var tukarTombol by remember { mutableStateOf(false) }

    var sisaWaktuMs by remember { mutableStateOf(5000L) }
    var teksHitungan by remember { mutableStateOf("3") }

    fun acakSoal() {
        val listWarna = ColorName.entries
        val wordBaru = listWarna.random()
        var inkBaru = listWarna.random()

        while (inkBaru == wordBaru) {
            inkBaru = listWarna.random()
        }

        kata = wordBaru
        warnaTinta = inkBaru
        modeAktif = Mode.entries.random()
        tukarTombol = (0..1).random() == 1
        sisaWaktuMs = 5000L
    }

    fun jawab(pilihan: Choice) {
        val jawabanBenar = if (modeAktif == Mode.COLOR) Choice.INK else Choice.WORD

        if (pilihan == jawabanBenar) {
            skor++
            if (skor > skorBest) {
                skorBest = skor
            }
            acakSoal()
        } else {
            salah++
            if (salah >= 3) {
                status = GameState.GAME_OVER
            } else {
                acakSoal()
            }
        }
    }

    LaunchedEffect(status, teksHitungan) {
        if (status == GameState.COUNTDOWN) {
            delay(700L)
            when (teksHitungan) {
                "3" -> teksHitungan = "2"
                "2" -> teksHitungan = "1"
                "1" -> teksHitungan = "Start!"
                "Start!" -> {
                    skor = 0
                    salah = 0
                    acakSoal()
                    status = GameState.RUNNING
                }
            }
        }
    }

    LaunchedEffect(status, kata, warnaTinta, sisaWaktuMs) {
        if (status == GameState.RUNNING) {
            if (sisaWaktuMs > 0) {
                delay(100L)
                sisaWaktuMs -= 100L
            } else {
                salah++
                if (salah >= 3) {
                    status = GameState.GAME_OVER
                } else {
                    acakSoal()
                }
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color(0xFFFAFAFA)
    ) {
        when (status) {
            GameState.START -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Welcome",
                        fontSize = 28.sp,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "to",
                        fontSize = 20.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Color Word Matching",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                    Button(
                        onClick = {
                            teksHitungan = "3"
                            status = GameState.COUNTDOWN
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF90A4AE)
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = "Start Game",
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            GameState.COUNTDOWN -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = teksHitungan,
                        fontSize = 44.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF424242)
                    )
                }
            }

            GameState.RUNNING -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Mode: ${modeAktif.name}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.DarkGray
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "✅ $skor",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32)
                            )
                            Spacer(modifier = Modifier.width(12.dp))

                            Text(
                                text = "❌ $salah/3",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFC62828)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(0.8f))

                    val detik = ceil(sisaWaktuMs / 1000.0).toInt()
                    Text(
                        text = "$detik s",
                        fontSize = 22.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = kata.label,
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold,
                        color = warnaTinta.color
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    val clueText = if (modeAktif == Mode.COLOR) "Pilih WARNA TINTA" else "Pilih NAMA KATA"
                    Text(
                        text = clueText,
                        fontSize = 14.sp,
                        color = Color.Gray,
                        fontWeight = FontWeight.Normal
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    val (pilihanKiri, teksKiri) = if (tukarTombol) Pair(Choice.INK, warnaTinta.label) else Pair(Choice.WORD, kata.label)
                    val (pilihanKanan, teksKanan) = if (tukarTombol) Pair(Choice.WORD, kata.label) else Pair(Choice.INK, warnaTinta.label)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 48.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {jawab(pilihanKiri)},
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFB0BEC5)
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.width(130.dp)
                        ) {
                            Text(
                                text = teksKiri,
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Button(
                            onClick = { jawab(pilihanKanan) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFB0BEC5)
                            ),
                            shape = RoundedCornerShape(20.dp),
                            modifier = Modifier.width(130.dp)
                        ) {
                            Text(
                                text = teksKanan,
                                fontSize = 14.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
            GameState.GAME_OVER -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Game Over!",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Your Score",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$skor",
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Best Score",
                        fontSize = 16.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "$skorBest",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(36.dp))

                    Button(
                        onClick = {
                            teksHitungan = "3"
                            status = GameState.COUNTDOWN
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF90A4AE)
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.padding(horizontal = 32.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Restart Game",
                            fontSize = 16.sp,
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            status = GameState.START
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFCFD8DC)
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.padding(horizontal = 32.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Exit",
                            fontSize = 16.sp,
                            color = Color.DarkGray,
                            modifier = Modifier.padding(horizontal = 24.dp, vertical = 2.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ColorWordMatchingAppPreview() {
    ColorWordMatchingApp()
}
