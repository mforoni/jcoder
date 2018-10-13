package com.github.mforoni.jcoder.util;

import java.io.File;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.github.mforoni.jbasic.io.JFiles;
import com.github.mforoni.jcoder.CodeGenerator;
import com.github.mforoni.jcoder.JHeader;
import com.github.mforoni.jspreadsheet.Spreadsheet;
import com.github.mforoni.jspreadsheet.Spreadsheets;

public final class CodeGenerators {
  private static final Logger LOGGER = LoggerFactory.getLogger(CodeGenerators.class);

  private CodeGenerators() {
    throw new AssertionError();
  }

  public enum Mode {
    INFER_TYPES, READ_TYPES
  }

  public static void fromSpreadsheet(final File file, final String sheetName,
      final String className, final String packageName, final Mode mode) throws IOException {
    LOGGER.info("Detecting header of file {}", file);
    try (final Spreadsheet spreadsheet = Spreadsheets.open(file)) {
      final JHeader header;
      switch (mode) {
        case INFER_TYPES:
          header = MoreSpreadsheets.inferHeader(spreadsheet, sheetName);
          break;
        case READ_TYPES:
          header = MoreSpreadsheets.buildHeader(spreadsheet, sheetName);
          break;
        default:
          throw new AssertionError();
      }
      header.print(LOGGER);
      final CodeGenerator codeGenerator = new CodeGenerator(className, packageName, header);
      codeGenerator.writeBean(JFiles.SRC_MAIN_JAVA);
      LOGGER.info("File {}.java successfully written at {}", className, packageName);
    }
  }

  public static void fromCsv(final File csv, final String className, final String packageName)
      throws IOException {
    // Preconditions.checkArgument(JFiles.is, errorMessageTemplate, p1);
    LOGGER.info("Detecting header of file {}", csv);
    final JHeader header = MoreCsv.inferHeader(new CsvReader(csv));
    header.print(LOGGER);
    final CodeGenerator codeGenerator = new CodeGenerator(className, packageName, header);
    codeGenerator.writeBean(JFiles.SRC_MAIN_JAVA);
    LOGGER.info("File {}.java successfully written at {}", className, packageName);
  }
}
