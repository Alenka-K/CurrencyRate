package com.example.currencyrate.model;


public class CurrencyRate {
    String currencyCode;
    float value;

    public CurrencyRate() {
    }

    public CurrencyRate(String currencyCode, float value) {
        this.currencyCode = currencyCode;
        this.value = value;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public float getValue() {
        return value;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setValue(float value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrencyRate that = (CurrencyRate) o;

        if (Float.compare(that.value, value) != 0) return false;
        return currencyCode != null ? currencyCode.equals(that.currencyCode) : that.currencyCode == null;
    }

    @Override
    public int hashCode() {
        int result = currencyCode != null ? currencyCode.hashCode() : 0;
        result = 31 * result + (value != +0.0f ? Float.floatToIntBits(value) : 0);
        return result;
    }

    @Override
    public String toString() {
        return "CurrencyRate{" +
                "currencyCode='" + currencyCode + '\'' +
                ", value=" + value +
                '}';
    }
}
