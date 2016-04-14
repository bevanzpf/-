package service;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
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
import model.work.Work;
import model.work.WorkDao;
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
		List<String> typeRange = Arrays.asList("jpg","gif","png","bmp","tiff");// 图片的扩展名范围
		User user = (User)request.getSession().getAttribute("user");
		String oldIcon = user.getIcon();           //记录旧的icon文件。
		// Parse the request
		List<FileItem> items = upload.parseRequest(request);
		Map<String,FileItem> pathItemMap = new HashMap<>();
		
		buildPathItemMapAndBean(items,pathItemMap,user,REAL_UPLOAD_PATH);
		// 验证扩展名；                                                                                                       怎么封装成方法。
		String iconfileName = user.getIcon();
		System.out.println("iconFileName:"+iconfileName);
		String ext = iconfileName.substring(iconfileName.indexOf(".")+1);
		if(!typeRange.contains(ext))
			throw new ServiceException("文件类型错误");
		
		//删除旧的icon文件。
		String deleteFile = REAL_UPLOAD_PATH +"\\"+oldIcon;
		FileUtil.deleteFile(deleteFile);
		
		upload(pathItemMap); //上传文件
		//更新数据库;
		String icon = user.getIcon();
		int id = user.getId();
		String savaIcon = "UPDATE users set icon = ? where id = ?";
		UserDao userdao = new UserDao();
		userdao.update(savaIcon, icon,id);
	}
	
	
public void processUploadWork(HttpServletRequest request) throws Exception{
	request.setCharacterEncoding("UTF-8");
	String file = "/upload/works";
	String  REAL_UPLOAD_PATH = request.getSession().getServletContext().getRealPath(file);
	ServletFileUpload upload = getUpload(request);
	List<String> typeRange = Arrays.asList("jpg","gif","png","bmp","tiff");
	Work work = new Work();
	
	List<FileItem> items = upload.parseRequest(request);
	Map<String,FileItem> pathItemMap =  new HashMap<>();
	buildPathItemMapAndBean(items,pathItemMap,work,REAL_UPLOAD_PATH);
	// validateType
	validateType(work.getFileName(),typeRange);
	//upload
	upload(pathItemMap);
	
	//更新数据库
	User user = (User)request.getSession().getAttribute("user");
	int userId = user.getId();
	//userId subject info date fileName
	String subject = work.getSubject();
	String info = work.getInfo();
	Date date = new Date(new java.util.Date().getTime());
	String fileName = work.getFileName();
	String SaveWork = "INSERT into works(userId,subject,info,date,fileName) values(?,?,?,?,?)";
	WorkDao workdao = new WorkDao();
	workdao.update(SaveWork, userId,subject,info,date,fileName);
	
}
private void validateType(String fileName,List<String> range) throws ServiceException{
	String ext = fileName.substring(fileName.indexOf(".")+1);
	if(!range.contains(ext))
		throw new ServiceException("文件类型出错");
}

	

	private void upload(Map<String, FileItem> pathItemMap) throws Exception  {
		for(Map.Entry<String,FileItem> map: pathItemMap.entrySet()){
			String file = map.getKey();
			FileItem item  = map.getValue();
			File uploadeFile = new File(file);
			System.out.println("文件正上传到："+file);
			item.write(uploadeFile);
		}
	}

	private void buildPathItemMapAndBean(List<FileItem> items, Map<String,FileItem> map,Object object,String path) throws ServiceException, UnsupportedEncodingException{ 
		for(FileItem item :items){
			if (item.isFormField()) {
		        String fieldName = item.getFieldName();//fieldName 与 bean 中的属性名对应
		        String fieldValue = item.getString("UTF-8");
		        ReflectUtil.setFieldValue(object, fieldName, fieldValue);//在bean对象中 记录作品属性（如描述，主题）.方便后续存入数据库。
		    } else {
		    	String fieldName = item.getFieldName(); //fieldName 与 bean 中的属性名对应
		    	String fileName = item.getName();
		    	long size = item.getSize();
		    	if(size > 1024 *1024)
		    		throw new ServiceException("文件大于1M");    //就这么抛了
		    	//
		    	String extName = fileName.substring(fileName.indexOf("."));
		    	String radomFileName = MD5Util.getMD5hash(RandomUtil.random(11));  
		    	String Realpath = path+"\\"+radomFileName+extName;
		    	String savedPath = Realpath.substring(Realpath.indexOf("\\upload\\")+8);///worK上传时要改变？？？？？？
		    	System.out.println("存入绝对路径："+Realpath);   
		    	
		    	ReflectUtil.setFieldValue(object, fieldName ,savedPath);//在bean对象中 记录作品属性及其对应的路径.方便后续存入数据库。
		    	
		    	map.put(Realpath, item); //把路径跟 item 对象填充到map 方便后续写入文件;
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
		upload.setHeaderEncoding("UTF-8");
		// Set overall request size constraint
		upload.setSizeMax(1024*1024*1024);  //56 行代替方案，虽然有点那啥。。。
		return upload;
	}
	
}
