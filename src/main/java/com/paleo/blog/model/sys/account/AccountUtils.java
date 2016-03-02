package com.paleo.blog.model.sys.account;

import java.util.HashSet;

import com.paleo.blog.tools.http.resource.WebResource;


public class AccountUtils {
	
	private final static String USER_ID_SESSION_KEY = "session:com.paleo.blog.model.account.UserId";
	
//	private final static String TOKEN_SET = "session:com.paleo.blog.model.account.TOKEN_SET";
	
	/**
	 * 从会话中取出userId
	 * @return
	 */
	public static Long getUserId() {
		Object obj = WebResource.session().getAttribute(USER_ID_SESSION_KEY);
		if (obj != null && obj.getClass() == Long.class) {
			return (Long)obj;
		} else {
			return null;
		}
	}
	/**
	 * 将userId加入会话
	 * @return
	 */
	public static void setUserId(Long userId) {
		WebResource.session().setAttribute(USER_ID_SESSION_KEY, userId);
	}
	
	/**
	 * TOKEN不放Session了，个人感觉有线程安全问题，不过少量用户，出现频率很低。
	 * 首先用户量大，且重复提交的用户多，才有机会发生。
	 * 
	 */
//	
//	/**
//	 * add token
//	 * @param token
//	 */
//	public static void addToken(String token) {
//		HashSet<String> set = (HashSet<String>) WebResource.session().getAttribute(TOKEN_SET);
//		if(set!=null){
//			set = new HashSet<String>();
//		}
//		set.add(token);
//		WebResource.session().setAttribute(TOKEN_SET, set);
//	}
//	/**
//	 * has token and remove token
//	 * @return
//	 */
//	public static boolean hasToken(String token) {
//		HashSet<String> set = (HashSet<String>) WebResource.session().getAttribute(TOKEN_SET);
//		boolean hasToken = false;
//		if(set!=null&&!set.isEmpty()){
//			hasToken = set.contains(token);//线程2执行到这一步
//			if(hasToken){
//				set.remove(token);
//				WebResource.session().setAttribute(TOKEN_SET, set);//线程1未执行到这一步，此时脏读
//			}
//		}
//		return hasToken;
//	}
	
}
