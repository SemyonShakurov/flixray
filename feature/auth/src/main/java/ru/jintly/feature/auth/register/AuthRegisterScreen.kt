package ru.jintly.feature.auth.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.jintly.feature.auth.R

@Composable
internal fun AuthRegisterRoute(
    onSendCodeSuccess: () -> Unit,
    viewModel: AuthRegisterViewModel = hiltViewModel(),
) {
    AuthRegisterScreen(
        onSendCodeClick = {
            viewModel.onSendCodeClick(
                onSendCodeSuccess = onSendCodeSuccess,
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AuthRegisterScreen(
    modifier: Modifier = Modifier,
    onSendCodeClick: () -> Unit,
) {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(horizontal = 28.dp, vertical = 144.dp)
            .fillMaxSize(),
    ) {
        Text(text = stringResource(id = R.string.auth_register_enter_email))
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onSendCodeClick) {
            Text(text = stringResource(id = R.string.auth_register_send_code))
        }
    }
}

@Preview(
    device = Devices.PHONE,
)
@Composable
private fun AuthRegisterScreenPreview() {
    MaterialTheme {
        AuthRegisterScreen(
            onSendCodeClick = {},
        )
    }
}
