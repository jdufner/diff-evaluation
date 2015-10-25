package de.jdufner.experiments.diff_evaluation;

import java.util.Arrays;

import org.junit.Test;

import difflib.Chunk;
import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

public class DiffUtilsTest {

  private String s1 = "Hello World!";
  private String s2 = "Hallo Welt!";

  @Test
  public void testString() {
    Patch<String> patch = DiffUtils.diff(Arrays.asList(s1), Arrays.asList(s2));
    System.out.println("Compare String: " + patch.getDeltas());
  }

  @Test
  public void testCharacterList1() {
    Patch<Character> patch = DiffUtils.diff(StringToCharacterUtils.stringAsCharacterList(s1),
        StringToCharacterUtils.stringAsCharacterList(s2));
    System.out.println("Compare Characters: " + patch.getDeltas());
  }

  @Test
  public void testCharacterList2() {
    Patch<Character> patch = DiffUtils.diff(StringToCharacterUtils.stringAsCharacterList(s1),
        StringToCharacterUtils.stringAsCharacterList(s2));
    printOriginal(patch);
    System.out.println("##########");
    printRevised(patch);
  }

  private void printOriginal(final Patch<Character> patch) {
    for (int i = 0; i < s1.length(); i++) {
      System.out.print(i + ": ");
      Delta<Character> d = getOriginalDeltaByIndex(patch, i);
      if (d == null) {
        System.out.println(s1.charAt(i));
      } else {
        System.out.println("[" + getByIndex(d.getOriginal(), i) + "]");
      }
    }
  }

  private void printRevised(final Patch<Character> patch) {
    for (int i = s2.length() - 1; i >= 0; i--) {
      System.out.print(i + ": ");
      Delta<Character> d = getRevisedDeltaByIndex(patch, i);
      if (d == null) {
        System.out.println(s2.charAt(i));
      } else {
        System.out.println("[" + getByIndex(d.getRevised(), i) + "]");
      }
    }
  }

  private Delta<Character> getOriginalDeltaByIndex(final Patch<Character> patch, final int i) {
    for (Delta<Character> d : patch.getDeltas()) {
      if (isInChunk(d.getOriginal(), i)) {
        return d;
      }
    }
    return null;
  }

  private Delta<Character> getRevisedDeltaByIndex(final Patch<Character> patch, final int i) {
    for (Delta<Character> d : patch.getDeltas()) {
      if (isInChunk(d.getRevised(), i)) {
        return d;
      }
    }
    return null;
  }

  private boolean isInChunk(final Chunk<Character> chunk, final int i) {
    return i >= chunk.getPosition() && i < chunk.getPosition() + chunk.size();
  }

  private Character getByIndex(final Chunk<Character> chunk, final int i) {
    int j = i - chunk.getPosition();
    return chunk.getLines().get(j);
  }

}
