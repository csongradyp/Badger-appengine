package com.github.csongradyp.badger.domain.achievement;

import com.github.csongradyp.badger.domain.achievement.trigger.DateTrigger;
import java.util.ArrayList;
import java.util.List;
import com.github.csongradyp.badger.domain.AchievementType;
import com.github.csongradyp.badger.domain.ITriggerableAchievementBean;

public class DateAchievementBean extends AbstractAchievementBean implements ITriggerableAchievementBean<DateTrigger> {

    private List<DateTrigger> trigger;

    public DateAchievementBean() {
        trigger = new ArrayList<>();
    }

    @Override
    public List<DateTrigger> getTrigger() {
        return trigger;
    }

    @Override
    public void setTrigger(final List<DateTrigger> trigger) {
        this.trigger = trigger;
    }

    @Override
    public AchievementType getType() {
        return AchievementType.DATE;
    }
}
