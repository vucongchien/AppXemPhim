package com.example.appxemphim.Utilities;

import com.google.firebase.Timestamp;

import java.sql.Time;

public class TimeWrapper {
    long seconds;
    int nanos;
    public Timestamp toTimestamp() { return new Timestamp(seconds, nanos); }
}
