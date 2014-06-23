package com.mokkoji.function;

public class MonthItem {
    
    private int dayValue;
    private String schedule;
 
    public MonthItem() {
         
       
    }
     
    public MonthItem(int day) {
        dayValue = day;
        schedule="";
    }
     
    public int getDay() {
        return dayValue;
    }
 
    public void setDay(int day) {
        this.dayValue = day;
    }
    
   
    public void addSchedule(String schedule){
       this.schedule=this.schedule+"\n"+schedule;
    }
    public String getSchedule(){
       return this.schedule;
    }
     
     
     
}