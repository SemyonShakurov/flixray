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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
            contentPadding = PaddingValues(start = 24.dp, end = 24.dp, top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(sessions) { session ->
                SessionRow(session = session, onPublicSessionClick)
            }
        }
    }
}

@Composable
private fun SessionRow(session: PublicSessionData, onPublicSessionClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPublicSessionClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Secondary),
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
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = session.title,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row {
                LinearProgressIndicator(
                    modifier = Modifier
                        .width(200.dp)
                        .padding(top = 8.dp),
                    progress = 0.7f,
                    color = Primary,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "еще 53 мин",
                    color = Color.White,
                    fontSize = 12.sp,
                )
            }
        }
    }
}
