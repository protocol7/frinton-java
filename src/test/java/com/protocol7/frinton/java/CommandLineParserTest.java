package com.protocol7.frinton.java;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;

public class CommandLineParserTest extends TestCase {

  @Test
  public void testLongOption() {
    CommandLineParser parser = new CommandLineParser();
    Map<String, List<String>> actual = parser.parse(new String[]{"--foo=val", "--bar", "--baz="});
    Map<String, List<String>> expected = new HashMap<String, List<String>>();
    expected.put("foo", Arrays.asList("val"));
    expected.put("bar", Arrays.asList((String)null));
    expected.put("baz", Arrays.asList(""));

    assertEquals(expected, actual);
  }

  @Test(expected=IllegalArgumentException.class)
  public void testLongOptionInvalidOption() {
    CommandLineParser parser = new CommandLineParser();
    parser.parse(new String[]{"--=val", "--bar"});
  }

  @Test
  public void testShortOption() {
    CommandLineParser parser = new CommandLineParser();
    Map<String, List<String>> actual = parser.parse(new String[]{"-aabc", "foo"});
    Map<String, List<String>> expected = new HashMap<String, List<String>>();
    expected.put("a", Arrays.asList((String)null, (String)null));
    expected.put("b", Arrays.asList((String)null));
    expected.put("c", Arrays.asList("foo"));

    assertEquals(expected, actual);
  }

  @Test
  public void testArguments() {
    CommandLineParser parser = new CommandLineParser();
    Map<String, List<String>> actual = parser.parse(new String[]{"-c", "foo", "bar"});
    Map<String, List<String>> expected = new HashMap<String, List<String>>();
    expected.put("c", Arrays.asList("foo"));
    expected.put(CommandLineParser.ARGUMENTS, Arrays.asList("bar"));

    assertEquals(expected, actual);
  }


}
