package com.github.csongradyp.badger.provider.unlock;

import com.github.csongradyp.badger.domain.achievement.TimeRangeAchievementBean;
import com.github.csongradyp.badger.event.message.AchievementUnlockedEvent;
import com.github.csongradyp.badger.factory.UnlockedEventFactory;
import com.github.csongradyp.badger.provider.date.DateProvider;
import java.util.Date;
import java.util.List;
import com.github.csongradyp.badger.domain.achievement.trigger.TimeTriggerPair;
import com.github.csongradyp.badger.repository.Repository;

public class TimeRangeUnlockedProvider extends UnlockedProvider<TimeRangeAchievementBean> {

    public TimeRangeUnlockedProvider(Repository repository) {
        super(repository);
    }

    @Override
    public AchievementUnlockedEvent getUnlockable(final String userId, final TimeRangeAchievementBean timeAchievement, final Long score) {
        final List<TimeTriggerPair> timeTriggers = timeAchievement.getTrigger();
        for (TimeTriggerPair timeTrigger : timeTriggers) {
            final Date now = DateProvider.currentTime();
            if(timeTrigger.fire(now) && !isUnlocked(userId, timeAchievement.getId())) {
                return UnlockedEventFactory.createEvent(userId, timeAchievement, DateProvider.currentTimeString());
            }
        }
        return null;
    }
}
