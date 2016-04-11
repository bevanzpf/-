package tools;

import java.io.File;

public class FileUtil {

	/**
	 * ɾ���ļ���
	 * @param folderPath:  �ļ�����������·��
	 */
	public static void delFolder(String folderPath) {
		try {
			// ɾ����������������
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			// ɾ�����ļ���
			myFilePath.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ɾ��ָ���ļ����������ļ�
	 * @param path: �ļ�����������·��
	 * @return
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				// ��ɾ���ļ���������ļ�
				delAllFile(path + "/" + tempList[i]);
				// ��ɾ�����ļ���
				delFolder(path + "/" + tempList[i]);
				flag = true;
			}
		}
		
		return flag;
	}
	/**
	 * ɾ���ض��ļ����ļ���
	 * @param file �ض�һ���ļ�����·����
	 */
	public static void deleteFile(String file){
		File temp = new File(file);
		temp.delete();
	}

}

