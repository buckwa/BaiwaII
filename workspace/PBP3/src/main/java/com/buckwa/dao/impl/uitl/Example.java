package com.buckwa.dao.impl.uitl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.buckwa.dao.impl.uitl.ExcelReader.ExcelRowMapper;

public class Example {
	public static void main(String[] args) {
		
		final Map<String,String> data = new HashMap<>();

		try {
			final Pattern r = Pattern.compile("^[0-9]+$");
			ExcelReader excelReader = new ExcelReader("D:\\Project\\KMITL\\doc\\eject_subject.xlsx", 4,1);
			List<String> rs = (List<String>) excelReader.readExcel(new ExcelRowMapper<String>() {

				@Override
				public String mapper(List<Object> rs, int rowIndex) {
					
					if(rs.size() >= 2){
						String val = "";
						if(rs.get(1) instanceof Double){
							val = String.valueOf(((Double)rs.get(1)).intValue());
						}else{
							val= String.valueOf(rs.get(1));
						}
						Matcher m = r.matcher(val);
						boolean find = m.find();
						System.out.println(val + " " + find);
						if(find){
							data.put(val, val);
							return val;
						}
						return null;
					}
					return null;
				}
			});
			System.out.println("SIZE:" +  rs.size());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		//map data
		System.out.println("data:" +  data.size());
		
	}
}
