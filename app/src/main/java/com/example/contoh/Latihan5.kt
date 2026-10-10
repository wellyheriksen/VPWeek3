package com.example.contoh

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Latihan5Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TravelJournalScreen()
        }
    }
}

@Composable
fun TravelJournalScreen() {
    val konteks = LocalContext.current

    var spotWisata by remember { mutableStateOf("Fjellheisen Cable Car") }
    var pengalaman by remember { mutableStateOf("Pemandangan langit malam hijau aurora spektakuler dari puncak gunung...") }
    var catatan by remember { mutableStateOf("") }
    var ratingKepuasan by remember { mutableStateOf("Luar Biasa ★") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val pesanToast = if (spotWisata.isNotBlank()) {
                        "Jurnal untuk '$spotWisata' berhasil disimpan!"
                    } else {
                        "Harap isi nama spot wisata terlebih dahulu!"
                    }
                    Toast.makeText(konteks, pesanToast, Toast.LENGTH_SHORT).show()
                },
                containerColor = Color(0xFF64B5F6),
                contentColor = Color.Black
            ) {
                Icon(Icons.Default.Add, contentDescription = "Simpan Jurnal")
            }
        }
    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF101828))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 80.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E5288)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "LOG PERJALANAN",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF90CAF9)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "Tromsø, Norway ❄️",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Spacer(modifier = Modifier.height(2.dp))

                        Text(
                            text = "Ekspedisi Aurora Borealis",
                            fontSize = 12.sp,
                            color = Color(0xFFBBDEFB)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                CustomInputField(
                    label = "SPOT WISATA FAVORIT",
                    value = spotWisata,
                    onValueChange = { spotWisata = it },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomInputField(
                    label = "APA YANG PALING KAMU NIKMATI?",
                    value = pengalaman,
                    onValueChange = { pengalaman = it },
                    minLines = 3
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomInputField(
                    label = "CATATAN TAMBAHAN / PERLENGKAPAN",
                    value = catatan,
                    onValueChange = { catatan = it },
                    placeholder = "(Ketik perlengkapan ekstra di sini...)",
                    minLines = 1
                )

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "TINGKAT KEPUASAN:",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF90CAF9)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val opsiList = listOf("Biasa", "Seru", "Luar Biasa ★")
                    for (opsi in opsiList) {
                        val isSelected = ratingKepuasan == opsi
                        FilterChip(
                            selected = isSelected,
                            onClick = { ratingKepuasan = opsi },
                            label = {
                                Text(
                                    text = opsi,
                                    color = if (isSelected) Color.Black else Color.White,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                containerColor = Color(0xFF1D2939),
                                selectedContainerColor = Color(0xFF64B5F6)
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                    }
                }
            }

            Text(
                text = "Status: Draft Tersimpan",
                fontSize = 12.sp,
                color = Color(0xFF64B5F6),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun CustomInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    minLines: Int = 1,
    singleLine: Boolean = false
) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFF1D2939),
        border = BorderStroke(1.dp, Color(0xFF344054)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            Text(
                text = label,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF90CAF9)
            )
            Spacer(modifier = Modifier.height(4.dp))
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = singleLine,
                minLines = minLines,
                textStyle = LocalTextStyle.current.copy(
                    color = Color.White,
                    fontSize = 14.sp
                ),
                cursorBrush = SolidColor(Color(0xFF64B5F6)),
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    Box {
                        if (value.isEmpty() && placeholder.isNotEmpty()) {
                            Text(
                                text = placeholder,
                                color = Color(0xFF8A99AD),
                                fontSize = 14.sp
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TravelJournalPreview() {
    TravelJournalScreen()
}
