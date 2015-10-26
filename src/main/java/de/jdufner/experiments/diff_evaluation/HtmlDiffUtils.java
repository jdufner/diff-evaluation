package de.jdufner.experiments.diff_evaluation;

import java.util.LinkedList;
import java.util.List;

import difflib.Chunk;
import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

public class HtmlDiffUtils {

  public enum HtmlTag {

    OPEN("<span class=\"diff\">"), CLOSE("</span>");

    private String text;

    private HtmlTag(final String text) {
      this.text = text;
    }

    public String getText() {
      return text;
    }
  }

  public static class CharacterContainer {
    private final Character c;
    private final HtmlTag t;

    public CharacterContainer(final Character c, final HtmlTag t) {
      if ((c == null && t == null) || (c != null && t != null)) {
        throw new IllegalStateException("Exactly one of both parameters must be null.");
      }
      this.c = c;
      this.t = t;
    }

    @Override
    public String toString() {
      if (c != null) {
        return c.toString();
      }
      return t.getText();
    }

  }

  public static String buildActualAsHtml(final String actual, final String expected) {
    Patch<Character> patch = DiffUtils.diff(StringToCharacterUtils.stringToCharacterList(actual),
        StringToCharacterUtils.stringToCharacterList(expected));
    List<CharacterContainer> ccList = new LinkedList<CharacterContainer>();
    boolean isDelta = false;
    for (int i = 0; i < actual.length(); i++) {
      Delta<Character> delta = getOriginalDeltaByIndex(patch, i);
      if (delta == null) {
        if (isDelta) {
          isDelta = false;
          ccList.add(new CharacterContainer(null, HtmlTag.CLOSE));
        }
        ccList.add(new CharacterContainer(actual.charAt(i), null));
      } else {
        if (!isDelta) {
          isDelta = true;
          ccList.add(new CharacterContainer(null, HtmlTag.OPEN));
        }
        ccList.add(new CharacterContainer(getByIndex(delta.getOriginal(), i), null));
      }
    }
    StringBuilder sb = new StringBuilder(ccList.size());
    for (CharacterContainer cc : ccList) {
      sb.append(cc.toString());
    }
    return sb.toString();
  }

  public static String buildExpectedAsHtml(final String actual, final String expected) {
    Patch<Character> patch = DiffUtils.diff(StringToCharacterUtils.stringToCharacterList(actual),
        StringToCharacterUtils.stringToCharacterList(expected));
    List<CharacterContainer> ccList = new LinkedList<CharacterContainer>();
    boolean isDelta = false;
    for (int i = expected.length() - 1; i >= 0; i--) {
      Delta<Character> delta = getRevisedDeltaByIndex(patch, i);
      if (delta == null) {
        if (isDelta) {
          isDelta = false;
          ccList.add(new CharacterContainer(null, HtmlTag.OPEN));
        }
        ccList.add(new CharacterContainer(expected.charAt(i), null));
      } else {
        if (!isDelta) {
          isDelta = true;
          ccList.add(new CharacterContainer(null, HtmlTag.CLOSE));
        }
        ccList.add(new CharacterContainer(getByIndex(delta.getRevised(), i), null));
      }
    }
    StringBuilder sb = new StringBuilder(ccList.size());
    for (CharacterContainer cc : ccList) {
      sb.insert(0, cc.toString());
    }
    return sb.toString();
  }

  private static Delta<Character> getOriginalDeltaByIndex(final Patch<Character> patch, final int i) {
    for (Delta<Character> d : patch.getDeltas()) {
      if (isInChunk(d.getOriginal(), i)) {
        return d;
      }
    }
    return null;
  }

  private static Delta<Character> getRevisedDeltaByIndex(final Patch<Character> patch, final int i) {
    for (Delta<Character> d : patch.getDeltas()) {
      if (isInChunk(d.getRevised(), i)) {
        return d;
      }
    }
    return null;
  }

  private static boolean isInChunk(final Chunk<Character> chunk, final int i) {
    return i >= chunk.getPosition() && i < chunk.getPosition() + chunk.size();
  }

  private static Character getByIndex(final Chunk<Character> chunk, final int i) {
    int j = i - chunk.getPosition();
    return chunk.getLines().get(j);
  }

}
