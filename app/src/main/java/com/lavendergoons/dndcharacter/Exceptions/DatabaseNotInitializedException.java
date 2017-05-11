package com.lavendergoons.dndcharacter.Exceptions;

import com.google.firebase.crash.FirebaseCrash;
import com.lavendergoons.dndcharacter.BuildConfig;

/**
 * Exception for CharacterManager Database Not Loaded
 */

public class DatabaseNotInitializedException extends RuntimeException {
    public DatabaseNotInitializedException(String msg) {
        super(msg + " did not load database into CharacterManager");
        if (!BuildConfig.DEBUG) {
            FirebaseCrash.log(msg + " did not load database into CharacterManager");
        }
    }
}
