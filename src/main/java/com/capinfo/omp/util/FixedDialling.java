package com.capinfo.omp.util;

public class FixedDialling {
	 public static final String M11 = "8008100032";  	//语音广播
	 public static final String M13 = "84925513";  		//中心好号码1
	 public static final String M14 = "84931297";  		//中心好号码2
	 public static final String M15 = "8008100032";  	//中心好号码3
	 public static final String M16 = "8008100032";  	//中心好号码4
	 
	 public static final Long M11SP = 90L;  	//语音广播
	 public static final Long M13SP = 92L;  		//中心好号码1
	 public static final Long M14SP = 93L;  		//中心好号码2
	 public static final Long M15SP = 90L;  	//中心好号码3
	 public static final Long M16SP = 90L;  	//中心好号码4
	 
	 /**
	  * 查询服务号码对应服务商的id
	  * select sp.id FROM service_provider sp where sp.serviceTell = '8008100032';
	  */

}
