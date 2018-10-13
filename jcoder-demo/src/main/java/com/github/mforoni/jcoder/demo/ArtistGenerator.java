package com.github.mforoni.jcoder.demo;

import static com.github.mforoni.jcoder.demo.StudentGenerator.PACKAGE;
import java.io.IOException;
import com.github.mforoni.jbasic.io.JFiles;
import com.github.mforoni.jcoder.util.CodeGenerators;
import com.github.mforoni.jcoder.util.CodeGenerators.Mode;

final class ArtistGenerator {
  static final String ARTISTS_ODS = "artists.ods";
  static final String SHEET_NAME = "music";
  private static final String CLASS_NAME = "Artist";

  private ArtistGenerator() {}

  public static void main(final String[] args) {
    try {
      CodeGenerators.fromSpreadsheet(JFiles.fromResource(ARTISTS_ODS), SHEET_NAME, CLASS_NAME,
          PACKAGE, Mode.INFER_TYPES);
    } catch (final IOException ex) {
      ex.printStackTrace();
    }
  }
}
