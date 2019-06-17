package com.softwareonpurpose.calibrator4test;

class Tally {
    private long tally;

    static Tally getInstance() {
        return new Tally();
    }

    void increment() {
        tally += 1;
    }

    long getTally() {
        return tally;
    }

    void reset() {
        tally = 0;
    }
}
