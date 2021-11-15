package com.mayurg.jetchess.framework.presentation.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mayurg.jetchess.R
import com.mayurg.jetchess.business.domain.state.StateMessageCallback
import com.mayurg.jetchess.framework.presentation.base.BaseActivity
import com.mayurg.jetchess.framework.presentation.login.state.LoginStateEvent
import com.mayurg.jetchess.framework.presentation.main.MainActivity
import com.mayurg.jetchess.framework.presentation.register.RegisterActivity
import com.mayurg.jetchess.framework.presentation.utils.reusableviews.LoginRegisterButton
import com.mayurg.jetchess.framework.presentation.utils.reusableviews.LoginRegisterTextField
import com.mayurg.jetchess.framework.presentation.utils.reusableviews.PartiallyHighLightedClickableText
import com.mayurg.jetchess.framework.presentation.utils.themeutils.AppTheme
import com.mayurg.jetchess.util.TextFieldState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
@ExperimentalFoundationApi
class LoginActivity : BaseActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setupChannel()
        subscribeObservers()
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
                    when {
                        emailState.text.isEmpty() -> {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("Email is empty")
                            }
                        }
                        passwordState.text.isEmpty() -> {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("Password is empty")
                            }
                        }
                        else -> {
                            viewModel.setStateEvent(
                                LoginStateEvent.LoginUser(
                                    email = emailState.text,
                                    password = passwordState.text
                                )
                            )
                        }
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
                ShowDialog()

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

    private fun moveToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finishAffinity()
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
    fun ShowDialog() {
        val showDialog by viewModel.shouldDisplayProgressBar.observeAsState()

        if (showDialog == true) {
            Dialog(
                onDismissRequest = {},
                DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colors.secondary)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Logging In...",
                            style = MaterialTheme.typography.h6,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }

    private fun subscribeObservers() {
        viewModel.viewState.observe(this) {
            it?.baseResponseModel?.let { model ->
                Toast.makeText(this, model.message, Toast.LENGTH_SHORT).show()
                moveToMain()
            }
        }

        viewModel.stateMessage.observe(this) { stateMessage ->
            stateMessage?.let { msg ->
                onResponseReceived(msg.response, stateMessageCallback = object :
                    StateMessageCallback {
                    override fun removeMessageFromStack() {
                        viewModel.clearStateMessage()
                    }
                })
            }
        }
    }


}