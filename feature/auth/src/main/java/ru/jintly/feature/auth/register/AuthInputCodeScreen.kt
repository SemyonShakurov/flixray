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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.jintly.feature.auth.R.string

@Composable
internal fun AuthInputCodeRoute(
    onConfirmCodeSuccess: () -> Unit,
    viewModel: AuthInputCodeViewModel = hiltViewModel(),
) {
    AuthInputCodeScreen(
        onConfirmCodeClick = {
            viewModel.onConfirmCodeClick(
                onConfirmCodeSuccess = onConfirmCodeSuccess,
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AuthInputCodeScreen(
    modifier: Modifier = Modifier,
    onConfirmCodeClick: () -> Unit,
) {
    var code by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(horizontal = 28.dp, vertical = 144.dp)
            .fillMaxSize(),
    ) {
        Text(text = stringResource(id = string.auth_input_code_message))
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = code,
            onValueChange = { code = it },
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onConfirmCodeClick) {
            Text(text = stringResource(id = string.auth_input_code_confirm))
        }
    }
}
