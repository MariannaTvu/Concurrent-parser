package com.mariana.presentation;

import com.mariana.model.ValueCount;

import java.util.Objects;

/**
 * @author nicot
 */
public class ValueCountDto {

    private final String value;
    private final int count;

    public ValueCountDto(String value, int count) {
        this.value = value;
        this.count = count;
    }

    public ValueCountDto(ValueCount valueCount) {
        this(valueCount.getValue(), valueCount.getCount());
    }

    public String getValue() {
        return value;
    }

    public int getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueCountDto that = (ValueCountDto) o;
        return count == that.count &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, count);
    }

    @Override
    public String toString() {
        return "WordCountDto{" +
                "value='" + value + '\'' +
                ", count=" + count +
                '}';
    }
}
