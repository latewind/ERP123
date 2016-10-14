/**
 * 
 */
package com.latewind.action;

import java.util.Map;

import org.apache.log4j.Logger;

import com.latewind.bean.UserInfo;
import com.latewind.service.UserInfoService;
import com.latewind.tools.CookieUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Administrator
 *
 */
public class IndexAction extends ActionSupport {
static Logger logger=Logger.getLogger(IndexAction.class);
private  UserInfoService userInfoService;
	public String execute(){
		
		//���Cookie���Ƿ�洢
		String userName=CookieUtil.getCookie("userInfo");
		if(userName!=null){
			
			Map<String , Object>session= ActionContext.getContext().getSession();
			//session�д洢�û���
			UserInfo userInfo=userInfoService.findByAccount(userName);
			session.put("userInfo",userInfo);
			logger.info("ȡ��cookie����");
			
			return SUCCESS;	
		}else{
			return LOGIN;
		}
	}
	public UserInfoService getUserInfoService() {
		return userInfoService;
	}
	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	
	

}
