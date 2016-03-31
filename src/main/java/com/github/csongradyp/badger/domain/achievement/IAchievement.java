package com.github.csongradyp.badger.domain.achievement;

import com.github.csongradyp.badger.domain.AchievementType;
import java.util.List;

public interface IAchievement {

    String getId();

    String getCategory();

    List<String> getSubscriptions();

    String getTitleKey();

    String getTextKey();

    Integer getMaxLevel();

    AchievementType getType();

}
