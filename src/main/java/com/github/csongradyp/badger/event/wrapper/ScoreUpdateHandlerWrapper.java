package com.github.csongradyp.badger.event.wrapper;

import com.github.csongradyp.badger.event.IScoreUpdateHandler;
import com.github.csongradyp.badger.event.message.ScoreUpdatedEvent;
import net.engio.mbassy.listener.Handler;
import net.engio.mbassy.listener.Listener;
import net.engio.mbassy.listener.References;

/**
 * Wrapper implementation for proper registration of handlers to {@link net.engio.mbassy.bus.MBassador} event bus.
 */
@Listener(references = References.Strong)
public class ScoreUpdateHandlerWrapper implements IScoreUpdateHandler {

    private final IScoreUpdateHandler wrapped;

    public ScoreUpdateHandlerWrapper(final IScoreUpdateHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    @Handler(rejectSubtypes = true)
    public void onUpdate(final ScoreUpdatedEvent scoreUpdatedEvent) {
        wrapped.onUpdate(scoreUpdatedEvent);
    }

    /**
     * Returns the originally registered handler which was wrapped.
     *
     * @return originally registered handler.
     */
    public IScoreUpdateHandler getWrapped() {
        return wrapped;
    }
}
