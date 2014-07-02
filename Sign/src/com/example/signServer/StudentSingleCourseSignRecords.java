package com.example.signServer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import android.test.IsolatedContext;

import com.example.sign.HistorySignActivity;

public class StudentSingleCourseSignRecords {
	private String courseTime = null; //服务器端课程上课周时数据（格式为：1-n周）
	private String weekTime = null; //服务器端课程每周上课时间数据（格式为：150;251）
	private int currentWeek = 0;  //现在的周数
	private int currentTime = 0;        //现在周几、、、
	private String courseSignHistory[] = null;   //签到记录
	
	private int startWeek = 0;    //起始周
	private int endWeek = 0;      //终结周	
	
	private int timeType[] = null; //记录每种上课时间（如单周每周一三四节为：111）
	private int timeTypeNum = 0; //记录上课时间有几种（同timeType[]联系）
	private int time[][] = null;
	private String weekTimeTransfer[] = {"","周一","周二","周三","周四","周五","周六"
			,"周日"};
	private String timeTransfer[] = {"","第一二节","第三四节","第五六节","第七八节","晚修"};
	private String signTransfer[] = {"否","是"};
	private List<Map<String,String>> signHistoryList= new ArrayList<Map<String,String>>();
	private Map<Integer,Integer> map =null;
	public StudentSingleCourseSignRecords(String courseTime,String weekTime,
			String courseSignHistory,
			int currentWeek,int currentTime){
		this.courseTime = courseTime;
		this.weekTime = weekTime;
		this.currentWeek = currentWeek;
		this.currentTime = currentTime;
		this.courseSignHistory = courseSignHistory.split(";");
		init();       //初始化转换各种数据
	}
	
	public void init(){
		parseWeek();  //解析服务器中的周数据
		parseCourseTime();
		
	}
	
	protected void parseWeek(){
		
		//解析courseTime 为起始周
		int index1 = courseTime.indexOf("-");
		String tmp = courseTime.substring(0, index1);
		startWeek = Integer.valueOf(tmp);
		tmp = courseTime.substring(index1+1,courseTime.length());
		endWeek = Integer.valueOf(tmp);
		//解析课程上课时间
		String split[] = weekTime.split(";");
		timeTypeNum = split.length;
		timeType = new int[timeTypeNum];
		for(int x=0; x<split.length; x++){
			timeType[x] = Integer.valueOf(split[x]);
		}
		
		//
	}
	
	protected void parseCourseTime(){  //解析上课时间，并排列
		time = new int[timeTypeNum][2];
		int tmp1,tmp2,tmp3;
		for(int x=0; x<timeTypeNum; x++){
			time[x][1] = timeType[x]%100; 
			time[x][0] = timeType[x]/100%10;
			//time[x][0] = timeType[x]/100%10;
		}
		
		//生成上课时间列表
		map = new HashMap<Integer,Integer>();
		for(int x1=startWeek; x1<=currentWeek&&x1<=endWeek; x1++){//x1周数
			for(int x2=1; x2<=7; x2++){          //x2周几
				for(int x3=1; x3<5; x3++){        //x3第几节
					for(int x4=0; x4<timeType.length; x4++){//x4哪种上课时间
						if(x2 == timeType[x4]/100){//周几是否符合
							if(x3 == timeType[x4]/10%10){//第几节是否符合
								if(timeType[x4]%10 == 0){//若不分单双周
									map.put(x1*100+x2*10+x3, 0);
									continue;
								}
								if(x1%2 == timeType[x4]%10%2){
									map.put(x1*100+x2*10+x3, 0);
									continue;
								}
							}
							else continue;
						}
						else{
							continue;
						}
					}
				}
				
			}
		}
		//在上面的列表中签到
		for(int x=0; x<courseSignHistory.length; x++){
			map.put(Integer.valueOf(courseSignHistory[x]), 1);
		}
		//生成客户端显示需要的list
		TreeMap treemap = new TreeMap(map);
		Iterator<Integer> iter = treemap.keySet().iterator();
		Integer key =null;
		while (iter.hasNext()) {
			key = iter.next();
			Map<String,String> tmp = new HashMap<String, String>();
			tmp.put("weekTime", "第"+String.valueOf(key/100)+"周");
			tmp.put("courseTime", weekTimeTransfer[key/10%10]+timeTransfer[key%10]);
			tmp.put("isSigned", signTransfer[map.get(key)%10]);
			signHistoryList.add(tmp);
		}
	}
	
	public List<Map<String,String>> getSignList(){
		return signHistoryList;
	}
	
}
