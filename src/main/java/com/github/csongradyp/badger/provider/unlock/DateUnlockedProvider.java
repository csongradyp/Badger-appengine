package com.github.csongradyp.badger.provider.unlock;

import com.github.csongradyp.badger.domain.achievement.DateAchievementBean;
import com.github.csongradyp.badger.domain.achievement.trigger.DateTrigger;
import com.github.csongradyp.badger.event.message.AchievementUnlockedEvent;
import com.github.csongradyp.badger.factory.UnlockedEventFactory;
import com.github.csongradyp.badger.provider.date.DateProvider;
import java.util.Date;
import java.util.List;
import com.github.csongradyp.badger.repository.Repository;

public class DateUnlockedProvider extends UnlockedProvider<DateAchievementBean> {

    public DateUnlockedProvider(Repository repository) {
        super(repository);
    }

    @Override
    public AchievementUnlockedEvent getUnlockable(final String userId, final DateAchievementBean dateAchievement, final Long score) {
        final List<DateTrigger> dateTriggers = dateAchievement.getTrigger();
        final String nowString = DateProvider.currentDateString();
        final Date now = DateProvider.currentDate();
        for (DateTrigger dateTrigger : dateTriggers) {
            if (dateTrigger.fire(now) && !isUnlocked(userId, dateAchievement.getId())) {
                return UnlockedEventFactory.createEvent(userId, dateAchievement, nowString);
            }
        }
        return null;
    }

}
