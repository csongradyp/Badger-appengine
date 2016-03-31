package com.github.csongradyp.badger.domain.achievement;

import com.github.csongradyp.badger.domain.achievement.relation.Relation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.github.csongradyp.badger.domain.AchievementType;
import com.github.csongradyp.badger.domain.ITriggerableAchievementBean;
import com.github.csongradyp.badger.domain.achievement.trigger.ITrigger;

public class CompositeAchievementBean extends AbstractAchievementBean implements ITriggerableAchievementBean<ITrigger> {

    private Relation relation;
    private List<ITrigger> triggers;

    public CompositeAchievementBean() {
        triggers = new ArrayList<>();
    }

    @Override
    public void setTrigger(final List<ITrigger> triggers) {
        this.triggers = triggers;
    }

    @Override
    public List<ITrigger> getTrigger() {
        return triggers;
    }

    @Override
    public AchievementType getType() {
        return AchievementType.COMPOSITE;
    }

    public void addTrigger(final Collection<ITrigger> triggers) {
        this.triggers.addAll(triggers);
    }

    public void setRelation(final Relation relation) {
        this.relation = relation;
    }

    public Relation getRelation() {
        return relation;
    }
}
