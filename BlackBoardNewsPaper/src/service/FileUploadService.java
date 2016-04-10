package service;

import java.io.File;
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
import tools.MD5Util;
import tools.RandomUtil;
import tools.ReflectUtil;

public class FileUploadService {
	public void proccessUploadIcon(HttpServletRequest request) throws Exception{
		String file = "/upload/icon";
		String  REAL_UPLOAD_PATH = request.getSession().getServletContext().getRealPath(file);
		ServletFileUpload upload = getUpload(request);

		// Parse the request
		List<FileItem> items = upload.parseRequest(request);
		Map<String,FileItem> pathItemMap = new HashMap<>();
		User user = (User)request.getSession().getAttribute("user");
		
		buildPathItemMapAndBean(items,pathItemMap,user,REAL_UPLOAD_PATH);
		
		upload(pathItemMap);
		//更新数据库;
		String iconURL = user.getIconURL();
		int id = user.getId();
		String saveURL = "UPDATE users set iconURl = ? where id = ?";
		UserDao userdao = new UserDao();
		userdao.update(saveURL, iconURL,id);
	}


	private void upload(Map<String, FileItem> pathItemMap) throws Exception {
		for(Map.Entry<String,FileItem> map: pathItemMap.entrySet()){
			String file = map.getKey();
			FileItem item  = map.getValue();
			File uploadeFile = new File(file);
			item.write(uploadeFile);
		}
	}

	private void buildPathItemMapAndBean(List<FileItem> items, Map<String,FileItem> map,Object object,String path){ 
		for(FileItem item :items){
			if (item.isFormField()) {
		        String fieldName = item.getFieldName();//fieldName 与 bean 中的属性名对应
		        String fieldValue = item.getString();
		        ReflectUtil.setFieldValue(object, fieldName, fieldValue);//在bean对象中 记录作品属性（如描述，主题）.方便后续存入数据库。
		    } else {
		    	String fieldName = item.getFieldName(); //fieldName 与 bean 中的属性名对应
		    	String fileName = item.getName();
		    	String extName = fileName.substring(fileName.indexOf("."));
		    	String radomFileName = MD5Util.getMD5hash(RandomUtil.random(11));  
		    	String Realpath = path+"\\"+radomFileName+extName;
		    	
		    	ReflectUtil.setFieldValue(object, fieldName ,Realpath);//在bean对象中 记录作品属性及其对应的路径.方便后续存入数据库。
		    	
		    	map.put(Realpath, item); //把路径跟 item 对象填充到map 方便后续写入文件;
		    }
		}
	}
	private ServletFileUpload getUpload(HttpServletRequest request) {
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Set factory constraints
		factory.setSizeThreshold(1024*500);
		String temp = request.getSession().getServletContext().getRealPath("/temp");
		File tempFile = new File(temp);
		factory.setRepository(tempFile);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Set overall request size constraint
		upload.setSizeMax(1024*1024);
		return upload;
	}
	
}
