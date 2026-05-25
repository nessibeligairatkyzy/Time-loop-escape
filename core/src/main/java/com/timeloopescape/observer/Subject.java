package com.timeloopescape.observer;

public interface Subject {
    void addObserver(Observer observer);
    void notifyObservers(String event);
}
// Team Member 1 — Subject Interface:
// Defines methods for adding observers and notifying them.

