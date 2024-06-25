package dev.boxadactle.coordinatesdisplay.command;

import com.google.common.collect.ImmutableList;
import com.mojang.brigadier.context.CommandContext;
import dev.boxadactle.boxlib.command.BCommandSourceStack;
import dev.boxadactle.boxlib.command.api.BClientCommand;
import dev.boxadactle.boxlib.util.GuiUtils;
import dev.boxadactle.coordinatesdisplay.CoordinatesDisplay;
import net.minecraft.network.chat.Component;

import java.util.List;

public class CoordinatesCommand {

    public static BClientCommand create() {
        return BClientCommand.create("coordinates", CoordinatesCommand::openCoordinatesScreen)
                .registerSubcommand(new ConfigSubcommand())
                .registerSubcommand(new CornerSubcommand())
                .registerSubcommand("help", CoordinatesCommand::showHelpMessage)
                .registerSubcommand(new ModeSubcommand())
                .registerSubcommand("movehud", CoordinatesCommand::moveHud)
                .registerSubcommand(new PositionSubcommand());
    }

    private static int openCoordinatesScreen(CommandContext<BCommandSourceStack> ignored) {

        CoordinatesDisplay.shouldCoordinatesGuiOpen = true;

        return 0;

    }

    private static int showHelpMessage(CommandContext<BCommandSourceStack> ignored) {
        List<Component> components = ImmutableList.of(
                GuiUtils.colorize(Component.translatable("command.coordinatesdisplay.helpmenu"), GuiUtils.AQUA),
                Component.translatable("command.coordinatesdisplay.config"),
                Component.translatable("command.coordinatesdisplay.gui"),
                Component.translatable("command.coordinatesdisplay.help"),
                Component.translatable("command.coordinatesdisplay.mode"),
                Component.translatable("command.coordinatesdisplay.movehud"),
                Component.translatable("command.coordinatesdisplay.position"),
                Component.translatable("command.coordinatesdisplay.visibility")
        );

        components.forEach(c -> {
            CoordinatesDisplay.LOGGER.player.chat(c);
        });

        return 0;
    }

    private static int moveHud(CommandContext<BCommandSourceStack> ignored) {
        CoordinatesDisplay.shouldHudPositionGuiOpen = true;

        return 0;
    }

}
