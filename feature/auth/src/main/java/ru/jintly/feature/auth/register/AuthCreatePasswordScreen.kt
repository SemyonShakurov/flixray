package ru.jintly.feature.auth.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.jintly.core.designsystem.theme.JintlyTheme
import ru.jintly.feature.auth.R.string

@Composable
internal fun AuthCreatePasswordRoute(
    viewModel: AuthCreatePasswordViewModel = hiltViewModel(),
) {
    AuthCreatePasswordScreen(
        onCompleteAuthClick = {
            viewModel.onCompleteAuthClick(
                onAuthSuccess = {},
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AuthCreatePasswordScreen(
    modifier: Modifier = Modifier,
    onCompleteAuthClick: () -> Unit,
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(232.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
        )
        Spacer(modifier = Modifier.height(48.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
        )
        Spacer(modifier = Modifier.height(44.dp))
        Button(onClick = onCompleteAuthClick) {
            Text(text = stringResource(id = string.auth_create_password_complete))
        }
    }
}

@Preview(device = Devices.PHONE)
@Composable
private fun AuthCreatePasswordScreenPreview() {
    JintlyTheme {
        AuthCreatePasswordScreen(
            onCompleteAuthClick = {},
        )
    }
}
