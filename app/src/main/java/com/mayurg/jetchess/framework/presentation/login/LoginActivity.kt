package com.mayurg.jetchess.framework.presentation.login

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mayurg.jetchess.R
import com.mayurg.jetchess.util.themeutils.AppTheme

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colors.primary),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LoginLogo()
                    EmailField()
                }
            }
        }
    }

    @Composable
    fun LoginLogo() {
        Image(
            painter = painterResource(R.drawable.ic_vector_splash_logo),
            contentDescription = "Login Logo",
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
        )
    }

    @Composable
    fun EmailField() {
        var text by remember { mutableStateOf("") }

        TextField(
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                focusedIndicatorColor = Color.White,
                focusedLabelColor = Color.White
            ),
            value = text,
            onValueChange = { text = it },
            label = { Text("Email") }
        )
    }


}