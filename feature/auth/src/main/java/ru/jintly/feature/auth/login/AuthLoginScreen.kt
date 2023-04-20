package ru.jintly.feature.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 28.dp, vertical = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Spacer(modifier = Modifier.height(168.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
            )
            Spacer(modifier = Modifier.height(48.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = { password = it },
            )
            ClickableText(
                modifier = Modifier.align(Alignment.End),
                text = AnnotatedString(stringResource(id = R.string.auth_login_forgot_password)),
                onClick = { onForgotPasswordClick() },
            )
            Spacer(modifier = Modifier.height(44.dp))
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = onLoginClick,
            ) {
                Text(text = stringResource(id = R.string.auth_login_enter))
            }
        }
        ClickableText(
            modifier = Modifier.weight(1f, false),
            text = AnnotatedString(stringResource(id = R.string.auth_login_register)),
            onClick = { onRegisterClick() },
        )
    }
}

@Preview(
    device = Devices.PHONE,
)
@Composable
private fun AuthLoginScreenPreview() {
    AuthLoginScreen(
        onLoginClick = {},
        onForgotPasswordClick = {},
        onRegisterClick = {},
    )
}
