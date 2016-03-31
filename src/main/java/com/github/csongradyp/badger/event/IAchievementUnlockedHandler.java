package com.github.csongradyp.badger.event;

import com.github.csongradyp.badger.event.message.AchievementUnlockedEvent;

/**
 * Interface to handle unlocked achievement events.
 */
public interface IAchievementUnlockedHandler {

    /**
     * Callback method to receive notification about the unlocked achievements.
     *
     * @param achievementUnlockedEvent Unlocked achievement information as an {@link AchievementUnlockedEvent} instance.
     */
    void onUnlocked(IAchievementUnlockedEvent achievementUnlockedEvent);
}
