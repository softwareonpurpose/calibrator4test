package com.craigstockton.validator4test;

import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class IndentManagerTest {
    @Test
    public void noIndent() {
        final String message = "Message Text";
        Assert.assertEquals(IndentManager.getInstance().format(message), String.format("%s", message),
                "Initially, " + "format() returns message with NO indentation");
    }

    @Test
    public void nullMessage() {
        Assert.assertEquals(IndentManager.getInstance().format(null), "", "Null message returns empty String");
    }

    @Test
    public void singleIndentation() {
        final String message = "Message Text";
        final IndentManager manager = IndentManager.getInstance(3);
        manager.increment();
        Assert.assertEquals(manager.format(message), String.format("   %s", message), "After incrementing by 1, " +
                "format() returns message with four-space indentation");
    }

    @Test
    public void doubleIndentation() {
        final String message = "Message Text";
        final IndentManager manager = IndentManager.getInstance(4);
        manager.increment(2);
        Assert.assertEquals(manager.format(message), String.format("        %s", message), "After incrementing by " +
                "2," +
                "" + " format() returns message with eight-space indentation");
    }

    @Test
    public void decrementOnce() {
        final String message = "Message Text";
        final IndentManager manager = IndentManager.getInstance(4);
        manager.increment(2);
        manager.decrement();
        Assert.assertEquals(manager.format(message), String.format("    %s", message), "After incrementing by 2 " +
                "and decrementing once, format() returns message with four-space indentation");
    }

    @Test
    public void decrementLessThanOne() {
        final String message = "Message Text";
        final IndentManager manager = IndentManager.getInstance();
        manager.decrement();
        Assert.assertEquals(manager.format(message), String.format("%s", message), "After decrementing to a level "
                + "< 1, format() returns message with NO indentation");
    }
}
