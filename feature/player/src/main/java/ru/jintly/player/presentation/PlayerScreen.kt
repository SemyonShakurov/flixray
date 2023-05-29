package ru.jintly.player.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.jintly.core.designsystem.colors.Background
import ru.jintly.core.designsystem.colors.Primary
import ru.jintly.core.designsystem.colors.Secondary
import ru.jintly.feature.player.R
import ru.jintly.feature.player.R.drawable
import ru.jintly.player.presentation.data.Message
import ru.jintly.player.presentation.view.VideoPlayer

@Composable
internal fun PlayerRoute(
    modifier: Modifier = Modifier,
    viewModel: PlayerViewModel = hiltViewModel(),
) {
    val messages by viewModel.messagesFlow.collectAsStateWithLifecycle(initialValue = emptyList())
    PlayerScreen(
        messages = messages,
        modifier = modifier,
        onSendClick = viewModel::onSendClick,
    )
}

@Composable
internal fun PlayerScreen(
    messages: List<Message>,
    modifier: Modifier = Modifier,
    onSendClick: (String) -> Unit,
) {
    var isVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .animateContentSize()
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f, fill = true)
                .background(Color.Black),
        ) {
            VideoPlayer(
                modifier = Modifier
                    .fillMaxSize(),
            )
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(44.dp)
                    .background(Color.Black),
            ) {
                IconButton(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .size(32.dp),
                    onClick = { isVisible = !isVisible },
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(id = drawable.ic_chat),
                        contentDescription = null,
                        tint = Color.White,
                    )
                }
            }
        }
        if (isVisible) {
            Chat(
                messages = messages,
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxWidth()
                    .weight(2f)
                    .background(Background),
                onSendClick = onSendClick,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Chat(
    messages: List<Message>,
    modifier: Modifier = Modifier,
    onSendClick: (String) -> Unit,
) {
    var input by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Чат комнаты",
            textAlign = TextAlign.Center,
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            reverseLayout = true,
        ) {
            items(messages) { message ->
                MessageItem(message)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Secondary,
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 10.dp,
            ),
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(4.dp),
                placeholder = {
                    Text(text = "Сообщение...", color = Color.White)
                },
                value = input,
                onValueChange = { input = it },
                shape = RoundedCornerShape(24.dp),
                textStyle = LocalTextStyle.current.copy(
                    color = Color.White,
                ),
                trailingIcon = {
                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = {
                            val mess = input
                            input = ""
                            if (mess.isNotBlank()) {
                                onSendClick(mess)
                            }
                        },
                    ) {
                        Icon(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = R.drawable.ic_send),
                            tint = Primary,
                            contentDescription = null,
                        )
                    }
                },
            )
        }
    }
}

@Composable
private fun MessageItem(
    message: Message,
) {
    if (!message.isEnter) {
        val isOut = message.isOut
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = if (!isOut) Alignment.End else Alignment.Start,
        ) {
            Box(
                modifier = Modifier
                    .background(
                        if (isOut) Secondary else Primary,
                        shape = if (!isOut) UserMessageShape else OutMessageShape,
                    )
                    .padding(12.dp, 8.dp),
            ) {
                Column(horizontalAlignment = if (!isOut) Alignment.End else Alignment.Start) {
                    if (isOut) {
                        Text(
                            text = message.profileName,
                            color = Color(0xFFCC8899),
                            fontSize = 12.sp,
                        )
                    }
                    Text(text = message.text, color = Color.White)
                }
            }
        }
    } else {
        Text(
            modifier = Modifier.padding(12.dp, 8.dp),
            text = if (message.isExit) "${message.profileName} вышел из комнаты" else "${message.profileName} присоединился!",
            color = Color.White,
        )
    }
}

private val UserMessageShape = RoundedCornerShape(12.dp, 0.dp, 12.dp, 12.dp)
private val OutMessageShape = RoundedCornerShape(0.dp, 12.dp, 12.dp, 12.dp)

private val MESSAGES = listOf(
    Message("Hello from other user!", "qwerty", true),
    Message("Hello from main user!", "zxcvbn", false),
    Message("", "user 1", isOut = false, true),
    Message("Hello from main user!", "zxcvbn", false),
    Message("", "user 1", isOut = false, true, true),
)
