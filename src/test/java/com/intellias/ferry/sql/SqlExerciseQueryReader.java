package com.intellias.ferry.sql;

import java.nio.file.Files;
import java.nio.file.Paths;

public class SqlExerciseQueryReader {

  public static String queryStr(String dirname) {
    try {
      var path = Paths.get(SqlExerciseQueryReader.class.getClassLoader().getResource(dirname +"/query.sql").toURI());
      return Files.readString(path).replace(";", "");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
