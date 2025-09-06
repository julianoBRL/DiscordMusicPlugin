package io.github.julianobrl.musicplugin.commands.slash.utils;

import io.github.julianobrl.discordbots.framework.annotations.commands.SlashCommand;
import io.github.julianobrl.discordbots.framework.commands.IExecuteCommands;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;


@SlashCommand(name = "ping", description = "Ping <-> Pong")
public class PingCommand implements IExecuteCommands {

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("Pong!").queue();
    }

}
