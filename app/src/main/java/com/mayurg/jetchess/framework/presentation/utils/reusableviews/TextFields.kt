package com.mayurg.jetchess.framework.presentation.utils.reusableviews

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.mayurg.jetchess.util.TextFieldState
import com.mayurg.jetchess.framework.presentation.utils.themeutils.subtitle1TextColor


@Composable
fun PassWordField(
    label: String,
    imageVector: ImageVector,
    textFieldState: TextFieldState = remember { TextFieldState() }
) {
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

@Composable
fun LoginRegisterTextField(
    label: String,
    imageVector: ImageVector,
    textFieldState: TextFieldState = remember { TextFieldState() },
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
) {
    TextField(
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            textColor = MaterialTheme.colors.onPrimary,
            focusedIndicatorColor = MaterialTheme.colors.secondary,
            focusedLabelColor = MaterialTheme.colors.secondary
        ),
        value = textFieldState.text,
        onValueChange = { textFieldState.text = it },
        label = { Text(label) },
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        leadingIcon = {
            Icon(
                imageVector,
                "$label Icon"
            )
        }
    )
}

@Composable
fun PartiallyHighLightedClickableText(
    normalText: String,
    highlightedText: String,
    tag: String,
    modifier: Modifier = Modifier,
    onHighLightedTextClick: (tag: String) -> Unit
) {
    val annotatedText = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                color = subtitle1TextColor,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
        ) {
            append(normalText)
        }

        pushStringAnnotation(
            tag = tag,
            annotation = tag
        )
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.secondaryVariant,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                textDecoration = TextDecoration.Underline
            )
        ) {
            append(highlightedText)
        }

        pop()
    }

    ClickableText(
        text = annotatedText,
        modifier = modifier,
        onClick = { offset ->
            annotatedText.getStringAnnotations(
                tag = tag, start = offset,
                end = offset
            )[0].let { annotation ->
                onHighLightedTextClick(annotation.item)
                Log.d("Clicked", annotation.item)
            }
        }
    )
}