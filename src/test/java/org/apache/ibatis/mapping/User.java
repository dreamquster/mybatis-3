package org.apache.ibatis.mapping;

import org.apache.ibatis.submitted.integer_enum.IntegerEnumTest;

/**
 * Created by root on 15-4-29.
 */
public class User {
    private int id;

    private String firstName;

    private String lastName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
