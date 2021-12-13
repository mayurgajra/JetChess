package com.mayurg.jetchess.framework.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mayurg.jetchess.R
import com.mayurg.jetchess.framework.presentation.base.BaseActivity
import com.mayurg.jetchess.framework.presentation.login.LoginActivity
import com.mayurg.jetchess.framework.presentation.main.MainActivity
import com.mayurg.jetchess.framework.presentation.splash.state.SplashStateEvent
import com.mayurg.jetchess.framework.presentation.utils.themeutils.AppTheme
import com.mayurg.jetchess.util.hideSystemUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

/**
 * Splash activity to display logo till sum required processing is done
 *
 * Future TODOs:
 * 1.) Make sync calls
 * 2.) Redirection logic
 */

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
@ExperimentalFoundationApi
class SplashActivity : BaseActivity() {

    companion object {
        /**
         * Temp variable for splash duration will be replaced with network calls
         *
         */
        private const val SPLASH_DELAY = 3000L

        /**
         * Used to run function after delay
         *
         * @param process is lambda function which will be invoked after the delay [SPLASH_DELAY]
         */
        fun after(process: () -> Unit) {
            Handler(Looper.getMainLooper()).postDelayed({
                process()
            }, SPLASH_DELAY)
        }
    }

    private val viewModel: SplashViewModel by viewModels()

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
                    SplashLogo()
                    SplashText()
                }
            }

        }

        /**
         * Make it full screen
         */
        hideSystemUI()

        after {
            /**
             * Navigate to login/main activity and finish this activity
             */
            viewModel.setStateEvent(SplashStateEvent.GetUserData)
        }

        viewModel.viewState.observe(this) { splashViewState ->
            splashViewState.userDataSaved?.let {
                moveToMain()
            } ?: moveToLogin()
        }

    }

    private fun moveToMain() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    private fun moveToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    @Composable
    fun SplashLogo() {
        Image(
            painter = painterResource(R.drawable.ic_vector_app_logo),
            contentDescription = "Splash Logo",
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
        )
    }

    @Composable
    fun SplashText() {
        Text(
            stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.h3
        )
    }


}