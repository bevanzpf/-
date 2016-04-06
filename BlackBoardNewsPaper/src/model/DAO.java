package model;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.tagext.TryCatchFinally;

import org.apache.commons.beanutils.BeanUtils;

import model.User.User;
import tools.JdbcUtil;
import tools.ReflectUtil;

public class DAO {
	// INSERT UPDATE DELETE 操作都用这个方法
	public void update(String sql,Object...args){
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		
		try{
			conn = JdbcUtil.getConnection();
			preparedStatement = conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++)
			{
				preparedStatement.setObject(i+1, args[i]);
			}
			preparedStatement.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JdbcUtil.releaseDB(null,preparedStatement,conn);
		}
	}
	
	public <T> T get(Class<T> clazz,String sql,Object...args){
		List<T> result = getForList(clazz, sql, args);
		if(result.size() > 0){
			return result.get(0);
		}
		
		return null;
	}
	public <T> List<T> getForList(Class<T> clazz,String sql,Object...args){
		List<T> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			conn = JdbcUtil.getConnection();
			statement = conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++)
			{
				statement.setObject(i+1, args[i]);
			}
			resultSet = statement.executeQuery();
			
			List<Map<String,Object>> values = handleResultSetToMapLisst(resultSet);
			list = transfterMapListToBeanList(clazz,values);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.releaseDB(resultSet, statement, conn);
		}
		return list;
	}
	
	private<T> List<T> transfterMapListToBeanList(Class<T> clazz,List<Map<String, Object>> values) throws InstantiationException, IllegalAccessException, InvocationTargetException {
		List<T> result = new ArrayList<>();
		T bean = null;
		if(values.size()>0)
		{
			for(Map<String,Object> m: values){
				bean = clazz.newInstance();
				for(Map.Entry<String, Object> map: m.entrySet()){
					String fieldName = map.getKey();
					Object fieldValue = map.getValue();
					ReflectUtil.setFieldValue(bean, fieldName, fieldValue);
				}
				result.add(bean);
			}
		}
		
		return result;
	}

	
	private List<Map<String, Object>> handleResultSetToMapLisst(ResultSet resultSet) throws SQLException {
		List<Map<String,Object>> mapList = new ArrayList<>();
		Map<String,Object> map = null;
		List<String> columnLabels = getColumnLabels(resultSet);
		
		while(resultSet.next()){
			map = new HashMap<>();
			for(String columnLabel :columnLabels)
			{
				Object fieldValue = resultSet.getObject(columnLabel);
				map.put(columnLabel, fieldValue);
			}
			mapList.add(map);
		}
		
		return mapList;
	}
	public List<String> getColumnLabels(ResultSet resultSet) throws SQLException
	{
		List<String> result = new ArrayList<>();
		ResultSetMetaData rsmd = resultSet.getMetaData();
		for(int i=0;i<rsmd.getColumnCount();i++)
		{
			result.add(rsmd.getColumnName(i+1));
		}
		return result;
	}
	public <E> E getForValue(String sql,Object...args){
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			conn = JdbcUtil.getConnection();
			statement = conn.prepareStatement(sql);
			for(int i=0;i<args.length;i++)
			{
				statement.setObject(i+1, args[i]);
			}
			resultSet = statement.executeQuery();
			if(resultSet.next()){
				return(E) resultSet.getObject(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			JdbcUtil.releaseDB(resultSet, statement, conn);
		}
		return null;
	}

	//
	public static void main(String[] args) {
////		String sql = "INSERT into users(name,password) values(?,?)";
//		DAO dao = new DAO();
////		dao.update(sql,"tom5","233yy");
//		String sql2 = "Select id id,name name,password password from users where id =? or id=?";
//		List<User> user3 = dao.getForList(User.class, sql2, 1,2);
//		System.out.println(user3.toString());
//		String sql3 = "Select Max(id)  validateCode from users where id=? or id=?";
//		Object result = dao.getForValue(sql3, 2,3);
//		System.out.println(result);
		String sql1 = "select email email,registerTime registerTime,validateCode validateCode,status status from users where email = ?";
		DAO dao = new DAO();
		User user = dao.get(User.class, sql1, "627620497@qq.com");
		System.out.println(user.getValidateCode());
	}
}

//	public <T> T get(Class<T> clazz,String sql,Object...args){
//		T entity = null;
//		 Connection connection = null;
//		 PreparedStatement statement = null;
//		 ResultSet resultSet = null;
//		 try {
//			connection = JdbcUtil.getConnection();
//			statement = connection.prepareStatement(sql);
//			for(int i= 0;i< args.length;i++){
//				statement.setObject(i+1, args[i]);
//			}
//			resultSet = statement.executeQuery();
//			ResultSetMetaData rsmd = resultSet.getMetaData();
//			int counts = rsmd.getColumnCount();
//			Map<String,Object> map = new HashMap<>();
//			if(resultSet.next()){
//				for(int i=0;i<counts;i++){
//					map.put(rsmd.getColumnLabel(i+1), resultSet.getObject(i+1));
//				}
//			}
//			if(map.size()>0){
//				entity = clazz.newInstance();
//				for(Map.Entry<String, Object> e: map.entrySet()){
//					String fieldName = e.getKey();
//					Object fieldValue = e.getValue();
//					BeanUtils.setProperty(entity, fieldName, fieldValue);
////					ReflectUtil.setFieldValue(entity, fieldName, fieldValue);
//				}
//			}
//			
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally {
//			JdbcUtil.releaseDB(resultSet, statement, connection);
//			
//		}
//		return entity;
//	}