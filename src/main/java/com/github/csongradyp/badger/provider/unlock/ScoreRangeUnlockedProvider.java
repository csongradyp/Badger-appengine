package com.github.csongradyp.badger.provider.unlock;

import com.github.csongradyp.badger.event.message.AchievementUnlockedEvent;
import java.util.List;
import com.github.csongradyp.badger.domain.achievement.ScoreRangeAchievementBean;
import com.github.csongradyp.badger.domain.achievement.trigger.ScoreTriggerPair;
import com.github.csongradyp.badger.factory.UnlockedEventFactory;
import com.github.csongradyp.badger.repository.Repository;

public class ScoreRangeUnlockedProvider extends UnlockedProvider<ScoreRangeAchievementBean> {

    public ScoreRangeUnlockedProvider(Repository repository) {
        super(repository);
    }

    @Override
    public AchievementUnlockedEvent getUnlockable(final String userId, final ScoreRangeAchievementBean timeAchievement, final Long score) {
        final List<ScoreTriggerPair> timeTriggers = timeAchievement.getTrigger();
        for (ScoreTriggerPair trigger : timeTriggers) {
            if (trigger.fire(score) && !isUnlocked(userId, timeAchievement.getId())) {
                return UnlockedEventFactory.createEvent(userId, timeAchievement, String.valueOf(score));
            }
        }
        return null;
    }
}
