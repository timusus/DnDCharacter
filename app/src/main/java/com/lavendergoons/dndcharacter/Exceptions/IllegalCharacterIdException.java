package com.lavendergoons.dndcharacter.Exceptions;

import com.google.firebase.crash.FirebaseCrash;
import com.lavendergoons.dndcharacter.BuildConfig;

/**
 * Exception for illegal CharacterId
 */

public class IllegalCharacterIdException extends RuntimeException {
    public IllegalCharacterIdException(String msg) {
        super(msg + " has illegal CharacterId");
        if (!BuildConfig.DEBUG) {
            FirebaseCrash.log(msg + " has illegal CharacterId");
        }
    }
}
