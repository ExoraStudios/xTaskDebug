package com.exorastudios.xtaskdebug.command;

import com.exorastudios.library.command.annotation.AutoRegister;
import com.exorastudios.library.commandutil.BaseCommand;
import com.exorastudios.library.commandutil.annotation.CommandAlias;
import com.exorastudios.library.commandutil.annotation.CommandPermission;
import com.exorastudios.library.commandutil.annotation.Subcommand;
import com.exorastudios.library.task.ExoScheduler;
import com.exorastudios.xtaskdebug.util.TaskPrinter;
import org.bukkit.command.CommandSender;

@AutoRegister
@CommandAlias("xtaskdebug")
@CommandPermission("xtaskdebug.use")
public class TaskDebugCommand extends BaseCommand {

    @Subcommand("all")
    public void debug(CommandSender sender) {
        ExoScheduler.pool("Async-Thread").execute(() -> {
            TaskPrinter.printAllTasks(sender);
        });
    }

    @Subcommand("async")
    public void async(CommandSender sender) {
        ExoScheduler.pool("Async-Thread").execute(() -> {
            TaskPrinter.printActiveAsyncTasks(sender);
        });
    }

    @Subcommand("sync")
    public void sync(CommandSender sender) {
        ExoScheduler.pool("Async-Thread").execute(() -> {
            TaskPrinter.printActiveSyncTasks(sender);
        });
    }

    @Subcommand("pending")
    public void pending(CommandSender sender) {
        ExoScheduler.pool("Async-Thread").execute(() -> {
            TaskPrinter.printPendingTasks(sender);
        });
    }
}
