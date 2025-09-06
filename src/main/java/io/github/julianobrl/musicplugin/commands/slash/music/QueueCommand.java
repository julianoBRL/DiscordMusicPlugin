package io.github.julianobrl.musicplugin.commands.slash.music;

import io.github.julianobrl.discordbots.framework.annotations.commands.SlashCommand;
import io.github.julianobrl.discordbots.framework.commands.IExecuteCommands;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@SlashCommand(name = "queue", description = "List the music queue.")
public class QueueCommand implements IExecuteCommands {

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



    }

}
