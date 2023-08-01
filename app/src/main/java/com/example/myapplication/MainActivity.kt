package com.example.myapplication

import android.app.Activity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.media3.common.DeviceInfo
import androidx.media3.common.Player
import androidx.media3.common.SimpleBasePlayer
import androidx.media3.session.MediaSession
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.ListenableFuture

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val player = MediaSessionPlayer()
        MediaSession.Builder(this, player)
            .setId("Playback")
            .build()
    }
}

class MediaSessionPlayer : SimpleBasePlayer(Looper.getMainLooper()) {

    private var state = State.Builder()
        .setAvailableCommands(
            Player.Commands.Builder()
                .addAll(
                    Player.COMMAND_PLAY_PAUSE,
                    Player.COMMAND_STOP,
                    Player.COMMAND_GET_DEVICE_VOLUME,
//                    Player.COMMAND_SET_DEVICE_VOLUME,
//                    Player.COMMAND_ADJUST_DEVICE_VOLUME,
                    Player.COMMAND_SET_DEVICE_VOLUME_WITH_FLAGS,
                    Player.COMMAND_ADJUST_DEVICE_VOLUME_WITH_FLAGS,
                )
                .build()
        )
        .setDeviceInfo(
            DeviceInfo.Builder(DeviceInfo.PLAYBACK_TYPE_REMOTE)
                .setMinVolume(0)
                .setMaxVolume(20)
                .build()
        )
        .setPlaylist(listOf(MediaItemData.Builder("placeholder").build()))
        .setCurrentMediaItemIndex(0)
        .setPlaybackState(Player.STATE_READY)
        .setPlayWhenReady(true, PLAY_WHEN_READY_CHANGE_REASON_USER_REQUEST)
        .setDeviceVolume(1)
        .setIsDeviceMuted(false)
        .build()

    override fun getState(): State {
        return this.state
    }

    override fun handleSetDeviceVolume(deviceVolume: Int, flags: Int): ListenableFuture<*> {
        Log.d(LOG_TAG, "handleSetDeviceVolume: deviceVolume=$deviceVolume, flags=$flags")
        return Futures.immediateVoidFuture()
    }

    override fun handleIncreaseDeviceVolume(flags: Int): ListenableFuture<*> {
        Log.d(LOG_TAG, "handleIncreaseDeviceVolume: flags=$flags")
        return Futures.immediateVoidFuture()
    }

    override fun handleDecreaseDeviceVolume(flags: Int): ListenableFuture<*> {
        Log.d(LOG_TAG, "handleDecreaseDeviceVolume: flags=$flags")
        return Futures.immediateVoidFuture()
    }

    override fun handleSetDeviceMuted(muted: Boolean, flags: Int): ListenableFuture<*> {
        Log.d(LOG_TAG, "handleSetDeviceMuted: muted=$muted, flags=$flags")
        return Futures.immediateVoidFuture()
    }

    companion object {
        private const val LOG_TAG = "MediaSessionPlayer"
    }
}
