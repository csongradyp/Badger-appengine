package com.github.csongradyp.badger.repository;

import com.github.csongradyp.badger.domain.achievement.IPersistentAchievement;
import java.util.Collection;
import java.util.Date;

public interface AchievementRepository {

    /**
     * Unlocks an achievement for a user.
     *
     * @param userId        User unique identifier
     * @param achievementId achievement unique identifier
     */
    void unlock(final String userId, final String achievementId);

    /**
     * Unlocks an achievement for a user with given level.
     *
     * @param userId        User unique identifier
     * @param achievementId achievement unique identifier
     * @param level         achievement level to unlock
     */
    void unlock(final String userId, final String achievementId, final Integer level);

    /**
     * Clears all achievements for the user.
     *
     * @param userId User unique identifier
     */
    void clearAchievements(final String userId);

    /**
     * @return number of unlocked achievements
     */
    Long getNumberOfUnlocked();

    Collection<IPersistentAchievement> getAll(final String userId);

    /**
     * Is the achievement with the given name unlocked.
     *
     * @param userId User unique identifier
     * @param id     name of Achievement
     * @return {@code true} if the achievement is already unlocked.
     */
    Boolean isUnlocked(final String userId, final String id);

    /**
     * Is the achievement with given level and name unlocked.
     *
     * @param userId User unique identifier
     * @param id     name of Achievement
     * @param level  level to unlock
     * @return {@code true} if the achievement is already unlocked.
     */
    Boolean isUnlocked(final String userId, final String id, final Integer level);

    Date getAcquireDate(final String userId, final String achievementId);

}
