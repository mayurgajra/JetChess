package com.mayurg.jetchess.framework.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mayurg.jetchess.R
import com.mayurg.jetchess.framework.presentation.base.BaseActivity
import com.mayurg.jetchess.framework.presentation.main.MainActivity
import com.mayurg.jetchess.util.hideSystemUI

/**
 * Splash activity to display logo till sum required processing is done
 *
 * Future TODOs:
 * 1.) Make sync calls
 * 2.) Redirection logic
 */
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xff201A30)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_vector_splash_logo),
                    contentDescription = "Splash Logo",
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp)
                )
                Text(
                    stringResource(id = R.string.app_name),
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Serif
                )
            }
        }

        /**
         * Make it full screen
         */
        hideSystemUI()

        after{
            /**
             * Navigate to main activity and finish this activity
             */
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

    }


}