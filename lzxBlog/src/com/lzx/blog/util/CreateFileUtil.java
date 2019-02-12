package com.lzx.blog.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.channels.FileChannel;

import org.apache.struts2.ServletActionContext;

import sun.misc.BASE64Decoder;

public class CreateFileUtil {

    /**
     * 生成.json格式文件
     */
	private static File articleJSONfile = new File(ServletActionContext.getServletContext().getRealPath("articleJSON")); //文章json存放路径
	private static File ImgJSONfile = new File(ServletActionContext.getServletContext().getRealPath("page")); 	//图片路劲
	public static File bgImg = new File(ImgJSONfile + "\\img\\user_bgimg");	//背景图片路径
	public static File LogoImg = new File(ImgJSONfile + "/img/user_logo/custom");	//背景图片路径
	
    public static boolean createJsonFile(String jsonString, String fileName, String time) {
        // 标记文件生成是否成功
        boolean flag = true;
        // 拼接文件完整路径
        
        //判断文件是否上传，如果上传的话将会创建该目录  
        if(!articleJSONfile.exists()){  
        	articleJSONfile.mkdir(); //创建该目录   
        }
        File file2=new File(articleJSONfile + "\\" +time);
        System.out.println(file2);
        if(!file2.exists()){  
        	file2.mkdir(); //创建该目录   
        }
        FileOutputStream out=null;
        // 生成json格式文件
        try {
            // 保证创建一个新文件
            out=new FileOutputStream(file2 + "\\" +fileName + ".json");
            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(out, "UTF-8");
            write.write(jsonString);
            write.flush();
            write.close();
        } catch (Exception e) {
            flag = false;
            System.out.println("---文件生成发生错误---");
            e.printStackTrace();
        }

        // 返回是否成功的标记
        return flag;
    }
    public static String readJSONFile(String updateTime, String address){
    	
    	File file = new File(articleJSONfile + "\\" + updateTime + "\\" + address + ".json");
    	String jsonStr = null;
    	 try {
    		    if(file.isFile() && file.exists()) {
    		      InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
    		      BufferedReader br = new BufferedReader(isr);
    		      StringBuffer sb = new StringBuffer();
    		      
    		      while((jsonStr = br.readLine()) != null){
                      sb.append(jsonStr);
                  }
    		      br.close();
    		      isr.close();
    		      
    		      return sb.toString();
    		      
    		    } else {
    		      System.out.println("文件不存在!");
    		    }
    		  } catch (Exception e) {
    		    System.out.println("文件读取错误!");
    		  }
    	
		return jsonStr; 	
    }
    
   public static String readBGimgJSONFile(String updateTime, String address){
    	
    	File file = new File(bgImg + updateTime + "\\" + address + ".json");
    	String jsonStr = null;
    	 try {
    		    if(file.isFile() && file.exists()) {
    		      InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
    		      BufferedReader br = new BufferedReader(isr);
    		      StringBuffer sb = new StringBuffer();
    		      
    		      while((jsonStr = br.readLine()) != null){
                      sb.append(jsonStr);
                  }
    		      br.close();
    		      isr.close();
    		      
    		      return sb.toString();
    		      
    		    } else {
    		      System.out.println("文件不存在!");
    		    }
    		  } catch (Exception e) {
    		    System.out.println("文件读取错误!");
    		  }
    	
		return jsonStr; 	
    }

	/**
	 * @Description: 将base64编码字符串转换为图片
	 * @Author:
	 * @CreateTime:
	 * @param imgStr
	 *            base64编码字符串
	 * @param path
	 *            图片路径-具体到文件
	 * @return
	 */
	public static boolean generateImage(String imgStr, String path, String file) {
		if (imgStr == null || imgStr.equals("")){return false;};
		
		
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			 File file2=new File(LogoImg + "/" + path);
		        System.out.println(file2);
		        if(!file2.exists()){  
		        	file2.mkdir(); //创建该目录   
		        }
			
			// 解密
			byte[] b = decoder.decodeBuffer(imgStr);
			// 处理数据
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			OutputStream out = new FileOutputStream(file2 + "/" +  file);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
    
	//复制文件
	public static boolean copyFileUsingFileChannels(File source,File dest,String fileAddress,String fileName){    
	        FileChannel inputChannel = null;    
	        FileChannel outputChannel = null;    
	        boolean flag = true;
	    try {
	        try {
	        	
				inputChannel = new FileInputStream(source + fileAddress + fileName).getChannel();
				File file=new File(new File(dest + fileAddress).getAbsolutePath());
				if(!file.exists()){
					file.mkdirs();
				}
				outputChannel = new FileOutputStream(dest + fileAddress + fileName).getChannel();
			    outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
			} catch (IOException e) {
				flag = false;
				System.out.println("-----复制文件时出错-----");
				e.printStackTrace();
			}
	       
	    } finally {
	        try {
				inputChannel.close();
				outputChannel.close();
			} catch (IOException e) {
				flag = false;
				System.out.println("-----关闭复制文件功能时出错-----");
				e.printStackTrace();
			}
	    }
	    
	    return flag;
	}
	
	

}