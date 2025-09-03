package com.exorastudios.xtaskdebug.command;

import com.exorastudios.library.command.annotation.AutoRegister;
import com.exorastudios.library.commandutil.BaseCommand;
import com.exorastudios.library.commandutil.annotation.CommandAlias;
import com.exorastudios.library.commandutil.annotation.CommandPermission;
import com.exorastudios.library.commandutil.annotation.Subcommand;
import com.exorastudios.library.task.ExoScheduler;
import com.exorastudios.xtaskdebug.util.TaskPrinter;
import com.exorastudios.xtaskdebug.util.ThreadPrinter;
import org.bukkit.command.CommandSender;

@AutoRegister
@CommandAlias("xtaskdebug")
@CommandPermission("xtaskdebug.use")
public class TaskDebugCommand extends BaseCommand {


    @Subcommand("task all")
    public void debugTasks(CommandSender sender) {
        ExoScheduler.pool("Async-Thread").execute(() -> TaskPrinter.printAllTasks(sender));
    }

    @Subcommand("task async")
    public void asyncTasks(CommandSender sender) {
        ExoScheduler.pool("Async-Thread").execute(() -> TaskPrinter.printActiveAsyncTasks(sender));
    }

    @Subcommand("task sync")
    public void syncTasks(CommandSender sender) {
        ExoScheduler.pool("Async-Thread").execute(() -> TaskPrinter.printActiveSyncTasks(sender));
    }

    @Subcommand("task pending")
    public void pendingTasks(CommandSender sender) {
        ExoScheduler.pool("Async-Thread").execute(() -> TaskPrinter.printPendingTasks(sender));
    }


    @Subcommand("thread all")
    public void allThreads(CommandSender sender) {
        ExoScheduler.pool("Async-Thread").execute(() -> ThreadPrinter.printAllThreads(sender));
    }

    @Subcommand("thread platform")
    public void platformThreads(CommandSender sender) {
        ExoScheduler.pool("Async-Thread").execute(() -> ThreadPrinter.printPlatformThreads(sender));
    }

    @Subcommand("thread virtual")
    public void virtualThreads(CommandSender sender) {
        ExoScheduler.pool("Async-Thread").execute(() -> ThreadPrinter.printVirtualThreads(sender));
    }

    @Subcommand("thread groups")
    public void threadGroups(CommandSender sender) {
        ExoScheduler.pool("Async-Thread").execute(() -> ThreadPrinter.printThreadGroups(sender));
    }
}
