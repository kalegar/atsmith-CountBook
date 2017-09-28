/*
 * Counter
 *
 * Version 1
 *
 * September 26, 2017
 *
 * Copyright 2017 Andrew Smith, CMPUT 301, University of Alberta.
 * All Rights Reserved. This code can be used, modified, and distributed in
 * accordance with the Code of Student Behaviour at University of Alberta.
 * A copy of the license can be found in this project.
 * Otherwise, contact atsmith@ualberta.ca
 */

package com.atsmith.countbook;

import java.util.Date;

/**
 * Represents a counter.
 *
 * @author atsmith
 * @version 1.0
 */
public class Counter {
    private String name;
    private Date date;
    private int value;
    private int initalValue;
    private String comment;

    /**
     * Constructor for Counter
     * Date is set to current date as of Counter creation.
     *
     * @param name counter name
     * @param initialValue counter initial value
     * @param comment counter comment (null or empty string for no comment)
     * @throws NegativeValueException If initialValue < 0
     */
    public Counter(String name, int initialValue, String comment) throws NegativeValueException{
        if (initialValue < 0){
            throw new NegativeValueException();
        }
        this.name = name;
        this.initalValue = initialValue;
        this.value = initialValue;
        this.date = new Date();
        if (comment != null && comment.length() > 0) {
            this.comment = comment;
        } else {
            comment = "";
        }
    }

    public int getValue(){
        return value;
    }

    public int getInitalValue(){
        return initalValue;
    }

    public String getName(){
        return name;
    }

    public String getComment(){
        if (comment==null){
            return "";
        }else{
            return comment;
        }
    }

    public void incValue(){
        this.value++;
        this.date = new Date();
    }

    public void decValue(){
        if (this.value>0){
            this.value--;
            this.date = new Date();
        }
    }

    public void resetValue(){
        this.value = this.initalValue;
        this.date = new Date();
    }

    public void setName(String name){
        this.name = name;
    }

    public void setComment(String comment){
        this.comment = comment;
    }

    public void setInitalValue(int initialValue){
        this.initalValue = initialValue;
    }

    public Date getDate(){
        return date;
    }
}
