package com.example.contoh


import android.R.attr.width
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.contoh.ui.theme.ContohTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ContohTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    Test()
                }
            }
        }
    }
}

@Composable
fun Test(modifier: Modifier = Modifier) {
//    Box(Modifier.fillMaxSize()){
//        Row(Modifier.fillMaxSize(),
//            Arrangement.SpaceEvenly) {
//            Box(
//                Modifier
//                    .background(Color.Blue)
//                    .fillMaxHeight()
//                    .weight(1f)
//            )
//            Box(
//                Modifier
//                    .background(Color.Yellow)
//                    .fillMaxHeight()
//                    .weight(1f)
//            )
//        }



//        LazyRow(Modifier.fillMaxSize()) { }

}
@Preview(showBackground = true, showSystemUi = true) // buat ada tampilan layar
@Composable
fun TestPreview() {
    ContohTheme{
        Test()
    }
}