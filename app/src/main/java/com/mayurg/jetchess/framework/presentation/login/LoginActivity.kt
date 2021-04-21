package com.mayurg.jetchess.framework.presentation.login

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
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
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(32.dp))
                    LoginLogo()
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = "Login",
                        modifier = Modifier.fillMaxWidth(0.9f),
                        style = MaterialTheme.typography.h5,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Please sign in to continue",
                        modifier = Modifier.fillMaxWidth(0.9f),
                        style = MaterialTheme.typography.subtitle1,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    EmailField()
                    Spacer(modifier = Modifier.height(16.dp))
                    PassWordField()
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.Yellow,
                            contentColor = Color.White
                        )
                    ) {
                        Text(text = "Login")
                    }
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
            modifier = Modifier.fillMaxWidth(0.9f),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                focusedIndicatorColor = Color.White,
                focusedLabelColor = Color.White
            ),
            value = text,
            onValueChange = { text = it },
            label = { Text("Email") },
            leadingIcon = {
                Icon(
                    Icons.Filled.Email,
                    "contentDescription",
                    modifier = Modifier.clickable {})
            }
        )
    }

    @Composable
    fun PassWordField() {
        var text by remember { mutableStateOf("") }

        TextField(
            modifier = Modifier.fillMaxWidth(0.9f),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                focusedIndicatorColor = Color.White,
                focusedLabelColor = Color.White
            ),
            value = text,
            onValueChange = { text = it },
            label = { Text("Password") },
            leadingIcon = {
                Icon(
                    Icons.Filled.Lock,
                    "contentDescription",
                    modifier = Modifier.clickable {})
            }
        )
    }


}