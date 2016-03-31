package com.github.csongradyp.badger.provider.unlock;

import com.github.csongradyp.badger.domain.IAchievementBean;
import com.github.csongradyp.badger.domain.achievement.ScoreAchievementBean;
import com.github.csongradyp.badger.domain.achievement.trigger.ScoreTrigger;
import com.github.csongradyp.badger.event.message.AchievementUnlockedEvent;
import com.github.csongradyp.badger.factory.UnlockedEventFactory;
import java.util.List;
import com.github.csongradyp.badger.repository.Repository;

public class ScoreUnlockedProvider extends UnlockedProvider<ScoreAchievementBean> {

    public ScoreUnlockedProvider(Repository repository) {
        super(repository);
    }

    @Override
    public AchievementUnlockedEvent getUnlockable(final String userId, final ScoreAchievementBean achievementBean, final Long currentValue) {
        final List<ScoreTrigger> triggers = achievementBean.getTrigger();
        for (int i = 0; i < triggers.size(); i++) {
            final Integer level = i + 1;
            if (triggers.get(i).fire(currentValue) && isLevelValid(achievementBean, level) && !isLevelUnlocked(userId, achievementBean.getId(), level)) {
                return UnlockedEventFactory.createEvent(userId, achievementBean, level, currentValue);
            }
        }
        return null;
    }

    private boolean isLevelValid(final IAchievementBean counterAchievement, final Integer triggerIndex) {
        return counterAchievement.getMaxLevel() >= triggerIndex;
    }

    private Boolean isLevelUnlocked(final String userId, final String id, final Integer level) {
        return repository.achievement().isUnlocked(userId, id, level);
    }

}
