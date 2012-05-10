package com.protocol7.frinton.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandLineParser {

  public static final String ARGUMENTS = "__ARGUMENTS__";

  public Map<String, List<String>> parse(String[] args) {
    Map<String, List<String>> options = new HashMap<String, List<String>>();


    List<String> lastOptionList = null;;

    for(String arg : args) {
      if(arg.startsWith("--")) {
        // long option
        arg = arg.substring(2);
        int eq = arg.indexOf('=');
        String value = null;
        if(eq > -1) {
          value = arg.substring(eq + 1);
          arg = arg.substring(0, eq);
        }

        List<String> values = options.get(arg);
        if(values == null) {
          values = new ArrayList<String>();
          options.put(arg, values);
        }

        values.add(value);
      } else if(arg.startsWith("-")) {
        // possibly multiple short options
        arg = arg.substring(1);
        for(char c : arg.toCharArray()) {
          String cs = Character.toString(c);
          List<String> values = options.get(cs);
          if(values == null) {
            values = new ArrayList<String>();

            options.put(cs, values);
          }
          // always add a null default value
          values.add(null);

          lastOptionList = values;
        }
      } else {
        if(lastOptionList != null) {
          // remove the default null value
          lastOptionList.remove(lastOptionList.size() - 1);
          lastOptionList.add(arg);
        } else {
          // argument

          List<String> arguments = options.get(ARGUMENTS);
          if(arguments == null) {
            arguments = new ArrayList<String>();
            options.put(ARGUMENTS, arguments);
          }
          arguments.add(arg);
        }

        lastOptionList = null;
      }
    }

    return options;
  }
}
