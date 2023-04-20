package ru.jintly.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.jintly.core.designsystem.theme.JintlyTheme

@Composable
internal fun AuthMainRoute(
    onEnterClick: () -> Unit,
    onRegisterClick: () -> Unit,
) {
    AuthMainScreen(
        onEnterClick = onEnterClick,
        onRegisterClick = onRegisterClick,
    )
}

@Composable
internal fun AuthMainScreen(
    modifier: Modifier = Modifier,
    onEnterClick: () -> Unit,
    onRegisterClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = onEnterClick) {
            Text(text = stringResource(id = R.string.auth_enter))
        }
        Button(onClick = onRegisterClick) {
            Text(text = stringResource(id = R.string.auth_register))
        }
        Spacer(modifier = Modifier.height(184.dp))
    }
}

@Preview(
    device = Devices.PHONE,
)
@Composable
fun AuthMainScreenPreview() {
    JintlyTheme {
        AuthMainScreen(
            onEnterClick = {},
            onRegisterClick = {},
        )
    }
}
