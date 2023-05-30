package ru.jintly.player.presentation.view

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView
import ru.jintly.player.presentation.Event
import ru.jintly.player.presentation.Type.ENTER
import ru.jintly.player.presentation.Type.PAUSE
import ru.jintly.player.presentation.Type.PLAY

@OptIn(UnstableApi::class)
@Composable
internal fun VideoPlayer(
    modifier: Modifier = Modifier,
    isPrivate: Boolean,
    isAdmin: Boolean,
    event: Event,
    onPlay: (Long) -> Unit,
    onPause: (Long) -> Unit,
) {
    val context = LocalContext.current

    val mediaItem = MediaItem.Builder()
        .setUri(
            if (isPrivate) {
                "https://app.flixray.ru/srv/streaming/minecraft.m3u8"
            } else {
                "https://cph-p2p-msl.akamaized.net/hls/live/2000341/test/master.m3u8"
            },
        )
        .setLiveConfiguration(MediaItem.LiveConfiguration.Builder().build())
        .build()

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(DefaultMediaSourceFactory(context).setLiveTargetOffsetMs(10000))
            .build()
            .apply {
                this.setMediaItem(mediaItem)
                this.playWhenReady = true
                this.prepare()
            }
    }

    when {
        event.type == ENTER -> exoPlayer.seekTo(event.timing)
        event.type == PLAY -> {
            exoPlayer.seekTo(event.timing)
            if (!exoPlayer.isPlaying) {
                exoPlayer.play()
            }
        }
        event.type == PAUSE -> {
            exoPlayer.pause()
            exoPlayer.seekTo(event.timing)
        }
    }

    if (isPrivate && isAdmin) {
        exoPlayer.addListener(object : Player.Listener {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                if (playWhenReady && playbackState == Player.STATE_READY) {
                    if (isAdmin) {
                        onPlay(exoPlayer.currentPosition)
                    }
                } else if (!playWhenReady) {
                    onPause(exoPlayer.currentPosition)
                }
            }
        })
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
    ) {
        DisposableEffect(
            AndroidView(
                modifier = modifier.fillMaxSize(),
                factory = {
                    PlayerView(context).apply {
                        player = exoPlayer
                        layoutParams = FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                        )
                        useController = isAdmin || !isPrivate
                    }
                },
            ),
        ) {
            onDispose {
                exoPlayer.release()
            }
        }
        Row(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp),
        ) {
            Canvas(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(8.dp),
                onDraw = {
                    drawCircle(color = Color.Red)
                },
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "live", color = Color.White)
        }
    }
}
