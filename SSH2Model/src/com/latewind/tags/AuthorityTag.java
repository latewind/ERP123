/**
 * 
 */
package com.latewind.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * @author Administrator
 *
 */
public class AuthorityTag extends SimpleTagSupport {
	
	
	 /**
     * �����ǩ������
     */
	private String authority;
 
	private int count;
    
    /**count���Զ�Ӧ��set����
     * @param count
     */
    public void setCount(int count) {
        this.count = count;
    }
    public String getAuthority() {
 		return authority;
 	}

 	public void setAuthority(String authority) {
 		this.authority = authority;
 	}

    
    /* �򵥱�ǩʹ����������Ϳ���������е�ҵ���߼�
     * @see javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
     * ��дdoTag������ͨ����ǩ�����Կ��Ʊ�ǩ���ִ�д���
     */
    @Override
    public void doTag() throws JspException, IOException {
//        for (int i = 0; i < count; i++) {
//            this.getJspBody().invoke(null);
//        }
//        
        if(count>5){
        	 this.getJspBody().invoke(null);
        	
        }
        
    }
}
