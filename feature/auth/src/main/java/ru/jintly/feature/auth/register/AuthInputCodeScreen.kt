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
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.jintly.core.designsystem.colors.Primary
import ru.jintly.feature.auth.R.string

@Composable
internal fun AuthInputCodeRoute(
    onConfirmCodeSuccess: () -> Unit,
    viewModel: AuthInputCodeViewModel = hiltViewModel(),
) {
    AuthInputCodeScreen(
        onConfirmCodeClick = { code ->
            viewModel.onConfirmCodeClick(
                code,
                onConfirmCodeSuccess = onConfirmCodeSuccess,
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AuthInputCodeScreen(
    modifier: Modifier = Modifier,
    onConfirmCodeClick: (Int) -> Unit,
) {
    var code by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .padding(horizontal = 28.dp)
            .fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.height(200.dp))
        Text(
            text = stringResource(id = string.auth_input_code_message),
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(20.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = code,
            onValueChange = { code = it },
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color.Transparent,
            ),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                letterSpacing = 12.sp,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "* * * *",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    letterSpacing = 12.sp,
                )
            },
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            onClick = { onConfirmCodeClick(code.toIntOrNull() ?: 0) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
            ),
        ) {
            Text(
                text = stringResource(id = string.auth_input_code_confirm),
                fontSize = 16.sp,
            )
        }
    }
}
