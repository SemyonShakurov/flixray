package ru.jintly.feature.privatesessions

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.jintly.core.designsystem.colors.Primary

@Composable
internal fun InputRoomNameRoute(
    onContinueClick: (String) -> Unit,
    viewModel: InputRoomNameViewModel = hiltViewModel(),
) {
    InputRoomNameScreen(
        onContinueClick = { name ->
            viewModel.onContinueClick(name, onContinueClick)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InputRoomNameScreen(
    onContinueClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var roomName by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(200.dp))
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = "Выберите название для комнаты",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = roomName,
            onValueChange = { roomName = it },
            shape = RoundedCornerShape(16.dp),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = Color.Transparent,
            ),
            placeholder = {
                Text(
                    text = "Введите название комнаты",
                )
            },
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            onClick = { onContinueClick(roomName) },
            colors = ButtonDefaults.buttonColors(
                containerColor = Primary,
            ),
        ) {
            Text(
                text = "Продолжить",
                fontSize = 16.sp,
            )
        }
    }
}
