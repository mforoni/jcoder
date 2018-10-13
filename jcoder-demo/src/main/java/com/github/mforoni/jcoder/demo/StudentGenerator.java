package com.github.mforoni.jcoder.demo;

import java.io.IOException;
import com.github.mforoni.jbasic.io.JFiles;
import com.github.mforoni.jcoder.util.CodeGenerators;

/**
 * 
 * @author Foroni Marco
 *
 */
final class StudentGenerator {
  static final String STUDENT_CSV = "student.csv";
  static final String PACKAGE = "com.github.mforoni.jcoder.demo.generated";

  private StudentGenerator() {}

  public static void main(final String[] args) {
    try {
      CodeGenerators.fromCsv(JFiles.fromResource(STUDENT_CSV), "Student", PACKAGE);
    } catch (final IOException ex) {
      ex.printStackTrace();
    }
  }
}
