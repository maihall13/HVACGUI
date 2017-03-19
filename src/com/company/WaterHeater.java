package com.company;

import java.util.Date;

/**
 * Created by Maia on 3/19/2017.
 */
public class WaterHeater extends ServiceCall {

    private double age;
    static double upcharge = 20.00;

    public WaterHeater(String serviceAddress, String problemDescription, Date date, double age) {
        super(serviceAddress, problemDescription, date);
        this.age = age;
    }

    public Double getAge() {
        return age;
    }

    public void setAge(Double age) {
        this.age = age;
    }


    @Override
    public String toString() {

        //These statements use the Java ternary operator
        //This says "if resolvedDate == null, then set resolvedDate to "Unresolved". Otherwise, set resolvedDate to resolvedDate.toString()
        //Same logic as an if-else statement, but more concise if the condition is simple and the if statement's task is
        //to set the value of a variable based on a condition being true or false.
        String resolvedDateString = (resolvedDate == null) ? "Unresolved" : this.resolvedDate.toString();
        String resolutionString = (this.resolution == null) ? "Unresolved" : this.resolution;
        String feeString = (fee == UNRESOLVED) ? "Unresolved" : "$" + Double.toString(fee);
        String upchargeString = (fee == UNRESOLVED) ? "Unresolved" : "$" + Double.toString(upcharge);
        String totalFeeString = (fee == UNRESOLVED) ? "Unresolved" : "$" + Double.toString(fee + upcharge);



        return "Water Heater Service Call " + "\n" +
                "Service Address= " + serviceAddress + "\n" +
                "Problem Description = " + problemDescription + "\n" +
                "Age of Water Heater = " + age + "\n" +
                "Reported Date = " + reportedDate + "\n" +
                "Resolved Date = " + resolvedDateString + "\n" +
                "Resolution = " + resolutionString + "\n" +
                "Initial Fee = " + feeString + "\n" +
                "City Up-charge = " + upchargeString + "\n" +
                "Total Fee = " + totalFeeString;

    }
}
