package ru.jintly.feature.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.jintly.core.designsystem.colors.Primary
import ru.jintly.core.designsystem.theme.JintlyTheme
import ru.jintly.feature.auth.R

@Composable
internal fun AuthLoginRoute(
    onForgotPasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
    viewModel: AuthLoginViewModel = hiltViewModel(),
) {
    AuthLoginScreen(
        onLoginClick = viewModel::onLoginClick,
        onForgotPasswordClick = onForgotPasswordClick,
        onRegisterClick = onRegisterClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AuthLoginScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onRegisterClick: () -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 28.dp, vertical = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Spacer(modifier = Modifier.height(136.dp))
            Text(
                modifier = Modifier.padding(start = 24.dp),
                text = stringResource(id = R.string.auth_email),
                color = Color.White,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                placeholder = { Text(text = stringResource(id = R.string.auth_email_hint)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.padding(start = 24.dp),
                text = stringResource(id = R.string.auth_password),
                color = Color.White,
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { password = it },
                shape = RoundedCornerShape(16.dp),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                ),
                placeholder = { Text(text = stringResource(id = R.string.auth_login_password_hint)) },
                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (isPasswordVisible) {
                        Icons.Filled.Visibility
                    } else {
                        Icons.Filled.VisibilityOff
                    }

                    IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                        Icon(imageVector = image, contentDescription = null)
                    }
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            ClickableText(
                modifier = Modifier.align(Alignment.End),
                text = AnnotatedString(stringResource(id = R.string.auth_login_forgot_password)),
                onClick = { onForgotPasswordClick() },
                style = TextStyle.Default.copy(
                    color = Color.White,
                    fontSize = 16.sp,
                ),
            )
            Spacer(modifier = Modifier.height(44.dp))
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .height(52.dp),
                onClick = onLoginClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                ),
            ) {
                Text(
                    text = stringResource(id = R.string.auth_login_enter),
                    fontSize = 16.sp,
                )
            }
        }
        Column(modifier = Modifier.weight(1f, false)) {
            Text(
                text = stringResource(id = R.string.auth_login_no_account),
                color = Color(0xFFA6A6A6),
                fontSize = 16.sp,
            )
            Spacer(modifier = Modifier.height(4.dp))
            ClickableText(
                text = AnnotatedString(stringResource(id = R.string.auth_login_register)),
                onClick = { onRegisterClick() },
                style = TextStyle.Default.copy(
                    color = Color.White,
                    fontSize = 16.sp,
                ),
            )
        }
    }
}

@Preview(
    device = Devices.PHONE,
)
@Composable
private fun AuthLoginScreenPreview() {
    JintlyTheme {
        AuthLoginScreen(
            onLoginClick = {},
            onForgotPasswordClick = {},
            onRegisterClick = {},
        )
    }
}
