package com.github.csongradyp.badger.exception;


import com.github.csongradyp.badger.domain.AchievementType;

public class AchievementNotFoundException extends RuntimeException {

    public AchievementNotFoundException(final String message) {
        super(message);
    }

    public AchievementNotFoundException(final AchievementType type, final String id) {
        super("Achievement not found within type:" + type.getType() + " with id:" + id);
    }
}
