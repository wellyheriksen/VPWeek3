package com.example.contoh

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DirectionsRun
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class Latihan1GameState {
    START, WAITING, READY, TRIAL_SUCCESS, TRIAL_FAIL, RESULT
}

enum class ResultCategory(val message: String, val bgColor: Color, @param:DrawableRes val imageRes: Int) {
    FAST("DANG YOU ARE SO FAST BRO!", Color(0xFF00E676), R.drawable.gambarsiap),
    GOOD("YOUR REFLEX IS GOOD", Color(0xFF2196F3), R.drawable.gambarsuka),
    NORMAL("MEH LIKE OTHER PERSON", Color(0xFFFF9800), R.drawable.gambarsedang),
    SLOW("YOU LIKE A SNAIL BRO", Color(0xFFFF5722), R.drawable.gambartidak)
}

@Composable
fun ReactionGameScreen() {
    var status by remember { mutableStateOf(Latihan1GameState.START) }
    var ronde by remember { mutableStateOf(0) }
    var hasil by remember { mutableStateOf(List<Long?>(3) { null }) }
    var waktuMulai by remember { mutableStateOf(0L) }

    val coroutineScope = rememberCoroutineScope()
    var jobTunggu by remember { mutableStateOf<Job?>(null) }

    fun hitungRataRata(): Long {
        val dataValid = hasil.filterNotNull().filter { it > 0 }
        return if (dataValid.isNotEmpty()) dataValid.average().toLong() else 0L
    }

    fun cekKategori(rataRata: Long): ResultCategory {
        return when {
            rataRata == 0L -> ResultCategory.SLOW
            rataRata < 180 -> ResultCategory.FAST
            rataRata < 280 -> ResultCategory.GOOD
            rataRata < 450 -> ResultCategory.NORMAL
            else -> ResultCategory.SLOW
        }
    }

    fun lanjutRonde() {
        ronde++
        if (ronde >= 3) {
            status = Latihan1GameState.RESULT
        } else {
            status = Latihan1GameState.START
        }
    }

    val warnaBg = when (status) {
        Latihan1GameState.START -> Color(0xFF5CE1E6)
        Latihan1GameState.WAITING -> Color(0xFFD3D3D3)
        Latihan1GameState.READY, Latihan1GameState.TRIAL_SUCCESS -> Color(0xFF4CAF50)
        Latihan1GameState.TRIAL_FAIL -> Color(0xFFF44336)
        Latihan1GameState.RESULT -> cekKategori(hitungRataRata()).bgColor
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(warnaBg)
            .clickable {
                when (status) {
                    Latihan1GameState.START -> {
                        if (ronde == 0) {
                            hasil = List(3) { null }
                        }
                        status = Latihan1GameState.WAITING
                        jobTunggu?.cancel()

                        jobTunggu = coroutineScope.launch {
                            val delayAcak = (500..4500).random().toLong()
                            delay(delayAcak)
                            status = Latihan1GameState.READY
                            waktuMulai = System.currentTimeMillis()
                        }
                    }
                    Latihan1GameState.WAITING -> {
                        jobTunggu?.cancel()
                        val dataBaru = hasil.toMutableList()
                        dataBaru[ronde] = -1L
                        hasil = dataBaru
                        status = Latihan1GameState.TRIAL_FAIL
                    }

                    Latihan1GameState.READY -> {
                        val lamaPencet = System.currentTimeMillis() - waktuMulai
                        val dataBaru = hasil.toMutableList()
                        dataBaru[ronde] = lamaPencet
                        hasil = dataBaru
                        status = Latihan1GameState.TRIAL_SUCCESS
                    }

                    Latihan1GameState.TRIAL_SUCCESS, Latihan1GameState.TRIAL_FAIL -> {
                        lanjutRonde()
                    }

                    Latihan1GameState.RESULT -> {
                        ronde = 0
                        status = Latihan1GameState.START
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            when (status) {
                Latihan1GameState.START -> {
                    Text("Reaction", fontSize = 32.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(24.dp))
                    Icon(Icons.Default.Bolt, contentDescription = "Lightning", tint = Color.White, modifier = Modifier.size(80.dp))
                    Spacer(modifier = Modifier.height(24.dp))

                    Text("Test", fontSize = 24.sp, color = Color.White)
                    Text("Click to Start", fontSize = 16.sp, color = Color.White)

                    if (ronde > 0) {
                        Spacer(modifier = Modifier.height(32.dp))
                        TrialResultsBox(hasil, hitungRataRata())
                    }
                }

                Latihan1GameState.WAITING -> {
                    Text("Get Ready", fontSize = 32.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(24.dp))
                    Icon(Icons.Default.Warning, contentDescription = "Warning", tint = Color.White, modifier = Modifier.size(80.dp))

                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Wait for green light...", fontSize = 20.sp, color = Color.White)
                    Text("DON'T CLICK YET", fontSize = 16.sp, color = Color.Red, fontWeight = FontWeight.Bold)
                }

                Latihan1GameState.READY -> {
                    Text("GO!", fontSize = 40.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    Icon(Icons.Default.DirectionsRun, contentDescription = "Run", tint = Color.White, modifier = Modifier.size(80.dp))

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("CLICK NOW!", fontSize = 24.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    Text("TAP AS FAST AS YOU CAN!", fontSize = 16.sp, color = Color.White)
                }

                Latihan1GameState.TRIAL_SUCCESS -> {
                    Text("Trial ${ronde + 1} Complete!", fontSize = 28.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    Icon(Icons.Default.CheckCircle, contentDescription = "Success", tint = Color.White, modifier = Modifier.size(80.dp))

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Time: ${hasil[ronde]}ms", fontSize = 24.sp, color = Color.White)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(if (ronde < 2) "Continue to Trial ${ronde + 2}" else "See Final Result", fontSize = 16.sp, color = Color.White)

                    Spacer(modifier = Modifier.height(32.dp))
                    TrialResultsBox(hasil, hitungRataRata())
                }

                Latihan1GameState.TRIAL_FAIL -> {
                    Text("FAIL!", fontSize = 32.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))
                    Icon(Icons.Default.ThumbDown, contentDescription = "Fail", tint = Color.White, modifier = Modifier.size(80.dp))

                    Spacer(modifier = Modifier.height(16.dp))
                    Text("You clicked too early.", fontSize = 18.sp, color = Color.White, textAlign = TextAlign.Center)
                    Text("TRY TO READ THE RULE BRO", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("TRY AGAIN", fontSize = 16.sp, color = Color.White)

                    Spacer(modifier = Modifier.height(32.dp))
                    TrialResultsBox(hasil, hitungRataRata())
                }

                Latihan1GameState.RESULT -> {
                    val rerata = hitungRataRata()
                    val kategori = cekKategori(rerata)

                    Text(kategori.message, fontSize = 26.sp, color = Color.White, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(24.dp))

                    Image(
                        painter = painterResource(id = kategori.imageRes),
                        contentDescription = "Result Photo",
                        modifier = Modifier.size(150.dp)
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                    Text("Average: ${if (rerata > 0) "${rerata}ms" else "FAIL"}", fontSize = 20.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Click to Start New Test", fontSize = 14.sp, color = Color.White)

                    Spacer(modifier = Modifier.height(32.dp))
                    TrialResultsBox(hasil, rerata)
                }
            }
        }
    }
}

@Composable
fun TrialResultsBox(hasil: List<Long?>, rerata: Long) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .padding(8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Trial Results", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                for (i in 0..2) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("${i + 1}", color = Color.Gray, fontWeight = FontWeight.Bold)
                        val t = hasil[i]
                        val teksWaktu = when {
                            t == null -> "-"
                            t == -1L -> "Fail"
                            else -> "${t}ms"
                        }
                        Text(teksWaktu, color = if (t == -1L) Color.Red else Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(12.dp))

            Text("Average Valid", color = Color.Gray, fontSize = 14.sp)
            Text(if (rerata > 0) "${rerata}ms" else "-", color = Color(0xFF2196F3), fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ReactionGameScreenPreview() {
    ReactionGameScreen()
}
