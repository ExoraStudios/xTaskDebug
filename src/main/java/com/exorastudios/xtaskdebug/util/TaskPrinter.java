package com.exorastudios.xtaskdebug.util;

import com.exorastudios.library.messaging.ExoMessage;
import com.exorastudios.library.text.ExoText;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;

import java.util.HashMap;
import java.util.Map;

public class TaskPrinter {

    private static final int HIGH_THRESHOLD = 50;
    private static final int MID_THRESHOLD = 20;

    public static void printAllTasks(final CommandSender sender) {
        ExoMessage.send(sender, "&8--- &#D0FF00&lTASK DEBUGGER &8---");
        ExoMessage.send(sender, "");

        printPendingTasks(sender);
        printActiveSyncTasks(sender);
        printActiveAsyncTasks(sender);

        ExoMessage.send(sender, "&8--- &#D0FF00&lTASK DEBUGGER &8---");
    }

    public static void printPendingTasks(final CommandSender sender) {
        final Map<String, Integer> counts = new HashMap<>();

        for (BukkitTask task : Bukkit.getScheduler().getPendingTasks()) {

            counts.merge(task.getOwner().getName(),
                    1,
                    Integer::sum
            );
        }

        ExoMessage.send(sender, "&#00FF63⌛ &lPENDING TASKS:");

        counts.forEach((
                plugin,
                count) ->
                ExoMessage.send(sender, "&#00FF63♦ &f" + plugin + ": " + colorize(count, "&#00FF63"))
        );

        ExoMessage.send(sender, "");
    }

    public static void printActiveSyncTasks(final CommandSender sender) {
        final Map<String, Integer> counts = new HashMap<>();

        for (BukkitWorker worker : Bukkit.getScheduler().getActiveWorkers()) {

            counts.merge(worker.getOwner().getName(),
                    1,
                    Integer::sum
            );
        }

        ExoMessage.send(sender, "&#FF7B00🔔 &lACTIVE SYNC TASKS:");

        counts.forEach((
                plugin,
                count) ->
                ExoMessage.send(sender, "&#FF7B00♦ &f" + plugin + ": " + colorize(count, "&#FF7B00"))
        );

        ExoMessage.send(sender, "");
    }

    public static void printActiveAsyncTasks(final CommandSender sender) {
        final Map<String, Integer> counts = new HashMap<>();

        for (BukkitTask task : Bukkit.getScheduler().getPendingTasks()) {

            if (!task.isSync()) {

                counts.merge(task.getOwner().getName(),
                        1,
                        Integer::sum
                );
            }
        }

        ExoMessage.send(sender, "&#00A3FF⚡ &lACTIVE ASYNC TASKS:");

        counts.forEach((
                plugin,
                count) ->
                ExoMessage.send(sender, "&#00A3FF♦ &f" + plugin + ": " + colorize(count, "&#00A3FF"))
        );

        ExoMessage.send(sender, "");
    }

    public static String colorize(final int count, final String baseColor) {
        final String color =

                count >= HIGH_THRESHOLD ? "&c" :
                        count >= MID_THRESHOLD ? "&e" : baseColor;

        return ExoText.parseLegacy(color + count);
    }
}
