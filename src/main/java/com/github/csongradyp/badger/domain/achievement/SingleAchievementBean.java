package com.github.csongradyp.badger.domain.achievement;

import com.github.csongradyp.badger.domain.AchievementType;

public class SingleAchievementBean extends AbstractAchievementBean {

    @Override
    public AchievementType getType() {
        return AchievementType.SINGLE;
    }
}
