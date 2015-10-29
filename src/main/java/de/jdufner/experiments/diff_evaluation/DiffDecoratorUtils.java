package de.jdufner.experiments.diff_evaluation;

import java.util.LinkedList;
import java.util.List;

import difflib.Chunk;
import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;

public class DiffDecoratorUtils {

  /**
   * It's better to use {@link String} constants as default values?
   *
   * @author Jürgen
   */
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

  /**
   * Is replacement by {@link StringBuilder} possible?
   *
   * @author Jürgen
   */
  private static class CharacterContainer {
    private final Character c;
    private final HtmlTag t;

    private CharacterContainer(final Character c, final HtmlTag t) {
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

  private static abstract class Builder {
    protected final Patch<Character> patch;
    protected final List<CharacterContainer> ccList = new LinkedList<CharacterContainer>();
    protected boolean isDelta = false;
    protected List<Character> text;

    protected Builder(final String actual, final String expected) {
      this.patch = DiffUtils.diff(StringToCharacterUtils.stringToCharacterList(actual),
          StringToCharacterUtils.stringToCharacterList(expected));
    }

    protected String build() {
      iterate();
      return buildString();
    }

    protected abstract List<Character> getText();

    protected abstract void iterate();

    protected abstract Chunk<Character> getChunk(Delta<Character> d);

    protected abstract boolean isFirst(int i);

    protected abstract boolean isLast(int i);

    protected abstract void doAddOpeningTag();

    protected abstract void doAddClosingTag();

    protected abstract String buildString();

    protected void addElement(final int i) {
      Delta<Character> delta = getDeltaByIndex(i, patch);
      addOpeningTagIfNecessary(i, delta);
      if (delta == null) {
        if (isDelta) {
          addClosingTag();
        }
        ccList.add(new CharacterContainer(getText().get(i), null));
      } else {
        if (!isDelta) {
          addOpeningTag();
        }
        ccList.add(new CharacterContainer(getByIndex(getChunk(delta), i), null));
      }
      addClosingTagIfNecessary(i, delta);
    }

    private void addClosingTagIfNecessary(final int i, final Delta<Character> delta) {
      if (delta != null && isLast(i)) {
        addClosingTag();
      }
    }

    private void addClosingTag() {
      isDelta = false;
      doAddClosingTag();
    }

    private void addOpeningTagIfNecessary(final int i, final Delta<Character> delta) {
      if (delta != null && isFirst(i)) {
        addOpeningTag();
      }
    }

    private void addOpeningTag() {
      isDelta = true;
      doAddOpeningTag();
    }

    private Delta<Character> getDeltaByIndex(final int i, final Patch<Character> patch) {
      for (Delta<Character> delta : patch.getDeltas()) {
        if (isInChunk(getChunk(delta), i)) {
          return delta;
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

  private static class ActualBuilder extends Builder {

    protected ActualBuilder(final String actual, final String expected) {
      super(actual, expected);
      text = StringToCharacterUtils.stringToCharacterList(actual);
    }

    @Override
    protected List<Character> getText() {
      return text;
    }

    @Override
    protected void iterate() {
      for (int i = 0; i < getText().size(); i++) {
        addElement(i);
      }
    }

    @Override
    protected Chunk<Character> getChunk(final Delta<Character> d) {
      return d.getOriginal();
    }

    @Override
    protected boolean isFirst(final int i) {
      return i == 0;
    }

    @Override
    protected boolean isLast(final int i) {
      return i == getText().size() - 1;
    }

    @Override
    protected void doAddOpeningTag() {
      ccList.add(new CharacterContainer(null, HtmlTag.OPEN));
    }

    @Override
    protected void doAddClosingTag() {
      ccList.add(new CharacterContainer(null, HtmlTag.CLOSE));
    }

    @Override
    protected String buildString() {
      StringBuilder sb = new StringBuilder(ccList.size());
      for (CharacterContainer cc : ccList) {
        sb.append(cc.toString());
      }
      return sb.toString();
    }

  }

  private static class ExpectedBuilder extends ActualBuilder {

    protected ExpectedBuilder(final String actual, final String expected) {
      super(actual, expected);
      text = StringToCharacterUtils.stringToCharacterList(expected);
    }

    @Override
    protected void iterate() {
      for (int i = getText().size() - 1; i >= 0; i--) {
        addElement(i);
      }
    }

    @Override
    protected Chunk<Character> getChunk(final Delta<Character> d) {
      return d.getRevised();
    }

    @Override
    protected boolean isFirst(final int i) {
      return super.isLast(i);
    }

    @Override
    protected boolean isLast(final int i) {
      return super.isFirst(i);
    }

    @Override
    protected void doAddOpeningTag() {
      super.doAddClosingTag();
    }

    @Override
    protected void doAddClosingTag() {
      super.doAddOpeningTag();
    }

    @Override
    protected String buildString() {
      StringBuilder sb = new StringBuilder(ccList.size());
      for (CharacterContainer cc : ccList) {
        sb.insert(0, cc.toString());
      }
      return sb.toString();
    }

  }

  public static String buildActual(final String actual, final String expected) {
    Builder ab = new ActualBuilder(actual, expected);
    return ab.build();
  }

  public static String buildExpected(final String actual, final String expected) {
    Builder eb = new ExpectedBuilder(actual, expected);
    return eb.build();
  }

}
