package com.github.mforoni.jcoder.demo;

import static com.github.mforoni.jcoder.demo.StudentGenerator.PACKAGE;
import java.io.IOException;
import com.github.mforoni.jbasic.io.JFiles;
import com.github.mforoni.jcoder.util.CodeGenerators;

final class MusicGenerator {
  static final String MUSIC_CSV = "music.csv";
  private static final String CLASS_NAME = "Music";

  private MusicGenerator() {}

  public static void main(final String[] args) {
    try {
      CodeGenerators.fromCsv(JFiles.fromResource(MUSIC_CSV), CLASS_NAME, PACKAGE);
    } catch (final IOException ex) {
      ex.printStackTrace();
    }
  }
}
