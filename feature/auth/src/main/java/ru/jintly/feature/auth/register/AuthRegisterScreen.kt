package ru.jintly.feature.auth.register

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.jintly.core.designsystem.colors.Primary
import ru.jintly.core.designsystem.theme.JintlyTheme
import ru.jintly.feature.auth.R

@Composable
internal fun AuthRegisterRoute(
    onSendCodeSuccess: (String) -> Unit,
    viewModel: AuthRegisterViewModel = hiltViewModel(),
) {
    AuthRegisterScreen(
        onSendCodeClick = { email ->
            viewModel.onSendCodeClick(
                email,
                onSendCodeSuccess = onSendCodeSuccess,
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AuthRegisterScreen(
    modifier: Modifier = Modifier,
    onSendCodeClick: (String) -> Unit,
) {
    var email by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(horizontal = 28.dp)
            .fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.height(200.dp))
        Text(
            text = stringResource(id = R.string.auth_register_enter_email),
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(20.dp))
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
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            onClick = { onSendCodeClick(email) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
            ),
        ) {
            Text(
                text = stringResource(id = R.string.auth_register_send_code),
                fontSize = 16.sp,
            )
        }
    }
}

@Preview(
    device = Devices.PHONE,
)
@Composable
private fun AuthRegisterScreenPreview() {
    JintlyTheme {
        AuthRegisterScreen(
            onSendCodeClick = {},
        )
    }
}
