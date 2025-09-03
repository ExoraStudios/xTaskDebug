package com.exorastudios.xtaskdebug.util;

import com.exorastudios.library.messaging.ExoMessage;
import com.exorastudios.library.text.ExoText;
import org.bukkit.command.CommandSender;

import java.util.*;
import java.util.stream.Collectors;

public class ThreadPrinter {

    private static final int HIGH_THRESHOLD = 200;
    private static final int MID_THRESHOLD = 100;

    public static void printAllThreads(final CommandSender sender) {
        ExoMessage.send(sender, "&8--- &#FFD700&lTHREAD DEBUGGER &8---");
        ExoMessage.send(sender, "");

        printPlatformThreads(sender);
        printVirtualThreads(sender);
        printThreadGroups(sender);

        ExoMessage.send(sender, "&8--- &#FFD700&lTHREAD DEBUGGER &8---");
    }

    public static void printPlatformThreads(final CommandSender sender) {
        final Map<Thread.State, Integer> counts = new EnumMap<>(Thread.State.class);

        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (!thread.isVirtual()) {
                counts.merge(
                        thread.getState(),
                        1,
                        Integer::sum
                );
            }
        }

        ExoMessage.send(sender, "&#00FF63⌛ &lPLATFORM THREADS:");

        counts.forEach((state, count) ->
                ExoMessage.send(sender, "&#00FF63♦ &f" + state + ": " + colorize(count, "&#00FF63"))
        );

        ExoMessage.send(sender, "");
    }

    public static void printVirtualThreads(final CommandSender sender) {
        final Map<Thread.State, Integer> counts = new EnumMap<>(Thread.State.class);

        for (Thread thread : Thread.getAllStackTraces().keySet()) {
            if (thread.isVirtual()) {

                counts.merge(
                        thread.getState(),
                        1,
                        Integer::sum
                );
            }
        }

        ExoMessage.send(sender, "&#00A3FF⚡ &lVIRTUAL THREADS:");

        counts.forEach((state, count) ->
                ExoMessage.send(sender, "&#00A3FF♦ &f" + state + ": " + colorize(count, "&#00A3FF"))
        );

        ExoMessage.send(sender, "");
    }

    public static void printThreadGroups(final CommandSender sender) {
        final Map<String, Integer> groups = new HashMap<>();

        for (Thread thread : Thread.getAllStackTraces().keySet()) {

            final String groupName = thread
                    .getName()
                    .replaceAll("\\d+", "*"
                    );

            groups.merge(
                    groupName,
                    1,
                    Integer::sum
            );
        }

        final Map<String, Integer> significantGroups = groups.entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1)
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));

        ExoMessage.send(sender, "&#FF7B00🔔 &lTHREAD GROUPS:");

        significantGroups.forEach((group, count) ->
                ExoMessage.send(sender, "&#FF7B00♦ &f" + group + "-*: " + colorize(count, "&#FF7B00"))
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
