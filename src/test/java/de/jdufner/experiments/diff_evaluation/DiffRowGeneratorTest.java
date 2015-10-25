package de.jdufner.experiments.diff_evaluation;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import difflib.DiffRow;
import difflib.DiffRowGenerator;

public class DiffRowGeneratorTest {

  private String s1 = "Hello World!";
  private String s2 = "Hallo Welt!";

  @Test
  public void testString() {
    DiffRowGenerator dfg = new DiffRowGenerator.Builder().build();
    List<DiffRow> diffRowList = dfg.generateDiffRows(Arrays.asList(s1), Arrays.asList(s2));
    System.out.println(diffRowList);
  }

}
