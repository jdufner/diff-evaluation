package de.jdufner.experiments.diff_evaluation;

import org.junit.Test;

public class HtmlDiffUtilsTest {

  @Test
  public void test1() {
    String actual = "Hello Silvia!";
    String expected = "Hello Jürgen!";
    String htmlDiff = null;
    htmlDiff = HtmlDiffUtils.buildActualAsHtml(actual, expected);
    System.out.println(htmlDiff);
    htmlDiff = HtmlDiffUtils.buildExpectedAsHtml(actual, expected);
    System.out.println(htmlDiff);
  }

  @Test
  public void test2() {
    String actual = "Hello World!";
    String expected = "Hallo Welt!";
    String htmlDiff = null;
    htmlDiff = HtmlDiffUtils.buildActualAsHtml(actual, expected);
    System.out.println(htmlDiff);
    htmlDiff = HtmlDiffUtils.buildExpectedAsHtml(actual, expected);
    System.out.println(htmlDiff);
  }

}
