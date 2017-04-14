package com.lavendergoons.dndcharacter.Exceptions;

/**
 * Exception for CharacterManager Database Not Loaded
 */

public class DatabaseNotInitializedException extends Exception {
    public DatabaseNotInitializedException(String msg) {
        super(msg + " did not load database into CharacterManager");
    }
}
