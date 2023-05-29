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
import ru.jintly.player.presentation.data.Message
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor(
    @ApplicationContext context: Context,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val isPrivate: Boolean = savedStateHandle["is_private"] ?: false
    val isAdmin: Boolean = savedStateHandle["is_admin"] ?: false

    private val messages: MutableList<Message> = mutableListOf()
    private var webSocket: WebSocket? = null
    private val dataStore = context.dataStore
    private var profileName: String? = null
    private var token: String? = null

    val messagesFlow = MutableStateFlow<List<Message>>(emptyList())

    val listener = object : WebSocketListener() {

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
//        webSocket?.close(1000, "exit")
        super.onCleared()
    }

    init {
        dataStore.data
            .onEach { pref ->
                token = pref[TOKEN_KEY] ?: ""
                profileName = pref[NAME_KEY] ?: ""
                val client = OkHttpClient()
                val url = "ws://app.flixray.ru/srv/chat/ws/private2?token=$token"
                val request: Request =
                    Request.Builder().url(url).build()
                webSocket = client.newWebSocket(request, listener)
            }
            .launchIn(viewModelScope)
    }

    fun onSendClick(message: String) {
        messages.add(Message(message, "user 1", false))
        viewModelScope.launch {
            messagesFlow.emit(messages.reversed())
        }
        webSocket?.send(
            "{\n" +
                "    \"author\": \"${profileName ?: "user"}\",\n" +
                "    \"room_name\": \"private2\",\n" +
                "    \"message\": \"$message\"\n" +
                "}",
        )
    }
}
