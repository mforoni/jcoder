package com.github.mforoni.jcoder.util;

import java.util.Date;
import javax.annotation.Nullable;
import com.github.mforoni.jbasic.time.JLocalDates;
import com.google.common.annotations.Beta;

/**
 * @author Foroni Marco
 */
@Beta
final class Helper {
  private Helper() {
    throw new AssertionError();
  }

  public static Class<?> inferCommonType(final Class<?> type1, final Class<?> type2) {
    if (type1.equals(String.class) || type2.equals(String.class)) {
      return String.class;
    } else if (type1.equals(Date.class)) {
      if (type2.equals(Date.class)) {
        return Date.class;
      } else if (type2.equals(Boolean.class)) {
        return String.class;
      } else if (type2.equals(Integer.class)) {
        return String.class;
      } else if (type2.equals(Double.class)) {
        return String.class;
      }
    } else if (type1.equals(Boolean.class)) {
      if (type2.equals(Date.class)) {
        return String.class;
      } else if (type2.equals(Boolean.class)) {
        return Boolean.class;
      } else if (type2.equals(Integer.class)) {
        return Integer.class;
      } else if (type2.equals(Double.class)) {
        return Double.class;
      }
    } else if (type1.equals(Integer.class)) {
      if (type2.equals(Date.class)) {
        return String.class;
      } else if (type2.equals(Boolean.class)) {
        return Integer.class;
      } else if (type2.equals(Integer.class)) {
        return Integer.class;
      } else if (type2.equals(Double.class)) {
        return Double.class;
      }
    } else if (type1.equals(Double.class)) {
      if (type2.equals(Date.class)) {
        return String.class;
      } else if (type2.equals(Boolean.class)) {
        return Double.class;
      } else if (type2.equals(Integer.class)) {
        return Double.class;
      } else if (type2.equals(Double.class)) {
        return Double.class;
      }
    }
    throw new IllegalStateException("Case not handled: type1=" + type1 + ", type2=" + type2);
  }

  @Nullable
  public static Class<?> inferType(@Nullable final String value) {
    if (value == null || value.length() == 0) {
      return null;
    } else if (value.length() == 1 && (value.charAt(0) == '0' || value.charAt(0) == '1')) {
      return Boolean.class;
    } else {
      if (JLocalDates.isParsable(value)) {
        return Date.class;
      } else {
        try {
          Integer.parseInt(value);
          return Integer.class;
        } catch (final NumberFormatException e) {
          // Ignored
        }
        try {
          Double.parseDouble(value);
          return Double.class;
        } catch (final NumberFormatException e) {
          // Ignored
        }
        return String.class;
      }
    }
  }
}
