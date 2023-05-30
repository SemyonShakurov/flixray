package ru.jintly.player.presentation

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import ru.jintly.core.data.utils.NAME_KEY
import ru.jintly.core.data.utils.TOKEN_KEY
import ru.jintly.core.data.utils.dataStore
import ru.jintly.player.presentation.Type.ENTER
import ru.jintly.player.presentation.Type.PAUSE
import ru.jintly.player.presentation.Type.PLAY
import ru.jintly.player.presentation.data.Message
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    @ApplicationContext context: Context,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val isPrivate: Boolean = savedStateHandle["is_private"] ?: false
    val isAdmin: Boolean = savedStateHandle["is_admin"] ?: false
    val roomName: String = savedStateHandle["room_name"] ?: ""

    private val messages: MutableList<Message> = mutableListOf()
    private var chatWebSocket: WebSocket? = null
    private var videoWebSocket: WebSocket? = null
    private val dataStore = context.dataStore
    private var profileName: String? = null
    private var token: String? = null

    val messagesFlow = MutableStateFlow<List<Message>>(emptyList())

    val eventFlow = MutableStateFlow(Event(Type.ENTER, 0))

    private val videoListener = object : WebSocketListener() {

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            val jObj = JSONObject(text)
            when {
                jObj.getString("type") == "ENTER" -> {
                    val timing = jObj.getLong("timing")
                    val status = jObj.getString("status")
                    if (timing != 0L) {
                        viewModelScope.launch {
                            eventFlow.emit(Event(if (status == "PAUSE") PAUSE else ENTER, timing))
                        }
                    }
                }

                jObj.getString("type") == "PLAY" -> {
                    val timing = jObj.getLong("timing")
                    viewModelScope.launch {
                        eventFlow.emit(Event(PLAY, timing))
                    }
                }

                jObj.getString("type") == "PAUSE" -> {
                    val timing = jObj.getLong("timing")
                    viewModelScope.launch {
                        eventFlow.emit(Event(PAUSE, timing))
                    }
                }
            }
        }
    }

    private val chatListener = object : WebSocketListener() {

        override fun onMessage(webSocket: WebSocket, text: String) {
            super.onMessage(webSocket, text)
            val jObj = JSONObject(text)
            val author = jObj.getString("author")
            val roomName = jObj.getString("room_name")
            val message = jObj.getString("message")
            messages.add(
                Message(
                    text = message,
                    profileName = author,
                    isOut = profileName != author,
                ),
            )
            viewModelScope.launch {
                messagesFlow.emit(messages.reversed())
            }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            super.onFailure(webSocket, t, response)
        }

        override fun onOpen(webSocket: WebSocket, response: Response) {
            super.onOpen(webSocket, response)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            super.onClosed(webSocket, code, reason)
        }
    }

    override fun onCleared() {
        if (isAdmin) {
            videoWebSocket?.send(
                "{\n" +
                    "    \"author\": \"${profileName ?: "user"}\",\n" +
                    "    \"type\": \"PAUSE\",\n" +
                    "    \"timing\": -1\n" +
                    "}",
            )
        }
        videoWebSocket?.close(1000, "exit")
        chatWebSocket?.close(1000, "exit")
        super.onCleared()
    }

    init {
        dataStore.data
            .onEach { pref ->
                token = pref[TOKEN_KEY] ?: ""
                profileName = pref[NAME_KEY] ?: ""
                val client = OkHttpClient()
                val videoUrl = "ws://app.flixray.ru/srv/watch/ws/$roomName?token=$token"
                val videoRequest: Request =
                    Request.Builder().url(videoUrl).build()
                videoWebSocket = client.newWebSocket(videoRequest, videoListener)

                val chatUrl = "ws://app.flixray.ru/srv/chat/ws/$roomName?token=$token"
                val chatRequest: Request =
                    Request.Builder().url(chatUrl).build()
                chatWebSocket = client.newWebSocket(chatRequest, chatListener)

                videoWebSocket?.send(
                    "{\n" +
                        "    \"author\": \"$profileName\",\n" +
                        "    \"type\": \"ENTER\"\n" +
                        "}",
                )
            }
            .launchIn(viewModelScope)
    }

    fun onSendClick(message: String) {
        messages.add(Message(message, "user 1", false))
        viewModelScope.launch {
            messagesFlow.emit(messages.reversed())
        }
        chatWebSocket?.send(
            "{\n" +
                "    \"author\": \"${profileName ?: "user"}\",\n" +
                "    \"room_name\": \"$roomName\",\n" +
                "    \"message\": \"$message\"\n" +
                "}",
        )
    }

    fun onPlay(timing: Long) {
        videoWebSocket?.send(
            "{\n" +
                "    \"author\": \"${profileName ?: "user"}\",\n" +
                "    \"type\": \"PLAY\",\n" +
                "    \"timing\": $timing\n" +
                "}",
        )
    }

    fun onPause(timing: Long) {
        videoWebSocket?.send(
            "{\n" +
                "    \"author\": \"${profileName ?: "user"}\",\n" +
                "    \"type\": \"PAUSE\",\n" +
                "    \"timing\": $timing\n" +
                "}",
        )
    }
}

data class Event(
    val type: Type,
    val timing: Long,
)

enum class Type {
    ENTER, PLAY, PAUSE, EXIT
}
