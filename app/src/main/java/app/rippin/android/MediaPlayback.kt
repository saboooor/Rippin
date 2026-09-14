package app.rippin.android

import android.content.ComponentName
import android.graphics.Bitmap
import android.media.MediaMetadata
import android.media.session.MediaController
import android.media.session.MediaSessionManager
import android.media.session.PlaybackState
import android.service.notification.NotificationListenerService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal data class NowPlaying(
    val title: String = "No media playing",
    val artist: String = "Start playback in another app",
    val albumArt: Bitmap? = null,
    val positionMs: Long = 0L,
    val durationMs: Long = 0L,
    val isPlaying: Boolean = false,
    val controller: MediaController? = null,
)

internal object MediaPlayback {
    private val _nowPlaying = MutableStateFlow(NowPlaying())
    val nowPlaying: StateFlow<NowPlaying> = _nowPlaying

    private var activeController: MediaController? = null
    private var callback: MediaController.Callback? = null

    fun update(controller: MediaController?) {
        if (activeController?.sessionToken != controller?.sessionToken) {
            callback?.let { activeController?.unregisterCallback(it) }
            activeController = controller
            callback = controller?.let {
                object : MediaController.Callback() {
                    override fun onMetadataChanged(metadata: MediaMetadata?) = refresh()
                    override fun onPlaybackStateChanged(state: PlaybackState?) = refresh()
                }.also { callback -> it.registerCallback(callback) }
            }
        }
        refresh()
    }

    fun clear() {
        callback?.let { activeController?.unregisterCallback(it) }
        callback = null
        activeController = null
        _nowPlaying.value = NowPlaying()
    }

    internal fun refresh() {
        val controller = activeController ?: run { _nowPlaying.value = NowPlaying(); return }
        val metadata = controller.metadata
        val state = controller.playbackState
        val duration = metadata?.getLong(MediaMetadata.METADATA_KEY_DURATION)?.coerceAtLeast(0L) ?: 0L
        val position = state?.position?.coerceIn(0L, duration.takeIf { it > 0 } ?: Long.MAX_VALUE) ?: 0L
        _nowPlaying.value = NowPlaying(
            title = metadata?.getString(MediaMetadata.METADATA_KEY_TITLE)?.takeIf { it.isNotBlank() } ?: "Unknown track",
            artist = metadata?.getString(MediaMetadata.METADATA_KEY_ARTIST)?.takeIf { it.isNotBlank() } ?: "Unknown artist",
            albumArt = metadata?.getBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART) ?: metadata?.getBitmap(MediaMetadata.METADATA_KEY_ART),
            positionMs = position,
            durationMs = duration,
            isPlaying = state?.state == PlaybackState.STATE_PLAYING,
            controller = controller,
        )
    }
}

internal class MediaAccessService : NotificationListenerService() {
    private lateinit var sessions: MediaSessionManager
    private val listener = MediaSessionManager.OnActiveSessionsChangedListener { controllers ->
        MediaPlayback.update(selectController(controllers))
    }

    override fun onCreate() {
        super.onCreate()
        sessions = getSystemService(MediaSessionManager::class.java)
        val component = ComponentName(this, MediaAccessService::class.java)
        sessions.addOnActiveSessionsChangedListener(listener, component)
        MediaPlayback.update(selectController(sessions.getActiveSessions(component)))
    }

    override fun onDestroy() {
        sessions.removeOnActiveSessionsChangedListener(listener)
        MediaPlayback.clear()
        super.onDestroy()
    }

    private fun selectController(controllers: List<MediaController>?): MediaController? =
        controllers?.firstOrNull { controller ->
            controller.playbackState?.state == PlaybackState.STATE_PLAYING || controller.metadata != null
        }
}
