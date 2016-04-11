package service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import model.User.User;
import model.User.UserDao;
import tools.FileUtil;
import tools.MD5Util;
import tools.RandomUtil;
import tools.ReflectUtil;
import tools.ServiceException;

public class FileUploadService {
	public void proccessUploadIcon(HttpServletRequest request) throws Exception {
		String file = "/upload/icon";
		String  REAL_UPLOAD_PATH = request.getSession().getServletContext().getRealPath(file);
		ServletFileUpload upload = getUpload(request);
		List<String> typeRange = Arrays.asList("jpg","gif","png","bmp","tiff");// ͼƬ����չ����Χ
		User user = (User)request.getSession().getAttribute("user");
		String oldIcon = user.getIcon();           //��¼�ɵ�icon�ļ���
		// Parse the request
		List<FileItem> items = upload.parseRequest(request);
		Map<String,FileItem> pathItemMap = new HashMap<>();
		
		buildPathItemMapAndBean(items,pathItemMap,user,REAL_UPLOAD_PATH);
		// ��֤��չ����                                                                                                       ��ô��װ�ɷ�����
		String iconfileName = user.getIcon();
		System.out.println("iconFileName:"+iconfileName);
		String ext = iconfileName.substring(iconfileName.indexOf(".")+1);
		if(!typeRange.contains(ext))
			throw new ServiceException("�ļ����ʹ���");
		
		//ɾ���ɵ�icon�ļ���
		String deleteFile = REAL_UPLOAD_PATH +"\\"+oldIcon;
		FileUtil.deleteFile(deleteFile);
		
		upload(pathItemMap); //�ϴ��ļ�
		//�������ݿ�;
		String icon = user.getIcon();
		int id = user.getId();
		String savaIcon = "UPDATE users set icon = ? where id = ?";
		UserDao userdao = new UserDao();
		userdao.update(savaIcon, icon,id);
	}


	

	private void upload(Map<String, FileItem> pathItemMap) throws Exception  {
		for(Map.Entry<String,FileItem> map: pathItemMap.entrySet()){
			String file = map.getKey();
			FileItem item  = map.getValue();
			File uploadeFile = new File(file);
			item.write(uploadeFile);
		}
	}

	private void buildPathItemMapAndBean(List<FileItem> items, Map<String,FileItem> map,Object object,String path) throws ServiceException{ 
		for(FileItem item :items){
			if (item.isFormField()) {
		        String fieldName = item.getFieldName();//fieldName �� bean �е���������Ӧ
		        String fieldValue = item.getString();
		        ReflectUtil.setFieldValue(object, fieldName, fieldValue);//��bean������ ��¼��Ʒ���ԣ������������⣩.��������������ݿ⡣
		    } else {
		    	String fieldName = item.getFieldName(); //fieldName �� bean �е���������Ӧ
		    	String fileName = item.getName();
		    	long size = item.getSize();
		    	if(size > 1024 *1024)
		    		throw new ServiceException("�ļ�����1M");    //����ô����
		    	//
		    	String extName = fileName.substring(fileName.indexOf("."));
		    	String radomFileName = MD5Util.getMD5hash(RandomUtil.random(11));  
		    	String Realpath = path+"\\"+radomFileName+extName;
		    	String savedPath = Realpath.substring(Realpath.indexOf("\\icon\\")+6);
		    	System.out.println("�������·����"+Realpath);   
		    	
		    	ReflectUtil.setFieldValue(object, fieldName ,savedPath);//��bean������ ��¼��Ʒ���Լ����Ӧ��·��.��������������ݿ⡣
		    	
		    	map.put(Realpath, item); //��·���� item ������䵽map �������д���ļ�;
		    }
		}
	}
	private ServletFileUpload getUpload(HttpServletRequest request) {
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Set factory constraints
		factory.setSizeThreshold(1024*5);
		String temp = request.getSession().getServletContext().getRealPath("/temp");
		File tempFile = new File(temp);
		factory.setRepository(tempFile);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Set overall request size constraint
		upload.setSizeMax(1024*1024*1024);  //56 �д��淽������Ȼ�е���ɶ������
		return upload;
	}
	
}
