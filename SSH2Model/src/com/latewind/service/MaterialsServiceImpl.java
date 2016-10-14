/**
 * 
 */
package com.latewind.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.latewind.bean.UserInfo;
import com.latewind.dao.UserInfoDAO;
import com.latewind.domain.Department;
import com.latewind.domain.Employee;
import com.latewind.domain.EmployeeDAO;
import com.latewind.domain.Materials;
import com.latewind.domain.MaterialsDAO;
import com.latewind.domain.Position;
import com.latewind.domain.Processes;
import com.latewind.domain.ProcessesDAO;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author Administrator
 *
 */
public class MaterialsServiceImpl implements MaterialsService {

	private  ProcessesDAO processesDAO;
	private MaterialsDAO materialsDAO;
	private EmployeeDAO employeeDAO;
	private UserInfoDAO userInfoDAO;
	
	/* (non-Javadoc)
	 * @see com.latewind.service.MaterialsService#addMaterials()
	 */
	@Override
	
	public List<Materials> approveMaterials(UserInfo u) {
		
		
		return materialsDAO.findByUserId(u.getId());
	}
	public void agreed2Approve(List<Integer> ids){
		
		/**
		 * 1������userInfo �ҵ������˺�
		 * 2�������˺��ҳ�employee 
		 * 3������employee �ҳ�ְλ
		 * 4���ҳ��ϼ�
		 * 5���ҳ���������쵼
		 * 6��������һ����������
		 */
		Map<String , Object>session= ActionContext.getContext().getSession();//1
		UserInfo uInfo=(UserInfo) session.get("userInfo");
		
		Employee e=uInfo.getEmployee();//2
		Position p=e.getPosition();//3
		Position leaderP=p.getDepartment().getLeader();//�������쵼
		
		if(p.getPositionId().equals(leaderP.getPositionId())){//�����˾��ǲ�����Ҫ�쵼
			for(Integer i:ids){
				
				Materials ms=materialsDAO.findById(i);
				String pString=ms.getProcessesInfo();
				pString=pString+ms.getUserinfoId()+" "+"ͬ��"+",";//���ý�����Ϣ
				ms.setProcessesInfo(pString);
				
				ms.setStatus("����ͨ��");
				ms.setUserinfoId(10);//����Ӧ�����õ��ɹ���Ա��Ϣ 10Ϊ�ɹ�
				materialsDAO.attachDirty(ms);
			}
			
		}else{
		
		Employee es[] = new Employee[2];
		p.getSuperior().getEmployees().toArray(es);
		Integer userId=es[0].getUserInfo().getId();
		System.out.println("�ϼ��쵼userInfo id="+userId);
		for(Integer i:ids){
			
			Materials ms=materialsDAO.findById(i);
			String pString=ms.getProcessesInfo();
			pString=pString+ms.getUserinfoId()+" "+"ͬ��"+",";//���ý�����Ϣ
			ms.setProcessesInfo(pString);
			ms.setUserinfoId(userId);
			materialsDAO.attachDirty(ms);
		}
		}
		
	}
	
	public void disagree(List<Integer> ids){
		for(Integer i:ids){
			
			Materials ms=materialsDAO.findById(i);
			materialsDAO.delete(ms);//��ͨ������ɾ��
			/**
			 * �����Ϣ����
			 * 
			 */
		}
		
		
	}
	public void addMaterials(List<Materials> list) {	
		for(Materials m:list){	
			int userId=m.getUserinfoId();//������ID
			
			UserInfo u=userInfoDAO.findUserById(userId);//������
		Iterator<Employee>	ie=u.getEmployee().
							getPosition().
								getSuperior().
									getEmployees().
											iterator();
		int supId = 0;	
		while(ie.hasNext()){
				supId=ie.next().getUserInfo().getId();
				System.out.println("��һ���ϼ���userInfo ID"+supId);
			}
		m.setProcessesInfo(userId+" "+"�ύ"+",");
		m.setUserinfoId(supId);//�����ϼ��˺�ID
		materialsDAO.attachDirty(m);
		
		}
		//
		// TODO Auto-generated method stub
		
	}
	public ProcessesDAO getProcessesDAO() {
		return processesDAO;
	}
	public void setProcessesDAO(ProcessesDAO processesDAO) {
		this.processesDAO = processesDAO;
	}
	public MaterialsDAO getMaterialsDAO() {
		return materialsDAO;
	}
	public void setMaterialsDAO(MaterialsDAO materialsDAO) {
		this.materialsDAO = materialsDAO;
	}
	/**
	 * @return the employeeDAO
	 */
	public EmployeeDAO getEmployeeDAO() {
		return employeeDAO;
	}
	/**
	 * @param employeeDAO the employeeDAO to set
	 */
	public void setEmployeeDAO(EmployeeDAO employeeDAO) {
		this.employeeDAO = employeeDAO;
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


}
