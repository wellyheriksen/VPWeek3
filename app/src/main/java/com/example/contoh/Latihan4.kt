package com.example.contoh

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import java.util.Locale
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Coffee
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Latihan4Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeeOrderScreen()
        }
    }
}

fun formatRupiah(nominal: Int): String {
    return String.format(Locale.getDefault(), "Rp %,d", nominal).replace(',', '.')
}

@Composable
fun CoffeeOrderScreen() {
    val konteks = LocalContext.current

    var jumlah by remember { mutableStateOf(1) }
    var isLarge by remember { mutableStateOf(false) }

    val hargaSatuan = 25000 + if (isLarge) 6000 else 0
    val subtotal = hargaSatuan * jumlah
    val pajakResto = (subtotal * 0.10).toInt()
    val totalTagihan = subtotal + pajakResto

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFDF9))
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Kopi Kenangan Senja",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3E2723)
            )

            Text(
                text = "☕",
                fontSize = 20.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8F0)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .background(Color(0xFF6D3819), shape = RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Coffee,
                        contentDescription = "Coffee Icon",
                        tint = Color(0xFFFFD180),
                        modifier = Modifier.size(64.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Caramel Latte",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3E2723)
                )

                Text(
                    text = "Espresso shot, steamed milk & caramel syrup",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "${formatRupiah(hargaSatuan)} / cup",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD84315)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "PILIHAN UKURAN:",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = { isLarge = false },
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(
                    width = if (!isLarge) 2.dp else 1.dp,
                    color = if (!isLarge) Color(0xFFD84315) else Color.LightGray
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (!isLarge) Color(0xFFFFF3E0) else Color.Transparent
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Regular (+0)",
                    color = if (!isLarge) Color(0xFFD84315) else Color.Gray,
                    fontWeight = if (!isLarge) FontWeight.Bold else FontWeight.Normal
                )
            }

            OutlinedButton(
                onClick = { isLarge = true },
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(
                    width = if (isLarge) 2.dp else 1.dp,
                    color = if (isLarge) Color(0xFFD84315) else Color.LightGray
                ),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (isLarge) Color(0xFFFFF3E0) else Color.Transparent
                ),
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Large (+6k)",
                    color = if (isLarge) Color(0xFFD84315) else Color.Gray,
                    fontWeight = if (isLarge) FontWeight.Bold else FontWeight.Normal
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEEEEEE)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Jumlah Pesanan:",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF333333)
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconButton(
                        onClick = {
                            if (jumlah > 1) {
                                jumlah--
                            }
                        },
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color(0xFFF5F5F5), CircleShape)
                    ) {
                        Text("-", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                    }

                    Text(
                        text = "$jumlah",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    IconButton(
                        onClick = { jumlah++ },
                        modifier = Modifier
                            .size(36.dp)
                            .background(Color(0xFFFFE0B2), CircleShape)
                    ) {
                        Text("+", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFFD84315))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Subtotal:", color = Color.Gray, fontSize = 14.sp)
                    Text(formatRupiah(subtotal), color = Color.DarkGray, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Pajak Resto (10%):", color = Color.Gray, fontSize = 14.sp)
                    Text(formatRupiah(pajakResto), color = Color.DarkGray, fontSize = 14.sp, fontWeight = FontWeight.Medium)
                }
                Spacer(modifier = Modifier.height(8.dp))

                HorizontalDivider(color = Color(0xFFFFE082))
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Total Tagihan:", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color(0xFF3E2723))
                    Text(formatRupiah(totalTagihan), fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFFD84315))
                }
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = {
                val ukuranTeks = if (isLarge) "Large" else "Regular"
                Toast.makeText(
                    konteks,
                    "Berhasil menambah $jumlah Caramel Latte ($ukuranTeks) ke keranjang!",
                    Toast.LENGTH_SHORT
                ).show()
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD84315)),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(
                text = "TAMBAH KE KERANJANG",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CoffeeOrderPreview() {
    CoffeeOrderScreen()
}
