package com.latewind.action;

import java.util.Map;

import org.apache.log4j.Logger;

import com.latewind.bean.UserInfo;
import com.latewind.dao.OrdersDAO;
import com.latewind.dao.UserInfoDAO;
import com.latewind.service.UserInfoService;
import com.latewind.tools.CookieUtil;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport {
	private String userName;
	private String password;
	private String identifyCode;
	private String  alertMsg;
	private String keeplog="un";
	private UserInfoService userInfoService;
	private OrdersDAO ordersDAO;
	private UserInfoDAO userInfoDAO;
	 static Logger logger=Logger.getLogger(LoginAction.class);
	public UserInfoService getUserInfoService() {
		return userInfoService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public String getAlertMsg() {
		return alertMsg;
	}

	public void setAlertMsg(String alertMsg) {
		this.alertMsg = alertMsg;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIdentifyCode() {
		return identifyCode;
	}

	public void setIdentifyCode(String identifyCode) {
		this.identifyCode = identifyCode;
	}
	
	/**
	 * ע�� �˳��û�
	 * @return
	 */
	public String logout(){
		
		Map<String , Object>session= ActionContext.getContext().getSession();
		session.put("userInfo",null);
		CookieUtil.delCookie("userInfo");
		System.out.println("logout");
		return LOGIN;
	}

	public String login(){
		
		
		
		//��֤�˺�   ����
		//��ȡsession���洢�����֤��
//	System.out.println(userInfoService.isRight("admin", "admin"));
	Map<String , Object>session= ActionContext.getContext().getSession();
      String  code=(String) session.get("IdentifyCode");
//      System.out.println(session.get("userInfo")+"session");
	 
      if(logger.isInfoEnabled()){
		  
		  logger.info("�û����Ե�½");
	  }
	  
      if(session.get("userInfo")!=""&&session.get("userInfo")!=null){
    	  

    	  return SUCCESS;
      }
       
       if(!code.equals(identifyCode)){//��֤�벻һ��
    	   alertMsg="��֤������";
    	   return LOGIN;
    	 
       }
 
       UserInfo uInfo=userInfoService.findByAccount(userName);
       
       if (uInfo==null) {//��֤�˺��Ƿ����
    	   alertMsg="�˺Ų�����";
    	   return LOGIN;		  	   
	}else if (!uInfo.getPassword().equals(password)) {//��֤�����Ƿ���ȷ
		alertMsg="���벻��ȷ";
		return LOGIN;
		
	}else{
		uInfo.setPassword("com.latewind");//��������
		session.put("userInfo", uInfo);
		//����Ǽ�ס��¼�����û�����Cookie�У�
		if(keeplog.equals("keep")){			
			logger.info("���ֵ�¼������cookie��");
		//	CookieUtil cu=new CookieUtil();
			
			CookieUtil.addCookie("userInfo",uInfo.getUserName());
		}
		
		return SUCCESS;
	}
       		
	}

	/**
	 * @return the ordersDAO
	 */
	public OrdersDAO getOrdersDAO() {
		return ordersDAO;
	}

	/**
	 * @param ordersDAO the ordersDAO to set
	 */
	public void setOrdersDAO(OrdersDAO ordersDAO) {
		this.ordersDAO = ordersDAO;
	}

	/**
	 * @return the userInfoDAO
	 */
	public UserInfoDAO getUserInfoDAO() {
		return userInfoDAO;
	}

	/**
	 * @param userInfoDAO the userInfoDAO to set
	 */
	public void setUserInfoDAO(UserInfoDAO userInfoDAO) {
		this.userInfoDAO = userInfoDAO;
	}

	/**
	 * @return the keeplog
	 */
	public String getKeeplog() {
		return keeplog;
	}

	/**
	 * @param keeplog the keeplog to set
	 */
	public void setKeeplog(String keeplog) {
		this.keeplog = keeplog;
	}

}
