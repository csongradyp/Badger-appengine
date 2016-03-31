package com.github.csongradyp.badger.provider.unlock;

import com.github.csongradyp.badger.domain.achievement.TimeAchievementBean;
import com.github.csongradyp.badger.domain.achievement.trigger.TimeTrigger;
import com.github.csongradyp.badger.event.message.AchievementUnlockedEvent;
import com.github.csongradyp.badger.factory.UnlockedEventFactory;
import com.github.csongradyp.badger.provider.date.DateProvider;
import java.util.Date;
import java.util.List;
import com.github.csongradyp.badger.repository.Repository;

public class TimeUnlockedProvider extends UnlockedProvider<TimeAchievementBean> {

    public TimeUnlockedProvider(Repository repository) {
        super(repository);
    }

    public AchievementUnlockedEvent getUnlockable(final String userId, final TimeAchievementBean timeAchievement, final Long score) {
        final List<TimeTrigger> timeTriggers = timeAchievement.getTrigger();
        final String nowString = DateProvider.currentTimeString();
        final Date now = DateProvider.currentTime();
        for (TimeTrigger timeTrigger : timeTriggers) {
            if (timeTrigger.fire(now) && !isUnlocked(userId, timeAchievement.getId())) {
                return UnlockedEventFactory.createEvent(userId, timeAchievement, nowString);
            }
        }
        return null;
    }
}
