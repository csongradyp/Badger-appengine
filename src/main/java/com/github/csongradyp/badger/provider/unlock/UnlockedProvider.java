package com.github.csongradyp.badger.provider.unlock;

import com.github.csongradyp.badger.domain.achievement.IAchievement;
import com.github.csongradyp.badger.provider.IUnlockedProvider;
import com.github.csongradyp.badger.repository.Repository;

abstract class UnlockedProvider<TYPE extends IAchievement> implements IUnlockedProvider<TYPE> {

    protected Repository repository;

    public UnlockedProvider(final Repository repository) {
        this.repository = repository;
    }

    public Boolean isUnlocked(final String userId, final String achievementId) {
        return repository.achievement().isUnlocked(userId, achievementId);
    }

}
