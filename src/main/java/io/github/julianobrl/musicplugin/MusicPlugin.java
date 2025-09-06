package io.github.julianobrl.musicplugin;

import io.github.julianobrl.discordbots.framework.managers.SlashCommandsManager;
import io.github.julianobrl.musicplugin.commands.slash.music.PlayCommand;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.Plugin;

@Slf4j
public class MusicPlugin extends Plugin {

    @Override
    public void start() {
        log.info("MusicPlugin-Start");
        SlashCommandsManager.getInstance().addCommand(PlayCommand.class);
    }

    @Override
    public void stop() {
        log.info("MusicPlugin-Stop");
    }

    @Override
    public void delete() {
        log.info("MusicPlugin-Delete");

    }

}
