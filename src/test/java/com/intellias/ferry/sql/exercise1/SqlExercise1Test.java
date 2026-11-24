package com.intellias.ferry.sql.exercise1;

import com.intellias.ferry.sql.SqlExerciseQueryReader;
import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;


public class SqlExercise1Test extends DBSuite1 {

  @Test
  public void whenSelectQuery_thenResultsMatchExpectedIgnoringColumns() throws Exception {
    String[] excludedColumns = {};

    try (InputStream is = getClass().getClassLoader().getResourceAsStream("sql1/expected.xml")) {
      IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
      ITable expectedTable =
          DefaultColumnFilter.excludedColumnsTable(expectedDataSet.getTable("ITEMS"), excludedColumns);

      var query = SqlExerciseQueryReader.queryStr("sql1");
      Assertions.assertFalse(query.isEmpty(), "The query is empty!");
      Assertions.assertFalse(query.contains("Girona"),
          "Nice try, but no shortcuts allowed! 😜 Let's give the exercise an honest shot!");

      ITable actualTable = dbUnitConnection.createQueryTable("RESULTS", String.format(
          "select island_name, total_teu, port_count, round(avg_teu_per_port, 2) as avg_teu_per_port "
              + "from (%1$s) order by island_name",
          query));

      ITable filteredActualTable = DefaultColumnFilter.excludedColumnsTable(actualTable, excludedColumns);

      Assertion.assertEquals(expectedTable, filteredActualTable);
    }
  }

}
