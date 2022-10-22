package fr.teamunc.customitem_unclib.models;

import org.bukkit.event.Event;

public interface UNCAction<T extends Event> {
    // returned value is the durability consumed
    int execute(T event);
}
