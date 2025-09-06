package io.github.julianobrl.musicplugin.listeners;

import io.github.julianobrl.discordbots.framework.annotations.events.JDAEventListener;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

@Slf4j
@JDAEventListener
public class EventListener extends ListenerAdapter {

    @Override
    public void onReady(ReadyEvent event) {
        log.info("Aplicação pronta!");
    }

    @Override
    public void onShutdown( ShutdownEvent event) {
        log.info("Aplicação desligando");
    }

}
