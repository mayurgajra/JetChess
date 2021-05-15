package com.mayurg.jetchess.framework.presentation.register

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mayurg.jetchess.framework.presentation.base.BaseActivity
import com.mayurg.jetchess.framework.presentation.register.state.RegisterStateEvent
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
class RegisterActivity : BaseActivity() {

    private val viewModel: RegisterUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setupChannel()
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
        val scrollState = rememberScrollState()


        Scaffold(scaffoldState = scaffoldState) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 0.dp)
                    .verticalScroll(scrollState)
                    .clipToBounds()
                    .background(color = MaterialTheme.colors.primary),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Spacer(modifier = Modifier.height(16.dp))
                Icon(
                    Icons.Filled.ArrowBack,
                    "Back Icon",
                    modifier = Modifier
                        .align(Alignment.Start)
                        .clickable {
                            onBackPressed()
                        },
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
                                RegisterStateEvent.RegisterUser(
                                    fullName = nameState.text,
                                    mobile = mobileState.text,
                                    email = emailState.text,
                                    password = passwordState.text,
                                    confirmPassword = confirmPasswordState.text
                                )
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                SignUpText()
                Spacer(modifier = Modifier.height(16.dp))
                if (viewModel.shouldDisplayProgressBar.value == true) {
                    ShowDialog()
                }

            }
        }

    }

    @Composable
    fun SignUpText() {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            PartiallyHighLightedClickableText(
                normalText = "Already have an account? ",
                highlightedText = "Sign In",
                tag = "SignIn",
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                onBackPressed()
            }
        }
    }

    @Composable
    fun ShowDialog() {

        Dialog(
            onDismissRequest = {},
            DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
            ) {
                CircularProgressIndicator(color = Color.Red)
                Text(text = "Loading...")
            }
        }


    }

    fun subscribeObservers() {
        viewModel.shouldDisplayProgressBar.observe(this, {

        })
    }
}