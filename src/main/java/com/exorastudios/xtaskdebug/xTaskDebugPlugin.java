package com.exorastudios.xtaskdebug;

import com.exorastudios.library.ExoPlugin;
import com.exorastudios.library.command.ExoCommand;
import com.exorastudios.library.task.ExoScheduler;

public final class xTaskDebugPlugin extends ExoPlugin {

    @Override
    protected void start() {
        ExoCommand.of(this);

        ExoScheduler.Builder.executor(this)
                .name("xTaskDebug Async Processor Thread (x1)")
                .identifier("Async-Thread")
                .threads(1)
                .build();
    }

    @Override
    protected void stop() {
        ExoScheduler.shutdown();
    }
}