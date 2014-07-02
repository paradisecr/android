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
	private String courseTime = null; //�������˿γ��Ͽ���ʱ���ݣ���ʽΪ��1-n�ܣ�
	private String weekTime = null; //�������˿γ�ÿ���Ͽ�ʱ�����ݣ���ʽΪ��150;251��
	private int currentWeek = 0;  //���ڵ�����
	private int currentTime = 0;        //�����ܼ�������
	private String courseSignHistory[] = null;   //ǩ����¼
	
	private int startWeek = 0;    //��ʼ��
	private int endWeek = 0;      //�ս���	
	
	private int timeType[] = null; //��¼ÿ���Ͽ�ʱ�䣨�絥��ÿ��һ���Ľ�Ϊ��111��
	private int timeTypeNum = 0; //��¼�Ͽ�ʱ���м��֣�ͬtimeType[]��ϵ��
	private int time[][] = null;
	private String weekTimeTransfer[] = {"","��һ","�ܶ�","����","����","����","����"
			,"����"};
	private String timeTransfer[] = {"","��һ����","�����Ľ�","��������","���߰˽�","����"};
	private String signTransfer[] = {"��","��"};
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
		init();       //��ʼ��ת����������
	}
	
	public void init(){
		parseWeek();  //�����������е�������
		parseCourseTime();
		
	}
	
	protected void parseWeek(){
		
		//����courseTime Ϊ��ʼ��
		int index1 = courseTime.indexOf("-");
		String tmp = courseTime.substring(0, index1);
		startWeek = Integer.valueOf(tmp);
		tmp = courseTime.substring(index1+1,courseTime.length());
		endWeek = Integer.valueOf(tmp);
		//�����γ��Ͽ�ʱ��
		String split[] = weekTime.split(";");
		timeTypeNum = split.length;
		timeType = new int[timeTypeNum];
		for(int x=0; x<split.length; x++){
			timeType[x] = Integer.valueOf(split[x]);
		}
		
		//
	}
	
	protected void parseCourseTime(){  //�����Ͽ�ʱ�䣬������
		time = new int[timeTypeNum][2];
		int tmp1,tmp2,tmp3;
		for(int x=0; x<timeTypeNum; x++){
			time[x][1] = timeType[x]%100; 
			time[x][0] = timeType[x]/100%10;
			//time[x][0] = timeType[x]/100%10;
		}
		
		//�����Ͽ�ʱ���б�
		map = new HashMap<Integer,Integer>();
		for(int x1=startWeek; x1<=currentWeek&&x1<=endWeek; x1++){//x1����
			for(int x2=1; x2<=7; x2++){          //x2�ܼ�
				for(int x3=1; x3<5; x3++){        //x3�ڼ���
					for(int x4=0; x4<timeType.length; x4++){//x4�����Ͽ�ʱ��
						if(x2 == timeType[x4]/100){//�ܼ��Ƿ����
							if(x3 == timeType[x4]/10%10){//�ڼ����Ƿ����
								if(timeType[x4]%10 == 0){//�����ֵ�˫��
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
		//��������б���ǩ��
		for(int x=0; x<courseSignHistory.length; x++){
			map.put(Integer.valueOf(courseSignHistory[x]), 1);
		}
		//���ɿͻ�����ʾ��Ҫ��list
		TreeMap treemap = new TreeMap(map);
		Iterator<Integer> iter = treemap.keySet().iterator();
		Integer key =null;
		while (iter.hasNext()) {
			key = iter.next();
			Map<String,String> tmp = new HashMap<String, String>();
			tmp.put("weekTime", "��"+String.valueOf(key/100)+"��");
			tmp.put("courseTime", weekTimeTransfer[key/10%10]+timeTransfer[key%10]);
			tmp.put("isSigned", signTransfer[map.get(key)%10]);
			signHistoryList.add(tmp);
		}
	}
	
	public List<Map<String,String>> getSignList(){
		return signHistoryList;
	}
	
}
