package ru.jintly.publicsessions.presentation

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
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
import ru.jintly.feature.publicsessions.R.drawable
import ru.jintly.publicsessions.model.PublicSessionData

@Composable
internal fun PublicSessionsRoute(
    viewModel: PublicSessionsViewModel = hiltViewModel(),
    onPublicSessionClick: (String) -> Unit = {},
    onPrivateSessionClick: (String, Boolean) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val state by viewModel.stateFlow.collectAsStateWithLifecycle(initialValue = State(emptyList(), 0))

    PublicSessionsScreen(
        onPublicSessionClick = onPublicSessionClick,
        onPrivateSessionClick = { name, admin ->
            viewModel.onSessionClick(name, admin, onPrivateSessionClick)
        },
        uiState = uiState,
        count = state.count,
        rooms = state.rooms,
    )
}

@Composable
internal fun PublicSessionsScreen(
    modifier: Modifier = Modifier,
    uiState: PublicSessionsUiState,
    onPublicSessionClick: (String) -> Unit,
    onPrivateSessionClick: (String, String) -> Unit,
    count: Int,
    rooms: List<Room>,
) {
    if (uiState is PublicSessionsUiState.Success) {
        val sessions = uiState.sessions
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            if (rooms.isNotEmpty()) {
                item {
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = "Приватные сессии",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                }
                items(rooms) { room ->
                    SessionRow(
                        session = PublicSessionData(
                            title = room.name,
                            image = null,
                        ),
                        { onPrivateSessionClick(room.name, room.adminName) },
                        painter = painterResource(id = drawable.movie_1),
                        count = room.userCount,
                        timing = room.timing,
                        duration = room.duration,
                    )
                }
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
                SessionRow(
                    session = sessions[0],
                    { onPublicSessionClick(sessions[0].title) },
                    "Tears of steel",
                    painter = painterResource(id = R.drawable.movie_4),
                    count = count,
                )
            }
            item {
                SessionRow(
                    session = sessions[1],
                    { onPublicSessionClick(sessions[1].title) },
                    "NASA",
                    painter = painterResource(id = R.drawable.movie_3),
                )
            }
            item {
                SessionRow(
                    session = sessions[2],
                    { onPublicSessionClick(sessions[2].title) },
                    "Parkour",
                    painter = painterResource(id = R.drawable.movie_2),
                )
            }
        }
    }
}

@Composable
private fun SessionRow(
    session: PublicSessionData,
    onPublicSessionClick: () -> Unit,
    subtitle: String? = null,
    painter: Painter? = null,
    count: Int = 0,
    duration: Int? = null,
    timing: Int? = null,
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
            if (painter != null) {
                Image(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxSize(),
                    painter = painter,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
            } else {
                Icon(
                    modifier = Modifier.align(Alignment.Center),
                    painter = painterResource(id = R.drawable.ic_play_placeholder),
                    contentDescription = null,
                    tint = Primary,
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = session.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Сейчас идет: $subtitle",
                    color = Color.White,
                    fontSize = 16.sp,
                )
            }
            if (timing != null && duration != null) {
                val progress = timing.toFloat() / duration
                val minutes = (duration - timing) / 1000 / 60 + 1
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
                        text = "еще $minutes мин",
                        color = Color.White,
                        fontSize = 12.sp,
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = count.toString(),
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
        Spacer(modifier = Modifier.width(16.dp))
    }
}
