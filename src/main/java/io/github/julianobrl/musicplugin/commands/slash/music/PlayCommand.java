package io.github.julianobrl.musicplugin.commands.slash.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import io.github.julianobrl.musicplugin.music.PlayerManager;
import io.github.julianobrl.musicplugin.utils.TimeFormater;
import io.github.julianobrl.discordbots.framework.annotations.commands.SlashCommand;
import io.github.julianobrl.discordbots.framework.annotations.parameters.SlashParameter;
import io.github.julianobrl.discordbots.framework.commands.IExecuteCommands;
import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.List;

@SlashCommand(name = "play", description = "Play/Queue a video/music url.")
public class PlayCommand implements IExecuteCommands {

    @SlashParameter(name = "url", description = "url", optionType = OptionType.STRING)
    private String url;

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        Member sender = event.getMember();
        GuildVoiceState senderVoiceState = sender.getVoiceState();
        if (!senderVoiceState.inAudioChannel()){
            event.reply("You need to be in a voice channel.").queue();
            return;
        }

        Member self = event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState = self.getVoiceState();
        if (!selfVoiceState.inAudioChannel()){
            event.getGuild().getAudioManager().openAudioConnection(senderVoiceState.getChannel());
        }else{
            if(selfVoiceState.getChannel() != senderVoiceState.getChannel()){
                event.reply("We need to be in the same voice channel.").queue();
                return;
            }
        }

        PlayerManager.getInstance().play(event.getGuild(), url, new AudioLoadResultHandler() {

            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                AudioTrackInfo trackInfo = audioTrack.getInfo();
                MessageEmbed messageEmbed = new MessageEmbed(
                        trackInfo.uri,
                        trackInfo.title,
                        trackInfo.author,
                        EmbedType.RICH,
                        OffsetDateTime.now(),
                        Color.PINK.getRGB(),
                        new MessageEmbed.Thumbnail(trackInfo.artworkUrl, null, 100, 100),
                        null,
                        new MessageEmbed.AuthorInfo(
                                self.getNickname(),
                                null,
                                self.getAvatarUrl(),
                                null
                        ),
                        null,
                        new MessageEmbed.Footer(trackInfo.uri, null, null),
                        null,
                        List.of(
                            new MessageEmbed.Field(
                                "Duration",
                                TimeFormater.formatDurationInMillis(trackInfo.length),
                                true
                            ),
                            new MessageEmbed.Field(
                                "Stream",
                                (trackInfo.isStream)? ":white_check_mark:": ":x:",
                                true
                            ))
                );
                event.replyEmbeds(messageEmbed).queue();
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
            }

            @Override
            public void noMatches() {
                event.reply("Failed to load the url, is it a valid url?").queue();
            }

            @Override
            public void loadFailed(FriendlyException e) {
                event.reply("Failed to load the url, is it a valid url?").queue();
            }

        });



    }
}
