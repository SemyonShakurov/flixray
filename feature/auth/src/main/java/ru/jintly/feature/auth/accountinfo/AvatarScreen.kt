package ru.jintly.feature.auth.accountinfo

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import ru.jintly.core.designsystem.colors.Background
import ru.jintly.core.designsystem.colors.Primary
import ru.jintly.core.designsystem.colors.Secondary

@Composable
internal fun AvatarScreenRoute(
    onComplete: () -> Unit,
    viewModel: AvatarScreenViewModel = hiltViewModel(),
) {
    AvatarScreen(
        onComplete,
    )
}

@Composable
internal fun AvatarScreen(
    onComplete: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedImageUrl by remember {
        mutableStateOf<Uri?>(null)
    }
    var isChosen by remember {
        mutableStateOf(false)
    }

    val singlePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                selectedImageUrl = uri
                isChosen = true
            }
        },
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(Background),
        ) {
            if (selectedImageUrl == null) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize(),
                    painter = painterResource(id = ru.jintly.core.designsystem.R.drawable.ic_profile_placeholder),
                    contentDescription = null,
                    tint = Secondary,
                )
            } else {
                AsyncImage(
                    model = selectedImageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize(),
                    placeholder = painterResource(id = ru.jintly.core.designsystem.R.drawable.ic_profile_placeholder),
                    contentScale = ContentScale.Crop,
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Выберите аватар для вашего профиля",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (isChosen) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                onClick = onComplete,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary,
                ),
            ) {
                Text(
                    text = "Подтвердить",
                    fontSize = 16.sp,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            onClick = {
                singlePhotoPicker.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (!isChosen) Primary else Secondary,
            ),
        ) {
            Text(
                text = if (!isChosen) "Выбрать аватар" else "Сменить аватар",
                fontSize = 16.sp,
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            onClick = onComplete,
            colors = ButtonDefaults.buttonColors(
                containerColor = Secondary,
            ),
        ) {
            Text(
                text = "Позже",
                fontSize = 16.sp,
            )
        }
        Spacer(modifier = Modifier.height(60.dp))
    }
}
