package com.github.csongradyp.badger.provider.unlock;

import com.github.csongradyp.badger.domain.achievement.CompositeAchievementBean;
import com.github.csongradyp.badger.event.message.AchievementUnlockedEvent;
import com.github.csongradyp.badger.factory.UnlockedEventFactory;
import com.github.csongradyp.badger.provider.date.DateProvider;
import java.util.Date;
import com.github.csongradyp.badger.repository.Repository;

public class CompositeUnlockedProvider extends UnlockedProvider<CompositeAchievementBean> {

    public CompositeUnlockedProvider(Repository repository) {
        super(repository);
    }

    @Override
    public AchievementUnlockedEvent getUnlockable(final String userId, final CompositeAchievementBean compositeAchievement, final Long score) {
        final Date currentDate = DateProvider.currentDate();
        final Date currentTime = DateProvider.currentTime();
        if (compositeAchievement.getRelation().evaluate(score, currentDate, currentTime) & !isUnlocked(userId, compositeAchievement.getId())) {
            return UnlockedEventFactory.createEvent(userId, compositeAchievement, score.toString());
        }
        return null;
    }

}
