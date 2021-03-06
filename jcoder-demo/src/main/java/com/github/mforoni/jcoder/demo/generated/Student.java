package com.github.mforoni.jcoder.demo.generated;

import java.io.Serializable;
import org.supercsv.cellprocessor.ParseInt;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;

/**
 * Auto generated from file student.csv
 */
public class Student implements Serializable {
  public static final CellProcessor[] CELL_PROCESSOR = { //
      new NotNull(new ParseInt()), // id
      new NotNull(), // firstname
      new NotNull(), // lastname
      new NotNull(), // gender
      new NotNull(new ParseInt()), // age
  };
  private int id;
  private String firstname;
  private String lastname;
  private String gender;
  private int age;

  public Student() {}

  public int getId() {
    return id;
  }

  public void setId(final int id) {
    this.id = id;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(final String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(final String lastname) {
    this.lastname = lastname;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(final String gender) {
    this.gender = gender;
  }

  public int getAge() {
    return age;
  }

  public void setAge(final int age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return String.format("Student [id=%s, firstname=%s, lastname=%s, gender=%s, age=%s]", id,
        firstname, lastname, gender, age);
  }
}
