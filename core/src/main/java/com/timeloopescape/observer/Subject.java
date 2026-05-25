package com.timeloopescape.observer;

public interface Subject {
    void addObserver(Observer observer);
    void notifyObservers(String event);
}
