package com.mayurg.jetchess.framework.presentation.register

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mayurg.jetchess.framework.presentation.base.BaseActivity
import com.mayurg.jetchess.util.TextFieldState
import com.mayurg.jetchess.util.reusableviews.LoginRegisterButton
import com.mayurg.jetchess.util.reusableviews.LoginRegisterTextField
import com.mayurg.jetchess.util.reusableviews.PartiallyHighLightedClickableText
import com.mayurg.jetchess.util.themeutils.AppTheme
import kotlinx.coroutines.launch

class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Register()
            }
        }
    }

    @Composable
    private fun Register() {
        val nameState = remember { TextFieldState() }
        val mobileState = remember { TextFieldState() }
        val emailState = remember { TextFieldState() }
        val passwordState = remember { TextFieldState() }
        val confirmPasswordState = remember { TextFieldState() }
        val scope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState()


        Scaffold(scaffoldState = scaffoldState) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
                    .background(color = MaterialTheme.colors.primary),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(32.dp))
                Icon(
                    Icons.Filled.ArrowBack,
                    "Back Icon",
                    modifier = Modifier.align(Alignment.Start)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Create Account",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Please fill the input below here",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.subtitle1,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(32.dp))
                LoginRegisterTextField("Full Name", Icons.Filled.Person, nameState)

                Spacer(modifier = Modifier.height(16.dp))
                LoginRegisterTextField("Mobile", Icons.Filled.Phone, mobileState)

                Spacer(modifier = Modifier.height(16.dp))
                LoginRegisterTextField("Email", Icons.Filled.Email, emailState)

                Spacer(modifier = Modifier.height(16.dp))
                LoginRegisterTextField(
                    label = "Password",
                    imageVector = Icons.Filled.Lock,
                    textFieldState = passwordState,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Spacer(modifier = Modifier.height(16.dp))
                LoginRegisterTextField(
                    label = "Confirm Password",
                    imageVector = Icons.Filled.Lock,
                    textFieldState = confirmPasswordState,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Spacer(modifier = Modifier.height(32.dp))
                LoginRegisterButton("Sign Up") {
                    if (emailState.text.isEmpty()) {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Email is empty")
                        }
                    } else if (passwordState.text.isEmpty()) {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Password is empty")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                SignInText()

            }
        }

    }

    @Composable
    fun SignInText() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            PartiallyHighLightedClickableText(
                normalText = "Already have an account? ",
                highlightedText = "Sign In",
                tag = "SignIn",
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {

            }
        }
    }
}