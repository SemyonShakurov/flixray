package ru.jintly.feature.privatesessions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.jintly.core.designsystem.R.drawable
import ru.jintly.core.designsystem.colors.Background
import ru.jintly.core.designsystem.colors.Primary
import ru.jintly.core.designsystem.colors.Secondary
import ru.jintly.feature.privatesessions.data.ProfileInfo

@Composable
internal fun InvitePeopleRoute(
    onContinueClick: (String, String) -> Unit,
    viewModel: InvitePeopleViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    InvitePeopleScreen(
        uiState,
        viewModel::onAddProfileClick,
        onContinueClick = { viewModel.onContinueClick(onContinueClick) },
        viewModel::onSearchClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun InvitePeopleScreen(
    uiState: InvitePeopleUiState,
    onProfileClick: (ProfileInfo) -> Unit,
    onContinueClick: () -> Unit,
    onSearchClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState is InvitePeopleUiState.Success || uiState is InvitePeopleUiState.NSuccess) {
        var input by remember { mutableStateOf("") }
        val profiles = when (uiState) {
            is InvitePeopleUiState.Success -> uiState.profiles
            is InvitePeopleUiState.NSuccess -> uiState.profiles
            else -> emptyList()
        }

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(264.dp))
            Text(
                text = "С кем будете смотреть?",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextField(
                    modifier = Modifier.weight(1f),
                    value = input,
                    onValueChange = { input = it },
                    shape = RoundedCornerShape(16.dp),
                    singleLine = true,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                    ),
                    placeholder = { Text(text = "Введите имя пользователя") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    modifier = Modifier
                        .height(52.dp),
                    onClick = {
                        onSearchClick(input)
                        input = ""
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Primary,
                    ),
                ) {
                    Text(
                        text = "Поиск",
                        fontSize = 16.sp,
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(profiles) { profile ->
                    SearchProfileRow(profile = profile, onProfileClick)
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                onClick = onContinueClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                ),
            ) {
                Text(
                    text = "Продолжить",
                    fontSize = 16.sp,
                )
            }
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

@Composable
private fun SearchProfileRow(
    profile: ProfileInfo,
    onProfileClick: (ProfileInfo) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(Secondary)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.width(4.dp))
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(Background),
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(),
                painter = painterResource(id = drawable.ic_profile_placeholder),
                contentDescription = null,
                tint = Secondary,
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = profile.name,
            color = Color.White,
            fontSize = 20.sp,
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { onProfileClick(profile) },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (profile.isAdded) Primary else Color(0xFF535353),
            ),
        ) {
            if (profile.isAdded) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    painter = painterResource(id = R.drawable.ic_added),
                    contentDescription = null,
                    tint = Color.White,
                )
            } else {
                Text(
                    text = "Добавить",
                    fontSize = 12.sp,
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
    }
}
