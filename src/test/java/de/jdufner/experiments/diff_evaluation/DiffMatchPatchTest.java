package de.jdufner.experiments.diff_evaluation;

import java.util.LinkedList;

import org.junit.Test;

import name.fraser.neil.plaintext.diff_match_patch;
import name.fraser.neil.plaintext.diff_match_patch.Diff;

public class DiffMatchPatchTest {

  private String s1 = "Hello World!";
  private String s2 = "Hallo Welt!";

  @Test
  public void test() {
    diff_match_patch dmp = new diff_match_patch();
    LinkedList<Diff> diff_main = dmp.diff_main(s1, s2);
    System.out.println(diff_main);
  }

}
