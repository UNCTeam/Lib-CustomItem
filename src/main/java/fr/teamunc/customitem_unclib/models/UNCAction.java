package fr.teamunc.customitem_unclib.models;

import org.bukkit.event.Event;

public interface UNCAction<T extends Event> {
    T execute(T event);
}
