package de.jdufner.experiments.diff_evaluation;

import org.junit.Assert;
import org.junit.Test;

public class DiffDecoratorUtilsTest {

  @Test
  public void testHelloWorld() {
    String actual = "Hello World!";
    String expected = "Hallo Welt!";
    String htmlDiff = null;
    htmlDiff = DiffDecoratorUtils.buildActual(actual, expected);
    Assert.assertEquals("H[e]llo W[or]l[d]!", htmlDiff);
    htmlDiff = DiffDecoratorUtils.buildExpected(actual, expected);
    Assert.assertEquals("H[a]llo W[e]l[t]!", htmlDiff);
  }

  @Test
  public void testCompletelyDifferentTexts() {
    String actual = "abc";
    String expected = "xyz";
    String htmlDiff = null;
    htmlDiff = DiffDecoratorUtils.buildActual(actual, expected);
    Assert.assertEquals("[abc]", htmlDiff);
    htmlDiff = DiffDecoratorUtils.buildExpected(actual, expected);
    Assert.assertEquals("[xyz]", htmlDiff);
  }

  @Test
  public void testOneDifferentCharInMiddleOfText() {
    String actual = "abc";
    String expected = "axc";
    String htmlDiff = null;
    htmlDiff = DiffDecoratorUtils.buildActual(actual, expected);
    Assert.assertEquals("a[b]c", htmlDiff);
    htmlDiff = DiffDecoratorUtils.buildExpected(actual, expected);
    Assert.assertEquals("a[x]c", htmlDiff);
  }

  @Test
  public void testOneDifferentCharAtTheBeginningOfText() {
    String actual = "abc";
    String expected = "xbc";
    String htmlDiff = null;
    htmlDiff = DiffDecoratorUtils.buildActual(actual, expected);
    Assert.assertEquals("[a]bc", htmlDiff);
    htmlDiff = DiffDecoratorUtils.buildExpected(actual, expected);
    Assert.assertEquals("[x]bc", htmlDiff);
  }

  @Test
  public void testOneDifferentCharAtTheEndOfText() {
    String actual = "abc";
    String expected = "abx";
    String htmlDiff = null;
    htmlDiff = DiffDecoratorUtils.buildActual(actual, expected);
    Assert.assertEquals("ab[c]", htmlDiff);
    htmlDiff = DiffDecoratorUtils.buildExpected(actual, expected);
    Assert.assertEquals("ab[x]", htmlDiff);
  }

  @Test
  public void testBuilder() {
    String actual = "Hello Silvia!";
    String expected = "Hello Jürgen!";
    String htmlDiff = null;
    htmlDiff = new DiffDecoratorUtils.ActualBuilder(actual, expected).setOpeningTag("<span class=\"diff\">")
        .setClosingTag("</span>").build();
    Assert.assertEquals("Hello <span class=\"diff\">Silvia</span>!", htmlDiff);
    htmlDiff = new DiffDecoratorUtils.ExpectedBuilder(actual, expected).setOpeningTag("<span class=\"diff\">")
        .setClosingTag("</span>").build();
    Assert.assertEquals("Hello <span class=\"diff\">Jürgen</span>!", htmlDiff);
  }

}
