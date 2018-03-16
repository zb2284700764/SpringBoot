package com.common.util;

import net.coobird.thumbnailator.Thumbnails;

import java.io.*;

/**
 *
 * @author zhoubin
 *
 * @createDate 2016年10月25日 上午11:05:30
 */
public class ImageUtil {
	
	private static float defaultRatio = 0.1f;

	/**
	 * 将图片压缩到小于1M并返回压缩后的图片
	 * 
	 * @author zhoubin
	 * @throws IOException 
	 * @createDate 2016年10月25日 上午11:15:15
	 */
	public static File compressImageLess1MReturnFile(InputStream inputStream) throws IOException{
		File file = new File(System.getProperty("user.home")+File.separator+"tmp.jpg");
		
		Thumbnails.of(inputStream).scale(defaultRatio).toFile(file);
		if(file.length() > (1024 * 1024)){
			InputStream in = new FileInputStream(file);
			compressImageLess1MReturnInputStream(in);
		} else {
			return file;
		}
		return null;
	}
	
	/**
	 * 将图片压缩到1M之内并返回InputStream
	 * @param in
	 * @return
	 * @throws IOException
	 * @author zhoubin
	 * @createDate 2016年12月9日 下午3:32:43
	 */
	public static InputStream compressImageLess1MReturnInputStream(InputStream in) throws IOException{
		
		if (in.available() > (1024 * 1024)) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Thumbnails.of(in).scale(defaultRatio).toOutputStream(out);
			ByteArrayInputStream inn = new ByteArrayInputStream(out.toByteArray());
			out.close();
			return inn;
		} else {
			return in;
		}
	}
	
	/**
	 * 等比例压缩、放大图片
	 * @param inputStream 输入流
	 * @param outputStream 输出流
	 * @param ratio 比例(0.1f/2f)
	 * @return
	 * @author zhoubin
	 * @throws IOException 
	 * @createDate 2016年10月25日 上午11:08:49
	 */
	public static OutputStream equalRatioCompressImage(InputStream inputStreams, OutputStream outputStreams, float ratio) throws IOException{
		Thumbnails.of(inputStreams).scale(ratio).toOutputStream(outputStreams);
		return outputStreams;
	}
	
	/**
	 * 等比例压缩、放大图片
	 * @param fromFile 原始图片
	 * @param toFile 处理后的图片
	 * @param ratio 比例(0.1f/2f)
	 * @return
	 * @author zhoubin
	 * @throws IOException 
	 * @createDate 2016年10月25日 上午11:09:55
	 */
	public static File equalRatioCompressImage(File fromFile, File toFile, float ratio) throws IOException{
		Thumbnails.of(fromFile).scale(ratio).toFile(toFile);
		return toFile;
	}
	
	public static byte[] toBytes(InputStream inputStream){
		try {
			byte[] b = new byte[inputStream.available()];
			inputStream.read(b);
			inputStream.close();
			return b;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}

