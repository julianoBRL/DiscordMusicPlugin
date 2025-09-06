package io.github.julianobrl.musicplugin.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import lombok.Getter;
import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

    private static PlayerManager INSTANCE;
    private Map<Long, GuildMusicManager> guildsMusicManagerMap = new HashMap<>();

    @Getter
    private AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();

    public PlayerManager() {
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
        AudioSourceManagers.registerRemoteSources(audioPlayerManager);

    }

    public GuildMusicManager getGuildMusicManager(Guild guild){
        return guildsMusicManagerMap.computeIfAbsent(guild.getIdLong(), (guildId)->{
            GuildMusicManager guildMusicManager = new GuildMusicManager(audioPlayerManager);
            guild.getAudioManager().setSendingHandler(guildMusicManager.getAudioFowarder());
            return guildMusicManager;
        });
    }

    public static PlayerManager getInstance(){
        if (INSTANCE == null) {
            INSTANCE = new PlayerManager();
        }
        return INSTANCE;
    }

    public void play(Guild guild, String trackURL, AudioLoadResultHandler resultHandler){
        GuildMusicManager guildMusicManager = getGuildMusicManager(guild);
        audioPlayerManager.loadItemOrdered(guildMusicManager, trackURL, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                guildMusicManager.getTrackScheduler().queue(audioTrack);
                resultHandler.trackLoaded(audioTrack);
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                resultHandler.playlistLoaded(audioPlaylist);
            }

            @Override
            public void noMatches() {
                resultHandler.noMatches();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                resultHandler.loadFailed(e);
            }
        });
    }

}
