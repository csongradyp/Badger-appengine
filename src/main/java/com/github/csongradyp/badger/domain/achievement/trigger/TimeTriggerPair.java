package com.github.csongradyp.badger.domain.achievement.trigger;

import com.github.csongradyp.badger.domain.AchievementType;
import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

public class TimeTriggerPair implements ITrigger<Date> {
    private final LocalTime startTrigger;
    private final LocalTime endTrigger;

    public TimeTriggerPair(final LocalTime startTrigger, final LocalTime endTrigger) {
        this.startTrigger = startTrigger;
        this.endTrigger = endTrigger;
    }

    @Override
    public Boolean fire(final Date triggerValue) {
        Boolean triggered;
        final LocalTime currentTime = new DateTime(triggerValue).toLocalTime();
        if (startTrigger.isBefore(endTrigger)) {
            triggered = isInRange(startTrigger, endTrigger, currentTime);
        } else {
            triggered = !isInRange(endTrigger, startTrigger, currentTime);
        }
        return triggered;
    }

    private Boolean isInRange(final LocalTime startTrigger, final LocalTime endTrigger, final LocalTime currentTime) {
        return (currentTime.isAfter(startTrigger) || currentTime.isEqual(startTrigger)) && (currentTime.isBefore(endTrigger) || currentTime.isEqual(endTrigger));
    }

    @Override
    public AchievementType getType() {
        return AchievementType.TIME_RANGE;
    }

    public LocalTime getStartTrigger() {
        return startTrigger;
    }

    public LocalTime getEndTrigger() {
        return endTrigger;
    }
}
