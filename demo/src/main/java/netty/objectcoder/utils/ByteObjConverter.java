package netty.objectcoder.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
/**
 * byte和obj的互相转换的工具。
 * @author yanChaoLiu
 *
 */
public class ByteObjConverter {
	/**
	 * 将字节数组转为对象
	 * @param bytes
	 * @return
	 */
	 public static Object byteToObject(byte[] bytes) {  
	        Object obj = null;  
	        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);  
	        ObjectInputStream oi = null;  
	        try {  
	            oi = new ObjectInputStream(bi);  
	            obj = oi.readObject();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                bi.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	            try {  
	                oi.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return obj;  
	    }  
	  	/**
	  	 * 将对象转为字节数组
	  	 * @param obj
	  	 * @return
	  	 */
	    public static byte[] objectToByte(Object obj) {  
	        byte[] bytes = null;  
	        ByteArrayOutputStream bo = new ByteArrayOutputStream();  
	        ObjectOutputStream oo = null;  
	        try {  
	            oo = new ObjectOutputStream(bo);  
	            oo.writeObject(obj);  
	            bytes = bo.toByteArray();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                bo.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	            try {  
	                oo.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	        return (bytes);  
	    } 
}
