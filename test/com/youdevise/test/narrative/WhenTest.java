package com.youdevise.test.narrative;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(JMock.class)
public class WhenTest {
    private Mockery context = new Mockery();
    
    @SuppressWarnings("unchecked")
    @Test public void
    usesTheActorToPerformTheAction() {
        final Actor<String> actor = context.mock(Actor.class);
        final Action<String> action = context.mock(Action.class);
        
        context.checking(new Expectations() {{
            oneOf(actor).perform(action);
        }});
        
        When.the(actor).attemptsTo(action);
    }
    
    @SuppressWarnings("unchecked")
    @Test public void
    canPerformanManyActionsInARow() {
        final Actor<String> actor = context.mock(Actor.class);
        final Action<String> action = context.mock(Action.class, "first action");
        final Action<String> otherAction = context.mock(Action.class, "second action");
        
        final Sequence orderOfActions = context.sequence("Order of the actions");
        context.checking(new Expectations() {{
            oneOf(actor).perform(action); inSequence(orderOfActions);
            oneOf(actor).perform(otherAction); inSequence(orderOfActions);
        }});
        
        When.the(actor).attemptsTo(action)
                       .attemptsTo(otherAction);
    }
}
