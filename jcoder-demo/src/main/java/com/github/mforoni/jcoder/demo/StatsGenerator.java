package com.github.mforoni.jcoder.demo;

import static com.github.mforoni.jcoder.demo.StudentGenerator.PACKAGE;
import java.io.IOException;
import com.github.mforoni.jbasic.io.JFiles;
import com.github.mforoni.jcoder.util.CodeGenerators;
import com.github.mforoni.jcoder.util.CodeGenerators.Mode;

final class StatsGenerator {
  static final String STATS_XLSX = "Stats.xlsx";
  static final String SHEET_NAME = "Tutti";

  private StatsGenerator() {}

  public static void main(final String[] args) {
    try {
      CodeGenerators.fromSpreadsheet(JFiles.fromResource(STATS_XLSX), SHEET_NAME, "Stats", PACKAGE,
          Mode.INFER_TYPES);
    } catch (final IOException ex) {
      ex.printStackTrace();
    }
  }
}
