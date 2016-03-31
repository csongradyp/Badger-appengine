package com.github.csongradyp.badger.domain.achievement.relation;

import com.github.csongradyp.badger.domain.achievement.trigger.ITrigger;
import java.util.Collection;
import java.util.Date;
import com.github.csongradyp.badger.domain.AchievementType;

public class RelationElement implements IRelation {

    private Collection<ITrigger> triggers;

    public RelationElement(final Collection<ITrigger> triggers) {
        this.triggers = triggers;
    }

    @Override
    public Boolean evaluate(final Long score, final Date date, final Date time) {
        Boolean triggered = false;
        for (ITrigger trigger : triggers) {
            final AchievementType type = trigger.getType();
            if (AchievementType.DATE == type) {
                triggered |= trigger.fire(date);
            } else if (AchievementType.TIME == type || AchievementType.TIME_RANGE == type) {
                triggered |= trigger.fire(time);
            } else if (AchievementType.SCORE == type) {
                triggered |= trigger.fire(score);
            }
        }
        return triggered;
    }
}
