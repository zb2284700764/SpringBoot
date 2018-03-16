package com.common.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 文件操作的 Util 类
 * 
 * @author zhoubin
 */
public class FileUtil {
	private static Logger log = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * 判断目录是否存在，不存在则创建该目录
	 *
	 * @param dirName 目录名称
	 */
	public static void createDir(String dirName) {
		File file = new File(dirName);
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	/**
	 * 创建文件
	 * @param filePath 文件具体的路径，包括文件
	 */
	public static boolean createFile(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	/**
	 * 检测文件夹是否存在的方法
	 * 
	 * @param dirPath
	 * @return
	 */
	public static boolean existsDir(String dirPath) {
		File f = new File(dirPath);
		if (f.exists())
			return true;
		else
			return false;
	}

	/**
	 * 检测文件是否存在
	 * @param filePath
	 * @return
	 */
	public static boolean existsFile(String filePath) {
		File f = new File(filePath);
		if (f.exists())
			return true;
		else
			return false;
	}

	/**
	 * 生产文件 如果文件所在路径不存在则生成路径
	 * 
	 * @param fileName 文件名 带路径
	 * @param isDirectory 是否为路径
	 * @return
	 * @author yayagepei
	 * @date 2008-8-27
	 */
	public static File buildFile(String fileName, boolean isDirectory) {
		File target = new File(fileName);
		if (isDirectory) {
			target.mkdirs();
		} else {
			if (!target.getParentFile().exists()) {
				target.getParentFile().mkdirs();
				target = new File(target.getAbsolutePath());
			}
		}
		return target;
	}

	/**
	 * 获取文件的后缀名
	 * 
	 * @param filename 文件的名字
	 * @return
	 */
	public static String getExtension(String filename) {
		String extension = null;
		if (filename != null && filename.length() > 0) {
			int i = filename.lastIndexOf('.');
			if (i > 0 && i < filename.length() - 1) {
				extension = filename.substring(i + 1);
			}
		}

		return extension;
	}

	/**
	 * 修改文件的后缀名（文件夹下面的所有文件）
	 * 
	 * @param path 修改的路径
	 * @param from 原来的后缀名
	 * @param to 修改后的后缀名
	 */
	public static boolean reExtensionAllFile(String path, String from, String to) {
		boolean bo = false;
		File f = new File(path);
		File[] fs = f.listFiles();
		try {
			for (int i = 0; i < fs.length; ++i) {
				File f2 = fs[i];
				if (f2.isDirectory()) {
					reExtension(f2.getPath(), from, to);
				} else {
					String name = f2.getName();
					if (name.endsWith(from)) {
						f2.renameTo(
								new File(f2.getParent() + File.separator + name.substring(0, name.indexOf(from)) + to));
					}
				}
			}
			bo = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bo;
	}

	/**
	 * 给单个文件修改后缀名
	 * 
	 * @param filename 文件的名字包括具体的路径
	 * @param from 原来的后缀名
	 * @param to 修改后的后缀名
	 * @return
	 */
	public static boolean reExtension(String filename, String from, String to) {
		boolean bo = false;
		File f = new File(filename);
		String name = f.getName();
		try {
			f.renameTo(new File(f.getParent() + File.separator + name.substring(0, name.indexOf(from)) + to));
			bo = true;
		} catch (Exception e) {
			log.info("找不到文件:" + f.getPath());
			e.printStackTrace();
		}
		return bo;
	}

	/**
	 * 文件拷贝，将源文件目录中的所有文件拷贝至新的文件目录下
	 * 
	 * @param oldPath：源文件目录,可以是文件(文件的话要带具体的后缀名)也可以是文件夹
	 * @param newPath：新文件夹目录不带后缀名
	 */
	public static void fileCopy(String oldPath, String newPath) {
		// oldPath = "file:\\" + oldPath;
		// newPath = "file:\\" + newPath;
		// 文件原地址
		File oldFile = new File(oldPath);
		File newFile = new File(newPath);
		if (!newFile.exists()) {
			newFile.mkdirs();
		}
		// 是否能检测到要拷贝的文件
		if (oldFile.exists()) {
			if (oldFile.isDirectory()) {
				File[] fs = oldFile.listFiles();
				for (int i = 0; i < fs.length; i++) {
					oldFile = fs[i];
					if (oldFile.isDirectory()) {
						fileCopy(oldFile.getPath(), newPath + File.separator + oldFile.getName());
					} else {
						try {
							fileCopy(oldFile, new File(newPath + File.separator + oldFile.getName().toString()));
						} catch (IOException e) {
							log.info(e.getMessage());
							// e.printStackTrace();
						}
					}
				}
			} else {
				try {
					fileCopy(oldFile, new File(newPath + File.separator + oldFile.getName()));
				} catch (IOException e) {
					log.info(e.getMessage());
					// e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 复制文件
	 * @param sourceFile 起始路径
	 * @param targetFile 目标路径
	 * @throws IOException
	 */
	public static void fileCopy(File sourceFile, File targetFile) throws IOException {
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = new FileInputStream(sourceFile);
		BufferedInputStream inBuff = new BufferedInputStream(input);
		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = new FileOutputStream(targetFile);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);
		// 缓冲数组
		byte[] b = new byte[1048576 * 30];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();
		// 关闭流
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();

//		// 通道最大120M
//		int length = 1048576 * 120;
//		FileInputStream in = new FileInputStream(sourceFile);
//		FileOutputStream out = new FileOutputStream(targetFile);
//		FileChannel inC = in.getChannel();
//		FileChannel outC = out.getChannel();
//		ByteBuffer b = null;
//		while (true) {
//			if (inC.position() == inC.size()) {
//				inC.close();
//				outC.close();
//			}
//			if ((inC.size() - inC.position()) < length) {
//				length = (int) (inC.size() - inC.position());
//			} else
//				length = 1048576 * 120;
//			b = ByteBuffer.allocateDirect(length);
//			inC.read(b);
//			b.flip();
//			outC.write(b);
//			outC.force(false);
//		}
	}

	/**
	 * 修改文件的名字
	 * 
	 * @param oldName 旧名字
	 * @param newName 新名字
	 * @return
	 */
	public static boolean reName(String oldName, String newName) {
		File f = new File(oldName);
		File nf = new File(newName);
		// 如果检测到已经有新的名字的文件就删除了
		if (nf.exists()) {
			nf.delete();
		}
		
		return f.renameTo(nf);
	}

	/**
	 * 文件检测，检测所给定文件夹下面所有符合后缀名的文件
	 * 
	 * @param dir 文件夹的名称
	 * @param extNames 要检测的文件的扩展名的集合，xml/txt/html
	 */
	public static List<String> fileExists(String dir, List<String> extNames) {
		List<String> fileNameList = new ArrayList<String>();
		File file = new File(dir);
		File[] allFiles = file.listFiles();
		if (allFiles != null) {
			for (int i = 0; i < allFiles.length; i++) {
				File f = allFiles[i];
				String name = f.getName();
				String houzhui = name.substring(name.lastIndexOf('.') + 1);
				for (int j = 0; j < extNames.size(); j++) {
					if (houzhui.equals(extNames.get(j))) {
						fileNameList.add(f.getPath());
					}
				}
			}
		}
		return fileNameList;
	}

	/**
	 * 文件检测，检测所给定文件夹下面所有符合后缀名的文件
	 * 
	 * @param dir 文件夹的名称
	 * @param extNames 要检测的文件的扩展名，xml、txt、html
	 */
	public static List<String> fileExists(String dir, String extNames) {
		List<String> fileNameList = new ArrayList<String>();
		File file = new File(dir);
		File[] allFiles = file.listFiles();
		if (allFiles != null) {
			for (int i = 0; i < allFiles.length; i++) {
				File f = allFiles[i];
				String name = f.getName();
				String houzhui = name.substring(name.lastIndexOf('.') + 1);
				if (houzhui.equals(extNames)) {
					fileNameList.add(f.getPath());
				}
			}
		}
		return fileNameList;
	}

	/**
	 * 统计文件夹的大小
	 * 
	 * @param f
	 * @return
	 */
	public static long getFileSize(File f) {
		long size = 0;
		File flist[] = f.listFiles();
		for (int i = 0; i < flist.length; i++) {
			if (flist[i].isDirectory()) {
				size = size + getFileSize(flist[i]);
			} else {
				size = size + flist[i].length();
			}
		}
		return size;
	}

	/**
	 * 删除指定的文件夹包括文件夹里面的文件 也可以删除单个文件
	 * 
	 * @param path 文件夹的路径
	 */
	public static boolean deleteDir(String path) {
		File file = new File(path);
		boolean bo = false;
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteDir(files[i].getPath());
			}
			file.delete();
		} else {
			bo = file.delete();
		}
		return bo;
	}

	/**
	 * 对文件大小进行格式化
	 * 
	 * @param fileS
	 * @return
	 */
	public static String formetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS); // 多少字节
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024); // 多少 KB
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1048576); // 多少 MB
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1073741824);// 多少 GB
		}
		return fileSizeString;
	}

	/**
	 * 显示出固定路径下面的所有的文件
	 * 
	 * @param file 传入的路径
	 * @param filesList 返回的文件名的集合 // * @param bo true表示第一次进来，根据文件名字中的下划线"_"将文件名进行分割
	 * @return
	 */
	public static List<String> showAllFile(File file, List<String> filesList) {
		if (file != null) {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				if (files != null) {
					for (int i = 0; i < files.length; i++) {
						showAllFile(files[i], filesList);
					}
				}
			} else {
				// 不带后缀名 只是名字
				// filesList.add(file.getName().substring(0,file.getName().lastIndexOf(".")));
				// 带后缀名的 只是名字
				// filesList.add(file.getName());
				// 带路径的
				filesList.add(file.getPath());
			}
		}
		return filesList;
	}

	/**
	 * 通过字节流的方式将字符串写入文件中
	 * @param filePath 具体的文件
	 * @param content 文件内容
	 * @throws Exception
	 */
	public static void writeToFileByByte(String filePath, String content) throws Exception {
		File file = new File(filePath);
		if (file.exists())
			file.delete();
		file.createNewFile();
		// 准备一个输出的对象，并实例化这个对象
		OutputStreamWriter outStreamWriter = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
		BufferedWriter bufferedWriter = new BufferedWriter(outStreamWriter);
		bufferedWriter.write(content);
		bufferedWriter.close();
		outStreamWriter.close();

		// // OutputStream 只能输出数组，建立一个数组并将字符串转换为数组
		// byte[] b = content.getBytes();
		// // 将数组中的内容写入到文件中
		// out.write(b);
		// // 关闭输出流
		// out.close();
	}

	/**
	 * 通过字节流的方式将字符串追加到文件中
	 * @param filePath 具体的文件
	 * @param content 文件内容
	 * @throws Exception
	 */
	public static void writeAppendOutputToFileByByte(String filePath, String content) throws Exception {
		File file = new File(filePath);
		// file.delete();
		if (!file.exists())
			file.createNewFile();

		OutputStreamWriter outStreamWriter = new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8");
		BufferedWriter bufferedWriter = new BufferedWriter(outStreamWriter);
		bufferedWriter.write(content);
		bufferedWriter.close();
		outStreamWriter.close();

		// // 准备一个输出的对象，并实例化这个对象，true表示进行追加内容
		// OutputStream out = new FileOutputStream(file,true);
		// // OutputStream 只能输出数组，建立一个数组并将字符串转换为数组
		// byte[] b = content.getBytes();
		// // 将数组中的内容写入到文件中
		// out.write(b);
		// // 关闭输出流
		// out.close();
	}

	/**
	 * 通过字节流的方式读取文件中的所有数据
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String readFromFileByByte(String filePath) throws Exception {
		String fileContent = "";
		File file = new File(filePath);
		if (file.exists()) {
			// 准备一个输入的对象
			InputStreamReader inputReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
			BufferedReader reader = new BufferedReader(inputReader);
			String line;
			while ((line = reader.readLine()) != null) {
				fileContent += line;
			}
			reader.close();
			inputReader.close();
		}
		// // 记录读取的数据的个数
		// int len = 0;
		// byte b[] = new byte[(int)file.length()];
		// // 接收读取的每一个内容
		// int temp = 0;
		// while((temp = input.read()) != -1)
		// {
		// b[len] = (byte) temp;
		// len++;
		// }
		// input.close();
		return fileContent;
	}

	/**
	 * 通过字符流的方式向文件中写入数据
	 * 
	 * @param filePath
	 * @param content
	 */
	public static void writeToFileByCharacter(String filePath, String content) throws Exception {
		File file = new File(filePath);
		if (file.exists())
			file.delete();
		file.createNewFile();
		// 准备一个输出流对象
		Writer out = new FileWriter(file);
		// 将内容写入到文件中
		out.write(content);
		// 关闭对象
		out.close();
	}

	/**
	 * 通过字符流的方式向文件中进行追加数据
	 * 
	 * @param filePath
	 * @param content
	 * @throws Exception
	 */
	public static void writeAppendToFileByCharacter(String filePath, String content) throws Exception {
		File file = new File(filePath);
		// 准备一个输出流对象，true 表示进行追加数据
		Writer out = new FileWriter(file, true);
		// 将内容写入到文件中
		out.write(content);
		// 关闭对象
		out.close();
	}

	/**
	 * 使用字符流的方式从文件中读取数据
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String readFromFileByCharacter(String filePath) throws Exception {
		StringBuffer sb = new StringBuffer();
		File file = new File(filePath);
		Reader read = new FileReader(file);
		// 用来记录读取的内容的
		int len = 0;
		// 声明一个char数据用于存放读取的所有内容
		char[] ch = new char[(int) file.length()];
		// 存放读取的内容
		int temp = 0;
		while ((temp = read.read()) != -1) {
			// 将temp中的内容存入数组中
			ch[len] = (char) temp;
			len++;
		}
		// 关闭read
		read.close();
		sb.append(new String(ch));
		return sb.toString();
	}

	/**
	 * 删除指定目录下指定后缀名的文件（批量删除）
	 * 
	 * @param dirPath
	 *            要删除的文件的路径
	 * @param suffix
	 *            要删除的文件的后缀名
	 */
	public static boolean deleteFilesByNameSuffix(String dirPath, String suffix) {
		boolean bo = false;
		File file = new File(dirPath);
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (!f.isDirectory()) {
				String strName = f.getName();
				String fileHouZhui = strName.substring(strName.lastIndexOf(".") + 1);
				System.out.println(fileHouZhui);
				if (fileHouZhui.equalsIgnoreCase(suffix)) {
					try {
						f.delete();
						bo = true;
					} catch (Exception e) {
						e.printStackTrace();
						bo = false;
						return bo;
					}
				}
			}
		}
		return bo;
	}

	/**
	 * 删除文件名字中指定的字符串
	 * 
	 * @param dirPath
	 *            文件所在的路径
	 * @param str
	 *            要删除的字符
	 */
	public static boolean deleteFilenameByCharacter(String dirPath, String str) {
		boolean bo = false;
		File file = new File(dirPath);
		File[] dirFiles = file.listFiles();
		for (int i = 0; i < dirFiles.length; i++) {
			File f = dirFiles[i];
			if (!f.isDirectory()) {
				String fileName = f.getName();
				int strIndex = fileName.indexOf(str); // 找到要删除的字符串在名字中的位置
				if (strIndex != -1) {
					try {
						String newName = fileName.replace(fileName.substring(strIndex, strIndex + str.length()), "");
						f.renameTo(new File(dirPath + File.separator + newName));
						bo = true;
					} catch (Exception e) {
						e.printStackTrace();
						bo = false;
						return bo;
					}
				}
			}
		}
		return bo;
	}

	/**
	 * 给一个指定的String类型的信息补充长度 例："5645"补充够8位，前面补"0"，"00005645"
	 * 
	 * @param message
	 *            指定的要补充长度的信息
	 * @param length
	 *            一共多长（包括obj本身的长度）
	 * @return
	 */
	public static String buChong(String message, int length) {
		String str = "";
		if (message.length() != length) {
			for (int i = 0; i < (length - message.length()); i++) {
				str += "0";
			}
		}
		str += message;
		return str;
	}

	/**
	 * 压缩字符串为 byte[]
	 * 
	 * @param str
	 *            压缩前的文本
	 * @return
	 */
	public static final byte[] compress(String str) {
		if (str == null)
			return null;
		byte[] compressed;
		ByteArrayOutputStream out = null;
		ZipOutputStream zout = null;

		try {
			out = new ByteArrayOutputStream();
			zout = new ZipOutputStream(out);
			zout.putNextEntry(new ZipEntry("0"));
			zout.write(str.getBytes());
			zout.closeEntry();
			compressed = out.toByteArray();
		} catch (IOException e) {
			compressed = null;
		} finally {
			if (zout != null) {
				try {
					zout.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return compressed;
	}

	/**
	 * 将压缩后的 byte[] 数据解压缩 成String
	 * 
	 * @param compressed
	 *            压缩后的 byte[] 数据
	 * @return 解压后的字符串
	 */
	public static final String decompress(byte[] compressed) {
		if (compressed == null)
			return null;
		ByteArrayOutputStream out = null;
		ByteArrayInputStream in = null;
		ZipInputStream zin = null;
		String decompressed;
		try {
			out = new ByteArrayOutputStream();
			in = new ByteArrayInputStream(compressed);
			zin = new ZipInputStream(in);
//			ZipEntry entry = zin.getNextEntry();
			byte[] buffer = new byte[1024];
			int offset = -1;
			while ((offset = zin.read(buffer)) != -1) {
				out.write(buffer, 0, offset);
			}
			decompressed = out.toString();
		} catch (IOException e) {
			decompressed = null;
		} finally {
			if (zin != null) {
				try {
					zin.close();
				} catch (IOException e) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
				}
			}
		}
		return decompressed;
	}

	/**
	 * 将byte[]类型加密
	 * 
	 * @param b
	 * @return
	 */
	public static String getBASE64(byte[] b) {
		String s = null;
		if (b != null) {
			s = new BASE64Encoder().encode(b);
		}
		return s;
	}

	/**
	 * 将一个加密后的String进行解密成byte[]
	 * 
	 * @param s
	 * @return
	 */
	public static byte[] getFromBASE64(String s) {
		byte[] b = null;
		if (s != null) {
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				b = decoder.decodeBuffer(s);
				return b;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return b;
	}

	/**
	 * 将图片读成二进制
	 * 
	 * @param filePath
	 *            文件的路径（绝对路径包括文件后缀名）
	 * @return
	 */
	@SuppressWarnings("resource")
	public static String getBinaryByImage(String filePath) {
		BASE64Encoder encoder = new BASE64Encoder();
		File file = new File(filePath);
		String str = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			byte[] b = new byte[fis.available()];
			fis.read(b);
			str = encoder.encode(b);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 将二进制转换成图片
	 * 
	 * @param str
	 *            二进制内容
	 * @param filePath
	 *            转换的图片的位置（绝对路径包括文件后缀名）
	 */
	public static void getImageByBinary(String str, String filePath) {
		BASE64Decoder decoder = new BASE64Decoder();
		File apple = new File(filePath);
		try {
			FileOutputStream fos = new FileOutputStream(apple);
			byte[] b = decoder.decodeBuffer(str);
			fos.write(b);
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	

	/**
	 * 获取单个文件的MD5值！
	 * @Title getFileMD5 (方法名)
	 * @require 
	 * @param file
	 * @return 
	 * @throws 
	 * @author zhoubin(作者)
	 * @date 2017年3月29日 下午2:57:28
	 * @history
	 */
	public static String getFileMD5(File file) {
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}
	
	/**
	 * 获取文件夹下的所有文件的md5
	 * @Title getDirMD5 (方法名)
	 * @require 
	 * @param file
	 * @param listChild
	 * @return 
	 * @throws 
	 * @author zhoubin(作者)
	 * @date 2017年3月29日 下午2:57:06
	 * @history
	 */
	public static Map<String, String> getDirMD5(File file, boolean listChild) {
		if (!file.isDirectory()) {
			return null;
		}
		// <filepath,md5>
		Map<String, String> map = new HashMap<>();
		String md5;
		File files[] = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			File f = files[i];
			if (f.isDirectory() && listChild) {
				map.putAll(getDirMD5(f, listChild));
			} else {
				md5 = getFileMD5(f);
				if (md5 != null) {
					map.put(f.getPath(), md5);
				}
			}
		}
		return map;
	}

	/**
	 * 将文件夹中的重复文件挑出来
	 * @Title repeatFile2 (方法名)
	 * @require 
	 * @param patha 路径1是文件所在路径
	 * @param pathb 路径2是要挑出来的路径
	 * @throws 
	 * @author zhoubin(作者)
	 * @date 2017年3月29日 下午2:59:02
	 * @history
	 */
	public static void repeatFile2(String patha, String pathb){
//		String patha = "D:\\Workspaces\\temp\\rename\\a";
//		String pathb = "D:\\Workspaces\\temp\\rename\\b";

		File file = new File(patha);
			
		Map<String, String> map = getDirMD5(file, true);


		String[] keys = new String[map.values().size()];
		String[] values = new String[map.values().size()];
		int index = 0;
		
		for (Map.Entry<String, String> entry : map.entrySet()) {  
			keys[index] = entry.getKey();
			values[index] = entry.getValue();
			index++;
//		    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()+",  "+index);  
		} 
		
		for (int i = 0; i < values.length; i++) {
			
			String pathi = keys[i];
			String vi = values[i];
			
			for (int j = (i+1); j < values.length; j++) {
				if((i+1)<=values.length){
					String pathj = keys[j];
					String vj = values[j];
					
					if(vi.equals(vj)){
						// 将相同的图片移动到另外一个目录

						String fileNamei = pathi.substring(pathi.lastIndexOf("\\")+1);
						String fileNamej = pathj.substring(pathj.lastIndexOf("\\")+1);
						FileUtil.fileCopy(pathi, pathb);
						FileUtil.fileCopy(pathj, pathb);
						
						System.out.println("相同的【"+fileNamei+"、"+fileNamej+"】");
					}
				}
			}
		}

	}

}