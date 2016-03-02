package com.paleo.test.sys.jedis;

import org.junit.Test;

import com.paleo.blog.tools.jedis.JedisUtils;

public class jedis {

	
	@Test
	public void test(){
//		String val = com.paleo.blog.tools.redis.JedisUtils.get("_ACCOUNT_com.paleo.blog.service.sys.account.imp.AccountService.getAccount[java.lang.Long][1]");
//		System.out.println(val);
		System.out.println(JedisUtils.incr("asdsfdsagf"));
	}
}
