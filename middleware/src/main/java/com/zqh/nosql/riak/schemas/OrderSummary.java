package com.zqh.nosql.riak.schemas;

import java.util.ArrayList;

public class OrderSummary {

    public OrderSummary() {
        Summaries= new ArrayList<OrderSummaryItem>();
    }
    public long CustomerId;
    public ArrayList<OrderSummaryItem> Summaries;
}
