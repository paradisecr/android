package com.example.CourseInfo;

public class Course {
	private String name = null;
	private String id = null;
	private boolean isChanged = false;
	private boolean isChecked = false;
	
	public Course(String id,String name){
		this.name = name;
		this.id = id;
	}
	
	public Course(String id,String name,boolean isChecked){
		this.name = name;
		this.id = id;
		this.isChecked = isChecked;
	}
	public boolean isChanged(){
		return isChanged;
	}
	
	public boolean isChecked(){
		return isChecked;
	}
	
	public void check(){
		isChanged  = !isChanged;
		isChecked =  !isChecked;
	}
	
	public String getName(){
		return name;
	}
}
