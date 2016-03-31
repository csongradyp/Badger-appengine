package com.github.csongradyp.badger.provider;

import com.github.csongradyp.badger.event.message.AchievementUnlockedEvent;
import com.github.csongradyp.badger.domain.achievement.IAchievement;

public interface IUnlockedProvider<TYPE extends IAchievement> {

    AchievementUnlockedEvent getUnlockable(String userId, TYPE achievement, Long score);
}
