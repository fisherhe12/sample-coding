package com.github.fisherhe12.common.domain;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * @author fisher
 */
public class PersonDTO implements Serializable {



    public static String index = "1";

    private Integer age;

    private String name;

    public PersonDTO(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("age", age).add("name", name).toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(age, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PersonDTO) {
            PersonDTO that = (PersonDTO) obj;
            return Objects.equal(age, that.age) && Objects.equal(name, that.name);
        }
        return false;
    }


    public int getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
