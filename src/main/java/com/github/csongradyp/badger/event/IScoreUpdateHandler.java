package com.github.csongradyp.badger.event;

import com.github.csongradyp.badger.event.message.ScoreUpdatedEvent;

/**
 * Interface to handle triggered event score update events.
 */
public interface IScoreUpdateHandler {

    /**
     * Callback method to receive update information.
     *
     * @param scoreUpdatedEvent Updated event counter/score as a {@link ScoreUpdatedEvent} instance.
     */
    void onUpdate(ScoreUpdatedEvent scoreUpdatedEvent);
}
