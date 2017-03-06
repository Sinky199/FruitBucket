package org.test.common.util;

import java.util.UUID;

public class NoteUtil {
	/**
	 * 生成表的主键ID
	 * @return 利用UUID产生的一个字符串值
	 */
	public static String createId(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
}