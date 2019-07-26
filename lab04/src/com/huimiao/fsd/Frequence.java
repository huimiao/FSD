package com.huimiao.fsd;

public enum Frequence {
    QUERTERLY(3),
    HALF_YEARLY(6),
    YEARLY(12);

    private int value;

    Frequence(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
