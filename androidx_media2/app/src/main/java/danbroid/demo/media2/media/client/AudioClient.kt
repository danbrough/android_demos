package danbroid.demo.media2.media.client

import android.content.Context
import androidx.core.content.ContextCompat.getMainExecutor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.media2.common.MediaItem
import androidx.media2.common.MediaMetadata
import androidx.media2.common.SessionPlayer
import androidx.media2.customplayer.MediaPlayer
import androidx.media2.session.MediaBrowser
import androidx.media2.session.MediaController
import androidx.media2.session.MediaSessionManager
import androidx.media2.session.SessionCommandGroup
import com.google.common.util.concurrent.ListenableFuture
import danbroid.demo.media2.media.AudioService
import danbroid.demo.media2.media.buffState
import danbroid.demo.media2.media.playerState


class AudioClient(context: Context) {

  val context = context.applicationContext

  init {
    log.error("Created AudioClient")
  }

  private val _pauseEnabled = MutableLiveData<Boolean>()
  val pauseEnabled: LiveData<Boolean> = _pauseEnabled

  private val _connected = MutableLiveData<Boolean>(false)
  val connected: LiveData<Boolean> = _connected

  private val _hasNext = MutableLiveData<Boolean>(false)
  val hasNext: LiveData<Boolean> = _hasNext

  private val _hasPrevious = MutableLiveData<Boolean>(false)
  val hasPrevious: LiveData<Boolean> = _hasPrevious

  protected val controllerCallback = ControllerCallback()

  protected val mainExecutor = getMainExecutor(context)//Executors.newSingleThreadExecutor()

  val mediaController: MediaBrowser = let {

    val sessionManager = MediaSessionManager.getInstance(context)
    log.debug("got sessionManager: $sessionManager")
    val serviceToken = sessionManager.sessionServiceTokens.first {
      it.serviceName == AudioService::class.qualifiedName
    }

    MediaBrowser.Builder(context)
      .setControllerCallback(mainExecutor, controllerCallback)
      .setSessionToken(serviceToken)
      .build()
  }


  fun playUri(uri: String) {
    log.trace("playUri() $uri")

    mediaController.addPlaylistItem(Integer.MAX_VALUE, uri).then {
      log.debug("result: $it code: ${it.resultCode} item:${it.mediaItem}")
      if (mediaController.playerState != MediaPlayer.PLAYER_STATE_PLAYING) {
        log.debug("skipping to start of playlist")
        mediaController.skipToPlaylistItem(0).then {
          play()
        }
      }
    }

  }


  fun togglePause() {
    log.debug("togglePause() state: ${mediaController.playerState.playerState}")
    if (mediaController.playerState == MediaPlayer.PLAYER_STATE_PLAYING) {
      mediaController.pause()
    } else {
      mediaController.play()
    }
  }

  fun state() {
    log.info("buf: ${mediaController.bufferingState.buffState} state: ${mediaController.playerState.playerState}")
    //mediaController.adjustVolume(AudioManager.ADJUST_TOGGLE_MUTE, AudioManager.FLAG_PLAY_SOUND)
  }

  fun play() {
    log.trace("play() state:${mediaController.playerState.playerState} buffState:${mediaController.bufferingState.buffState}")
    if (mediaController.playerState != SessionPlayer.PLAYER_STATE_PLAYING || mediaController.bufferingState == SessionPlayer.BUFFERING_STATE_BUFFERING_AND_PLAYABLE) {
      log.trace("calling mediaController.play()")
      mediaController.play()
    }
  }

  fun test() {
    log.debug("test()")
    mediaController.playlistMetadata?.also {
      log.info("metadata: $it")
    }
    mediaController.playlist?.forEach {
      log.debug("playlistItem: $it")
    }
  }

  fun skipToNext() {
    mediaController.skipToNextPlaylistItem().then {
      play()
    }
  }

  fun skipToPrev() {
    mediaController.skipToPreviousPlaylistItem().then {
      play()
    }
  }

  private fun <T> ListenableFuture<T>.then(job: (T) -> Unit) =
    addListener({
      job.invoke(get())
    }, mainExecutor)

  protected inner class ControllerCallback : MediaBrowser.BrowserCallback() {

    override fun onPlaybackInfoChanged(
      controller: MediaController,
      info: MediaController.PlaybackInfo
    ) {
      log.debug("onPlaybackInfoChanged(): $info")
    }

    override fun onAllowedCommandsChanged(
      controller: MediaController,
      commands: SessionCommandGroup
    ) {
      log.debug("onAllowedCommandsChanged() $commands")
      commands.commands.forEach {
        log.debug("allowed command: $it")
      }
    }

    override fun onPlaybackCompleted(controller: MediaController) {
      log.debug("onPlaybackCompleted()")
    }

    override fun onPlaylistMetadataChanged(controller: MediaController, metadata: MediaMetadata?) {
      log.debug("onPlaylistMetadataChanged() $metadata")
    }

    override fun onPlaylistChanged(
      controller: MediaController,
      list: MutableList<MediaItem>?,
      metadata: MediaMetadata?
    ) {
      val state = controller.playerState
      log.debug("onPlaylistChanged() size:${list?.size} state:${state.playerState} prev:${controller.previousMediaItemIndex} next:${controller.nextMediaItemIndex} $metadata")
      _hasNext.value = controller.nextMediaItemIndex != -1
      _hasPrevious.value = controller.previousMediaItemIndex != -1
    }

    override fun onCurrentMediaItemChanged(controller: MediaController, item: MediaItem?) {
      log.debug("item: $item")
    }

    override fun onBufferingStateChanged(controller: MediaController, item: MediaItem, state: Int) {
      log.debug("onBufferingStateChanged() ${state.buffState}")
      if (state == SessionPlayer.BUFFERING_STATE_BUFFERING_AND_PLAYABLE) {
        play()
      }
    }

    override fun onPlayerStateChanged(controller: MediaController, state: Int) {
      log.debug("onPlayerStateChanged() ${state.playerState} prev:${controller.previousMediaItemIndex} next:${controller.nextMediaItemIndex}")
      _pauseEnabled.value = state == SessionPlayer.PLAYER_STATE_PLAYING

    }

    override fun onTracksChanged(
      controller: MediaController,
      tracks: MutableList<SessionPlayer.TrackInfo>
    ) {
      val state = controller.playerState
      log.debug("onTracksChanged() tracks:${tracks} state:${state.playerState} prev:${controller.previousMediaItemIndex} next:${controller.nextMediaItemIndex}")
      _hasNext.value = controller.nextMediaItemIndex != -1
      _hasPrevious.value = controller.previousMediaItemIndex != -1
    }

    override fun onConnected(controller: MediaController, allowedCommands: SessionCommandGroup) {
      log.debug("onConnected()")
      _connected.value = true
    }

    override fun onDisconnected(controller: MediaController) {
      log.debug("onDisconnected()")
      _connected.value = false
    }


  }
}

private val log = org.slf4j.LoggerFactory.getLogger(AudioClient::class.java)
