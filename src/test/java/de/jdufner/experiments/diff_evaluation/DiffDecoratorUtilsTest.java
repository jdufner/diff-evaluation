package de.jdufner.experiments.diff_evaluation;

import org.junit.Assert;
import org.junit.Test;

public class DiffDecoratorUtilsTest {

  @Test
  public void test1() {
    String actual = "Hello Silvia!";
    String expected = "Hello Jürgen!";
    String htmlDiff = null;
    htmlDiff = DiffDecoratorUtils.buildActual(actual, expected);
    // System.out.println(htmlDiff);
    Assert.assertEquals("Hello <span class=\"diff\">Silvia</span>!", htmlDiff);
    htmlDiff = DiffDecoratorUtils.buildExpected(actual, expected);
    // System.out.println(htmlDiff);
    Assert.assertEquals("Hello <span class=\"diff\">Jürgen</span>!", htmlDiff);
  }

  @Test
  public void test2() {
    String actual = "Hello World!";
    String expected = "Hallo Welt!";
    String htmlDiff = null;
    htmlDiff = DiffDecoratorUtils.buildActual(actual, expected);
    // System.out.println(htmlDiff);
    Assert.assertEquals("H<span class=\"diff\">e</span>llo W<span class=\"diff\">or</span>l<span class=\"diff\">d</span>!",
        htmlDiff);
    htmlDiff = DiffDecoratorUtils.buildExpected(actual, expected);
    // System.out.println(htmlDiff);
    Assert.assertEquals("H<span class=\"diff\">a</span>llo W<span class=\"diff\">e</span>l<span class=\"diff\">t</span>!",
        htmlDiff);
  }

  @Test
  public void testCompletelyDifferentTexts() {
    String actual = "abc";
    String expected = "xyz";
    String htmlDiff = null;
    htmlDiff = DiffDecoratorUtils.buildActual(actual, expected);
    // System.out.println(htmlDiff);
    Assert.assertEquals("<span class=\"diff\">abc</span>", htmlDiff);
    htmlDiff = DiffDecoratorUtils.buildExpected(actual, expected);
    // System.out.println(htmlDiff);
    Assert.assertEquals("<span class=\"diff\">xyz</span>", htmlDiff);
  }

  @Test
  public void testOneDifferentCharInMiddleOfText() {
    String actual = "abc";
    String expected = "axc";
    String htmlDiff = null;
    htmlDiff = DiffDecoratorUtils.buildActual(actual, expected);
    // System.out.println(htmlDiff);
    Assert.assertEquals("a<span class=\"diff\">b</span>c", htmlDiff);
    htmlDiff = DiffDecoratorUtils.buildExpected(actual, expected);
    // System.out.println(htmlDiff);
    Assert.assertEquals("a<span class=\"diff\">x</span>c", htmlDiff);
  }

  @Test
  public void testOneDifferentCharAtTheBeginningOfText() {
    String actual = "abc";
    String expected = "xbc";
    String htmlDiff = null;
    htmlDiff = DiffDecoratorUtils.buildActual(actual, expected);
    // System.out.println(htmlDiff);
    Assert.assertEquals("<span class=\"diff\">a</span>bc", htmlDiff);
    htmlDiff = DiffDecoratorUtils.buildExpected(actual, expected);
    // System.out.println(htmlDiff);
    Assert.assertEquals("<span class=\"diff\">x</span>bc", htmlDiff);
  }

  @Test
  public void testOneDifferentCharAtTheEndOfText() {
    String actual = "abc";
    String expected = "abx";
    String htmlDiff = null;
    htmlDiff = DiffDecoratorUtils.buildActual(actual, expected);
    // System.out.println(htmlDiff);
    Assert.assertEquals("ab<span class=\"diff\">c</span>", htmlDiff);
    htmlDiff = DiffDecoratorUtils.buildExpected(actual, expected);
    // System.out.println(htmlDiff);
    Assert.assertEquals("ab<span class=\"diff\">x</span>", htmlDiff);
  }

}
