package com.usbbog.cbs_app.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.usbbog.cbs_app.R
import com.usbbog.cbs_app.view.ui.theme.Cbs_appTheme

class Dash : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DefaultPreview()
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {

    val genres = listOf("Casos", "Evidencias")

    Cbs_appTheme {
        Scaffold {
            Column(
                    modifier = Modifier
                            .background(Color.White)
                            .padding(it)
                            .padding(horizontal = 25.dp)
            ) {
                Text(fontSize = 35.sp, modifier = Modifier.padding(bottom = 25.dp), text = buildAnnotatedString {
                    append("¡Estamos Juntos!\n\n")
                    withStyle(style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                    )) {
                        append("Contra el ciberacoso")
                    }
                })
                LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.padding(bottom = 25.dp)
                ) {
                    items(genres) { genre ->
                        Genre(genre)
                    }
                }
                Box(
                        modifier = Modifier
                                .padding(end = 20.dp)
                                .clip(RoundedCornerShape(10))
                                .background(Color.Black)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(
                                modifier = Modifier
                                        .padding(vertical = 30.dp)
                                        .padding(start = 20.dp)
                        ) {
                            Text(
                                    text = "Vocabulario",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.size(10.dp))

                            Text(
                                    text = "El ciberacoso puede ser\n" +
                                            "una experiencia traumática \n" +
                                            "para cualquier persona, \n" +
                                            "pero existen algunas medidas \n" +
                                            "que se pueden tomar para \n" +
                                            "protegerse y prevenir esta situación. \n" +
                                            "Algunas recomendaciones \n" +
                                            "son las siguientes",
                                    color = Color.White,
                                    fontSize = 14.sp
                            )

                            Spacer(modifier = Modifier.size(40.dp))

                            Button(
                                    modifier = Modifier
                                            .size(height = 35.dp, width = 100.dp)
                                            .clip(RoundedCornerShape(20)),
                                    onClick = {},
                                    colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.White,
                                            contentColor = Color.Blue
                                    )
                            ) {
                                Text(
                                        text = "Ver mas",
                                        color = Color.Blue,
                                        fontSize = 14.sp
                                )
                            }

                        }
                    }
                }

                Text(
                        text = "Mis evidencias",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 30.dp)
                )

                LazyColumn(
                        modifier = Modifier.padding(end = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    //ESTO CARGA EL LISTADO DE EVIDENCIAS
                    items(3) {
                        Evidencias()
                    }
                }

            }
        }
    }
}

//FUNCION PARA LAS EVIDENCIAS
@Composable
fun Evidencias() {

    var isSelected by remember {
        mutableStateOf(false)
    }

    Box(
            modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20))
                    .background(colorResource(id = R.color.btnDash)),
            contentAlignment = Alignment.Center
    ) {
        Row(
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                    modifier = Modifier
                            .clip(RoundedCornerShape(20))
                            .background(colorResource(id = R.color.evidenceColor)),
                    contentAlignment = Alignment.Center
            ) {
                Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "icon",
                        tint = Color.White,
                        modifier = Modifier
                                .size(40.dp)
                                .padding(10.dp)
                )
            }

            Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally,
            ){
                Text(
                        text = "Evidencia 01",
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                )
                Text(
                        text = "4/12/2023",
                        color = Color.DarkGray,
                        fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                    onClick = {}

            ) {
                Icon(
                        imageVector = Icons.Filled.KeyboardArrowRight,
                        contentDescription = "icon",
                        tint = if(isSelected) Color.Blue else Color.Black,
                        modifier = Modifier.size(32.dp)
                )
            }

        }
    }

}

@Composable
fun Genre(text: String) {
    var isSelected by remember { mutableStateOf(false) }
    Box(
            modifier = Modifier
                    .clip(RoundedCornerShape(30))
                    .background(if (isSelected) Color.Black else colorResource(id = R.color.btnDash))
                    .clickable {
                        isSelected = !isSelected
                    },
            contentAlignment = Alignment.Center
    ) {
        Text(
                modifier = Modifier.padding(10.dp),
                text = text,
                color = if (isSelected) Color.White else Color.Black,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
        )
    }
}
