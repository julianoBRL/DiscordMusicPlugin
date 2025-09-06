package io.github.julianobrl.musicplugin.commands.slash.fun;

import io.github.julianobrl.discordbots.framework.annotations.commands.SlashCommand;
import io.github.julianobrl.discordbots.framework.commands.IExecuteCommands;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

@SlashCommand(name = "capy", description = "Random Capibara Gif")
public class CapyCommand implements IExecuteCommands {

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        event.reply("To be Implemented!").queue();
    }

}
