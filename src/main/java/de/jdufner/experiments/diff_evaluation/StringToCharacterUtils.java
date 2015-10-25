package de.jdufner.experiments.diff_evaluation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StringToCharacterUtils {

  public static List<Character> stringAsCharacterList(final String s) {
    if (s == null || s.isEmpty()) {
      return Collections.<Character> emptyList();
    }
    List<Character> characterList = new ArrayList<Character>(s.length());
    for (char c : s.toCharArray()) {
      characterList.add(c);
    }
    return characterList;
  }

}
