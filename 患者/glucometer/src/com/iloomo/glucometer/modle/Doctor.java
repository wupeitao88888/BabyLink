package com.iloomo.glucometer.modle;

public class Doctor {
	public long id;
	public String city;
	public String hospital;
	public String name;
	public String getDoctor(){
		String _temp="";
//		if(name != null && !"".equals(name) && !"null".equals(name)){
//			_temp +=name;
//		}
		String _temp2="";
		if(city != null && !"null".equals(city)&& !"".equals(city)){
			_temp2+=city;
		}
		
		if(hospital != null && !"null".equals(hospital)&& !"".equals(hospital)){
			_temp2+=hospital;
		}
		
		if(!"".equals(_temp2)){
			_temp+=_temp2;
		}
		return _temp;
	}
}
