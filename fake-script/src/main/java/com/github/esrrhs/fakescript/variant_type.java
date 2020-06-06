package com.github.esrrhs.fakescript;

enum variant_type 
{
	NIL,
	REAL,	   	// 参与计算的数值
	STRING,	 	// 字符串
	POINTER,	// 指针
	UUID,	   	// int64的uuid，不参与计算，为了效率
	ARRAY,	  	// 数组
	MAP,		// 集合
}
