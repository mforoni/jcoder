package com.github.mforoni.jcoder.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.github.mforoni.jbasic.reflect.JTypes;
import com.github.mforoni.jcoder.JField;
import com.github.mforoni.jcoder.JHeader;
import com.github.mforoni.jcoder.JHeader.Origin;
import com.github.mforoni.jcoder.JHeader.ParserType;
import com.github.mforoni.jcoder.Names;
import com.google.common.base.Optional;

final class Parser {
  private Parser() {
    throw new AssertionError();
  }

  static class Objects {
    private final List<MutableField> fields = new ArrayList<>();
    private final String filepath;

    Objects(final String filepath) {
      this.filepath = filepath;
    }

    private void addFields(final Object[] fields) {
      if (this.fields.size() > 0) {
        throw new IllegalStateException();
      }
      for (int i = 0; i < fields.length; i++) {
        final String str = String.valueOf(fields[i]);
        this.fields.add(new MutableField(Names.ofField(str)));
      }
    }

    public void computeRow(final Object[] values, final int row) {
      if (row == 0) {
        addFields(values);
      } else {
        final int headerSize = fields.size();
        if (values.length > headerSize) {
          throw new IllegalStateException(String.format(
              "Number of values (%d) not consistent with header in first line (%d) at line %d\nrow=%s",
              values.length, headerSize, row, Arrays.asList(values)));
        }
        try {
          for (int i = 0; i < values.length; i++) {
            final MutableField field = fields.get(i);
            if (values[i] != null) {
              final Class<?> type = values[i].getClass();
              field.setType(type);
            } else {
              field.setNullable(true);
            }
          }
        } catch (final Throwable t) {
          throw new IllegalStateException(
              "Something unexpected at row " + row + ": " + Arrays.asList(values), t);
        }
      }
    }

    JHeader buildHeader() {
      if (this.fields.size() == 0) {
        throw new IllegalStateException();
      }
      final List<JField> fields = new ArrayList<>();
      for (final MutableField mf : this.fields) {
        fields.add(mf.toJField());
      }
      return new JHeader(fields, Optional.of(new Origin(filepath, ParserType.OBJECTS)));
    }
  }
  static class Strings {
    private final List<MutableField> fields = new ArrayList<>();
    private final String filepath;

    Strings(final String filepath) {
      this.filepath = filepath;
    }

    private void addFields(final String[] fields) {
      if (this.fields.size() > 0) {
        throw new IllegalStateException();
      }
      int counter = 1;
      for (int i = 0; i < fields.length; i++) {
        final String s =
            com.google.common.base.Strings.isNullOrEmpty(fields[i]) ? "field" + counter++
                : fields[i];
        this.fields.add(new MutableField(Names.ofField(s)));
      }
    }

    public void computeRow(final String[] values, final int row) {
      if (row == 0) {
        addFields(values);
      } else {
        final int headerSize = fields.size();
        if (values.length != headerSize) {
          throw new IllegalStateException(String.format(
              "Number of values (%d) not consistent with header in first line (%d) at line %d\nrow=%s",
              values.length, headerSize, row, Arrays.asList(values)));
        }
        try {
          for (int i = 0; i < values.length; i++) {
            final MutableField field = fields.get(i);
            if (!com.google.common.base.Strings.isNullOrEmpty(values[i])) {
              final Class<?> type = Helper.inferType(values[i]);
              // LOGGER.debug("Value '{}' infered type {}", values[i], type);
              field.inferCommonType(type);
            } else {
              field.setNullable(true);
            }
          }
        } catch (final Throwable t) {
          throw new IllegalStateException(
              "Something unexpected at row " + row + ": " + Arrays.asList(values), t);
        }
      }
    }

    public JHeader buildHeader() {
      if (this.fields.size() == 0) {
        throw new IllegalStateException();
      }
      final List<JField> fields = new ArrayList<>();
      for (final MutableField mf : this.fields) {
        fields.add(mf.toJField());
      }
      return new JHeader(fields, Optional.of(new Origin(filepath, ParserType.STRINGS)));
    }
  }
  private static class MutableField {
    private final String name;
    private Class<?> type;
    private boolean nullable;

    MutableField(final String name) {
      this(name, null);
    }

    MutableField(final String name, final Class<?> type) {
      this.name = name;
      this.type = type;
      nullable = false;
    }

    public void setNullable(final boolean value) {
      nullable = value;
    }

    public void setType(final Class<?> type) {
      if (this.type == null || this.type == type) {
        this.type = type;
      } else {
        throw new IllegalStateException("Type can't change from " + this.type + " to " + type);
      }
    }

    public void inferCommonType(final Class<?> newType) {
      if (type == null) {
        type = newType;
      } else if (newType != null) {
        type = Helper.inferCommonType(type, newType);
      }
    }

    public JField toJField() {
      if (type == null) {
        type = String.class;
      }
      if (nullable && type.isPrimitive()) {
        throw new IllegalStateException();
      }
      if (!nullable && JTypes.isPrimitiveOrPrimitiveWrapper(type)) {
        type = JTypes.toPrimitive(type);
      }
      return new JField(name, type, nullable);
    }
  }
}
