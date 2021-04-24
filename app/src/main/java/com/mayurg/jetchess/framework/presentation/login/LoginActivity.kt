package com.mayurg.jetchess.framework.presentation.login

import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.mayurg.jetchess.R
import com.mayurg.jetchess.framework.presentation.base.BaseActivity
import com.mayurg.jetchess.util.themeutils.AppTheme
import com.mayurg.jetchess.util.themeutils.subtitle1TextColor
import kotlinx.coroutines.launch

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                Login()
            }
        }
    }

    class TextFieldState {
        var text: String by mutableStateOf("")
    }

    @Composable
    private fun Login() {
        val emailState = remember { TextFieldState() }
        val scope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState()


        Scaffold(scaffoldState = scaffoldState) {
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
                EmailField(emailState)
                Spacer(modifier = Modifier.height(16.dp))
                PassWordField()
                Spacer(modifier = Modifier.height(32.dp))
                LoginButton {
                    if (emailState.text.isEmpty()) {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Email is empty")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Forgot Password?",
                    style = MaterialTheme.typography.h6,
                    color = MaterialTheme.colors.secondaryVariant,
                    textDecoration = TextDecoration.Underline
                )
                SignUpText()

            }
        }

    }

    @Composable
    fun SignUpText() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val annotatedText = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = subtitle1TextColor,
                    )
                ) {
                    append("Don't have an account? ")

                }

                pushStringAnnotation(
                    tag = "SignUp",
                    annotation = "SignUp"
                )
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colors.secondaryVariant,
                    )
                ) {
                    append("Sign Up")
                }

                pop()
            }

            ClickableText(
                text = annotatedText,
                modifier = Modifier.align(Alignment.BottomCenter),
                onClick = { offset ->
                    annotatedText.getStringAnnotations(
                        tag = "SignUp", start = offset,
                        end = offset
                    )[0].let { annotation ->
                        Log.d("Clicked", annotation.item)
                    }
                }
            )
        }
    }

    @Composable
    private fun LoginButton(onValidate: () -> Unit) {
        Button(
            onClick = onValidate,
            modifier = Modifier
                .fillMaxWidth(0.5f),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.secondary
            )
        ) {
            Box(modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.h5
                )
            }
        }
    }

    @Composable
    fun LoginLogo() {
        Image(
            painter = painterResource(R.drawable.ic_vector_app_logo),
            contentDescription = "Login Logo",
            modifier = Modifier
                .width(120.dp)
                .height(120.dp)
        )
    }

    @Composable
    fun EmailField(emailState: TextFieldState = remember { TextFieldState() }) {
        TextField(
            modifier = Modifier.fillMaxWidth(0.9f),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onPrimary,
                focusedIndicatorColor = MaterialTheme.colors.secondary,
                focusedLabelColor = MaterialTheme.colors.secondary
            ),
            value = emailState.text,
            onValueChange = { emailState.text = it },
            label = { Text("Email") },
            leadingIcon = {
                Icon(
                    Icons.Filled.Email,
                    "Email Icon"
                )
            }
        )
    }

    @Composable
    fun PassWordField() {
        var password by rememberSaveable { mutableStateOf("") }

        TextField(
            modifier = Modifier.fillMaxWidth(0.9f),
            colors = TextFieldDefaults.textFieldColors(
                textColor = MaterialTheme.colors.onPrimary,
                focusedIndicatorColor = MaterialTheme.colors.secondary,
                focusedLabelColor = MaterialTheme.colors.secondary
            ),
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            leadingIcon = {
                Icon(
                    Icons.Filled.Lock,
                    "Password Icon"
                )
            }
        )
    }


}