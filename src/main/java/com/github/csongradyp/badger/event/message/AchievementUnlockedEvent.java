package com.github.csongradyp.badger.event.message;

import java.util.Date;
import com.github.csongradyp.badger.event.AchievementEventType;
import com.github.csongradyp.badger.event.IAchievementUnlockedEvent;

public class AchievementUnlockedEvent implements IAchievementUnlockedEvent {

    private final String id;
    private final String title;
    private final String text;
    private final Date acquireDate;
    private final String triggerValue;
    private String category;
    private Integer level;
    private AchievementEventType eventType;
    private String owner;

    public AchievementUnlockedEvent(final String owner, final String id, final String title, final String text, final String triggerValue) {
        this.owner = owner;
        this.id = id;
        this.title = title;
        this.text = text;
        this.acquireDate = new Date();
        this.triggerValue = triggerValue;
        this.level = 1;
        eventType = AchievementEventType.UNLOCK;
        category = "default";
    }

    /**
     * Returns the ID of the unlocked achievement.
     *
     * @return Unique ID of the achievement defined in the achievement definition file.
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * Returns the category of the achievement. Default value is {@code "default"}.
     *
     * @return Category of the unlocked achievement.
     */
    @Override
    public String getCategory() {
        return category;
    }

    /**
     * Returns the localized title of the achievement.
     * If no internationalization file is given it will return the localization key ({@code ACHIEVEMENT_ID.title}).
     *
     * @return Localized message or the i18n key of the achievement title.
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Returns the localized description of the achievement.
     * If no internationalization file is given it will return the localization key ({@code ACHIEVEMENT_ID.text}).
     *
     * @return Localized message or the i18n key of the achievement description.
     */
    @Override
    public String getText() {
        return text;
    }

    /**
     * Returns the unlock date of the achievement.
     *
     * @return {@link Date} of unlocked event.
     */
    @Override
    public Date getAcquireDate() {
        return acquireDate;
    }

    /**
     * Value which has unlocked the achievement.
     *
     * @return {@link String} representation of the value with the unlock event was triggered.
     */
    @Override
    public String getTriggerValue() {
        return triggerValue;
    }

    /**
     * Returns the current unlocked level of the achievement.
     *
     * @return Unlocked level of the achievement.
     */
    @Override
    public Integer getLevel() {
        return level;
    }

    /**
     * Sets the unlocked level of the achievement.
     *
     * @param level new acquired level.
     */
    @Override
    public void setLevel(final Integer level) {
        this.level = level;
    }

    /**
     * Returns whether the achievement was newly unlocked or was leveled up.
     *
     * @return {@link AchievementEventType} instance.
     */
    @Override
    public AchievementEventType getEventType() {
        return eventType;
    }

    /**
     * Sets the category of the achievement. Default category is {@literal "default"}.
     * Category could be useful to sort achievements by some conventions which is not present.
     * (E.g.: Positive or negative badges)
     *
     * @param category New category of the achievement.
     */
    public void setCategory(final String category) {
        this.category = category;
    }

    @Override
    public String getOwner() {
        return owner;
    }

}
