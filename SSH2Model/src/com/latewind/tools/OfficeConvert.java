/**
 * 
 */
package com.latewind.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;

import org.apache.struts2.ServletActionContext;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.springframework.context.support.StaticApplicationContext;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.latewind.common.ConstantValue;

/**
 * @author Administrator
 *
 */
public class OfficeConvert {
//	private static String  OPENOFFICE_HOME="E:\\Program Files (x86)\\OpenOffice 4";//openOffice·��
//	private static String SWFTOOL_HOME="E:\\Program Files (x86)\\SWFTools";//SWFTools·��
	private File sourceFile; //ת��Դ�ļ�
	private File pdfFile; //PDFĿ���ļ�
	private File swfFile;  //SWFĿ���ļ�
	private Runtime r;
	

    /** 
         * ��Office�ĵ�ת��ΪPDF. ���иú�����Ҫ�õ�OpenOffice, OpenOffice���ص�ַΪ 
         * http://www.openoffice.org/ 
         *  
         * <pre> 
         * ����ʾ��: 
         * String sourcePath = "F:\\office\\source.doc"; 
         * String destFile = "F:\\pdf\\dest.pdf"; 
         * Converter.office2PDF(sourcePath, destFile); 
         * </pre> 
         *  
         * @param sourceFile 
         *            Դ�ļ�, ����·��. ������Office2003-2007ȫ����ʽ���ĵ�, Office2010��û����. ����.doc, 
         *            .docx, .xls, .xlsx, .ppt, .pptx��. ʾ��: F:\\office\\source.doc 
         * @param destFile 
         *            Ŀ���ļ�. ����·��. ʾ��: F:\\pdf\\dest.pdf 
         * @return �����ɹ�������ʾ��Ϣ. ������� -1, ��ʾ�Ҳ���Դ�ļ�, ��url.properties���ô���; ������� 0, 
         *         ���ʾ�����ɹ�; ����1, ���ʾת��ʧ�� 
         */  
        public static int office2PDF(String sourceFile, String destFile) {  
            try {  
                File inputFile = new File(sourceFile);  
                if (!inputFile.exists()) {  
                    return -1;// �Ҳ���Դ�ļ�, �򷵻�-1  
                }  
                // ���Ŀ��·��������, ���½���·��  
                File outputFile = new File(destFile);  
                if (!outputFile.getParentFile().exists()) {  
                    outputFile.getParentFile().mkdirs();  
                }  
      
                String OpenOffice_HOME =ConstantValue.OPENOFFICE_HOME;//������OpenOffice�İ�װĿ¼, ���ҵ���Ŀ��,Ϊ�˱�����չ�ӿ�,û��ֱ��д���������,���������Ǿ���û�����  
                // ������ļ��ж�ȡ��URL��ַ���һ���ַ����� '\'�������'\'  
                if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '\\') {  
                    OpenOffice_HOME += "\\";  
                }  
                // ����OpenOffice�ķ���  
                String command = OpenOffice_HOME  
                        + "program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";  
                Process pro = Runtime.getRuntime().exec(command);  
                // connect to an OpenOffice.org instance running on port 8100  
                OpenOfficeConnection connection = new SocketOpenOfficeConnection(  
                        "127.0.0.1", 8100);  
                connection.connect();  
      
                // convert  
                DocumentConverter converter = new OpenOfficeDocumentConverter(  
                        connection);  
                converter.convert(inputFile, outputFile);  
      
                // close the connection  
                connection.disconnect();  
                // �ر�OpenOffice����Ľ���  
                pro.destroy();  
      
                return 0;  
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
                return -1;  
            } catch (ConnectException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
      
            return 1;  
        }  
	/**
	 * 
	 * @param src
	 * @param des
	 */
	public static void pdf2swf(String src,String des){
		String command=ConstantValue.SWFTOOLS_HOME+"\\"+"pdf2swf.exe "+src+" -o "+des+" -T 9";//	
//		Process p=Runtime.getRuntime().exec(command); 
		ExeCMD.exe(command);	
	}
	/**
	 * ���ļ�����ʱ�ļ�����ȡ�ߣ�����swf�ļ���
	 */
	
	public static int office2Swf(String srcPath){
		File file=new File(srcPath);
		String fileName=file.getName();
		String parentPath=file.getParent();
		String fileNameNoExt=FileUtil.getNameExcpExt(fileName);
		
		System.out.println(fileName+","+parentPath);
		String docPath=srcPath;
		String pdfPath=parentPath+"\\"+fileNameNoExt+".pdf";
		String swfPath=parentPath+"\\"+fileNameNoExt+".swf";
		System.out.println(docPath);
		System.out.println(pdfPath);
		System.out.println(swfPath);
		//
		office2PDF(docPath, pdfPath);
		pdf2swf(pdfPath, swfPath);
		
		
		return 1;
	}
	
	public static void pdf2swf2(){
		
		
		
		//String path=this.getClass().getClassLoader().getResource("/").getPath();
		//System.out.println(path.toString());
		System.out.println(System.getProperty("user.dir")); 
	
		String path2=ServletActionContext.getServletContext().getRealPath("/");
		String path3=path2+"swf\\";
		System.out.println(path3);
		
		File f=new File(ConstantValue.SWF_TEMP_PATH);
		f.mkdirs();
		OfficeConvert.pdf2swf("E:\\1.pdf",ConstantValue.SWF_TEMP_PATH+"\\1.swf");
		File file=new File(ConstantValue.SWF_TEMP_PATH+"\\1.swf");
		if (file.exists()){
//			file.renameTo(new File(file.getName()));
			file.renameTo(new File(path3+file.getName()));
		}	
		
		
		
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		OfficeConvert wConvert=new OfficeConvert();
//		wConvert.office2PDF("D:\\1.doc", "D:\\1.pdf");
//		OfficeConvert.office2PDF("E:\\2.doc", "E:\\2.pdf");
//		OfficeConvert.pdf2swf("E:\\2.pdf","E:\\2.swf");
//		OfficeConvert.pdf2swf2();
		
		System.out.println("end");
	}

}
