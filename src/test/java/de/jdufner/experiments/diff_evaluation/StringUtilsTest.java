package de.jdufner.experiments.diff_evaluation;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class StringUtilsTest {

  private String s1 = "Hello World!";
  private String s2 = "Hallo Welt!";

  @Test
  public void test() {
    System.out.println(StringUtils.difference(s1, s2));
  }

}
