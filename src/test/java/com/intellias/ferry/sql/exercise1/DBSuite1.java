package com.intellias.ferry.sql.exercise1;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;

public class DBSuite1 {
  protected static String schemaFile = "sql1/schema.sql";
  protected static String datasetDir = "src/test/resources/sql1";

  protected static final String JDBC_DRIVER = org.h2.Driver.class.getName();
  protected static final String USER = "sa";
  protected static final String PASSWORD = "sa";
  protected static final String JDBC_URL =
      String.format("jdbc:h2:mem:default;MODE=LEGACY;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:%1$s'",
          schemaFile);
  private static IDatabaseTester tester = null;
  protected IDatabaseConnection dbUnitConnection;

  @BeforeAll
  public static void setUp() throws Exception {
    tester = initDatabaseTester();
  }

  private static IDatabaseTester initDatabaseTester() throws Exception {
    JdbcDatabaseTester tester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
    tester.setDataSet(initDataSet());
    tester.setSetUpOperation(DatabaseOperation.REFRESH);
    tester.setTearDownOperation(DatabaseOperation.TRUNCATE_TABLE);
    return tester;
  }

  private static IDataSet initDataSet() throws Exception {
    var dir = new File(datasetDir);
    return new CsvDataSet(dir);
  }

  @BeforeEach
  public void setup() throws Exception {
    tester.onSetup();
    dbUnitConnection = tester.getConnection();
  }

  @AfterEach
  public void tearDown() throws Exception {
    tester.onTearDown();
  }
}
