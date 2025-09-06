package io.github.julianobrl.musicplugin.music;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class GuildMusicManager {

    private TrackScheduler trackScheduler;
    private AudioFowarder audioFowarder;
    private AudioPlayer player;

    public GuildMusicManager(AudioPlayerManager manager) {
        player = manager.createPlayer();

        trackScheduler = new TrackScheduler(player);
        player.addListener(trackScheduler);

        audioFowarder = new AudioFowarder(player);
    }

    public TrackScheduler getTrackScheduler() {
        return trackScheduler;
    }

    public AudioFowarder getAudioFowarder() {
        return audioFowarder;
    }

    public AudioPlayer getPlayer() {
        return player;
    }
}
