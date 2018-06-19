package com.github.fisherhe12.common.domain;

import com.google.common.base.Objects;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Player {

    private int number;

    private String name;

    public Player() {
    }

    public Player(String name, int number) {
        this.name = name;
        this.number = number;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number, name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player that = (Player) obj;
            return Objects.equal(number, that.number) && Objects.equal(name, that.name);
        }
        return false;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
