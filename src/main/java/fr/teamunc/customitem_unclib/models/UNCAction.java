package fr.teamunc.customitem_unclib.models;

import org.bukkit.event.Event;

public interface UNCAction {
    // returned value is the durability consumed
    int execute(Event event);
}
