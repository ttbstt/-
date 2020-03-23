package com.example.myapplication;

public class Thing {
    private int id;
    private String name;
    private int imageId;
    private String number;
    private String flag;

    public Thing(String name,int id){
        this.name=name;
        this.imageId=id;
    }
    public Thing(String name,String flag){
        this.name=name;
        this.flag=flag;
    }
    public void setId(int id){this.id=id;}
    public void setName(String name) { this.name = name;    }
    public void setNumber(String number){
        this.number=number;
    }
    public void setFlag(String flag){this.flag=flag;}
    public int getId(){return id;}
    public String getNumber(){
        return number;
    }
    public String getFlag(){return flag;}
    public String getName(){
        return name;
    }
    public int getImageId(){ return imageId; }
}
