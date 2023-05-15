package ru.jintly.publicsessions.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.jintly.core.designsystem.colors.Background
import ru.jintly.core.designsystem.colors.Primary
import ru.jintly.core.designsystem.colors.Secondary
import ru.jintly.feature.publicsessions.R
import ru.jintly.publicsessions.model.PublicSessionData

@Composable
internal fun PublicSessionsRoute(
    viewModel: PublicSessionsViewModel = hiltViewModel(),
    onPublicSessionClick: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PublicSessionsScreen(
        onPublicSessionClick = onPublicSessionClick,
        uiState = uiState,
    )
}

@Composable
internal fun PublicSessionsScreen(
    modifier: Modifier = Modifier,
    uiState: PublicSessionsUiState,
    onPublicSessionClick: () -> Unit,
) {
    if (uiState is PublicSessionsUiState.Success) {
        val sessions = uiState.sessions
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            item {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Приватные сессии",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            }
            item {
                SessionRow(session = PublicSessionData(title = "Смотрим фильм", image = null, uri = ""), onPublicSessionClick, "еще 1ч 29 мин", 0.2f)
            }
            item {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Публичные сессии",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            }
            item {
                SessionRow(session = sessions[0], onPublicSessionClick, "еще 8 мин", 0.2f)
            }
            item {
                SessionRow(session = sessions[1], onPublicSessionClick, "еще 11 мин", 0.6f)
            }
            item {
                SessionRow(session = sessions[2], onPublicSessionClick, "еще 15 мин", 0.1f)
            }
        }
    }
}

@Composable
private fun SessionRow(
    session: PublicSessionData,
    onPublicSessionClick: () -> Unit,
    text: String,
    progress: Float,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPublicSessionClick() }
            .clip(CircleShape)
            .background(Secondary)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(68.dp)
                .clip(CircleShape)
                .background(Background),
        ) {
            Icon(
                modifier = Modifier.align(Alignment.Center),
                painter = painterResource(id = R.drawable.ic_play_placeholder),
                contentDescription = null,
                tint = Primary,
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = session.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
            Row {
                LinearProgressIndicator(
                    modifier = Modifier
                        .width(100.dp)
                        .padding(top = 8.dp),
                    progress = progress,
                    color = Primary,
                    trackColor = Background,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = text,
                    color = Color.White,
                    fontSize = 12.sp,
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "0",
            color = Primary,
            fontSize = 20.sp,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Icon(
            modifier = Modifier.size(28.dp),
            painter = painterResource(id = R.drawable.ic_people),
            contentDescription = null,
            tint = Primary,
        )
        Spacer(modifier = Modifier.width(24.dp))
    }
}
