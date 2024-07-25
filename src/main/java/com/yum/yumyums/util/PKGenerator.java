package com.yum.yumyums.util;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class PKGenerator {
	public static String generatePK() {
		// 현재 시간 가져오기
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String currentTime = dateFormat.format(new Date());

		// UUID 생성 후 앞 8자리만 사용
		String uniqueID = UUID.randomUUID().toString().substring(0, 15).replace("-", "");

		// 시간과 UUID 결합
		return currentTime + uniqueID;
	}
}
