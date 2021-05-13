package com.mayurg.jetchess.framework.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.mayurg.jetchess.R
import com.mayurg.jetchess.framework.presentation.base.BaseActivity
import com.mayurg.jetchess.framework.presentation.login.state.LoginStateEvent
import com.mayurg.jetchess.framework.presentation.login.state.LoginUserViewState
import com.mayurg.jetchess.framework.presentation.register.RegisterActivity
import com.mayurg.jetchess.util.TextFieldState
import com.mayurg.jetchess.framework.presentation.utils.reusableviews.LoginRegisterButton
import com.mayurg.jetchess.framework.presentation.utils.reusableviews.LoginRegisterTextField
import com.mayurg.jetchess.framework.presentation.utils.reusableviews.PartiallyHighLightedClickableText
import com.mayurg.jetchess.framework.presentation.utils.themeutils.AppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class LoginActivity : BaseActivity() {

    private val viewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setupChannel()
        setContent {
            AppTheme {
                Login()
            }
        }
    }


    @Composable
    private fun Login() {
        val emailState = remember { TextFieldState() }
        val passwordState = remember { TextFieldState() }
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
                LoginLogo()
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Login",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Please sign in to continue",
                    modifier = Modifier.fillMaxWidth(),
                    style = MaterialTheme.typography.subtitle1,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(32.dp))
                LoginRegisterTextField("Email", Icons.Filled.Email, emailState)
                Spacer(modifier = Modifier.height(16.dp))
                LoginRegisterTextField(
                    label = "Password",
                    imageVector = Icons.Filled.Lock,
                    textFieldState = passwordState,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Spacer(modifier = Modifier.height(32.dp))
                LoginRegisterButton("Login") {
                    if (emailState.text.isEmpty()) {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Email is empty")
                        }
                    } else if (passwordState.text.isEmpty()) {
                        scope.launch {
                            scaffoldState.snackbarHostState.showSnackbar("Password is empty")
                        }
                    } else {
                        viewModel.setStateEvent(
                            LoginStateEvent.LoginUser(
                                email = emailState.text,
                                password = passwordState.text
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
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
            PartiallyHighLightedClickableText(
                normalText = "Don't have an account? ",
                highlightedText = "Sign Up",
                tag = "SignUp",
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                moveToRegister()
            }
        }
    }

    private fun moveToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
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


}