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
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultLivePlaybackSpeedControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
internal fun VideoPlayer(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    val mediaItem = MediaItem.Builder()
        .setUri("https://cph-p2p-msl.akamaized.net/hls/live/2000341/test/master.m3u8")
        .setLiveConfiguration(MediaItem.LiveConfiguration.Builder().build())
        .build()

    val exoPlayer = remember {
        ExoPlayer.Builder(context)
            .setMediaSourceFactory(DefaultMediaSourceFactory(context).setLiveTargetOffsetMs(5000))
            .setLivePlaybackSpeedControl(DefaultLivePlaybackSpeedControl.Builder().build())
            .build()
            .apply {
                this.setMediaItem(mediaItem)
                this.playWhenReady = true
                this.prepare()
            }
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
