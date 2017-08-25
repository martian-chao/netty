package netty.objectcoder.client2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import netty.objectcoder.bean.Person;

/**
 * StringEncoder 把Person对象转换成固定格式的String的二进制流进行传送
 * 
 * PersonDecoder和StringDecoder中有一个if判断，
 *  是为了判断消息究竟是什么协议。如果是String协议的话，
 *  格式是【name:xx;age:xx;sex:xx;】，第一个字母是英文字母n，
 *  所以判断协议类型时候是读取二进制流的第一个字符进行判断，
 *  当然这种判断方式非常幼稚，以后有机会可以进行改善。
 * @author yanChaoLiu
 *
 */
public class StringEncoder extends MessageToByteEncoder<Person>{
	 @Override  
	    protected void encode(ChannelHandlerContext ctx, Person msg, ByteBuf out) throws Exception {  
		 	System.out.println("StringEncoder");
	        // 转成字符串：name:xx;age:xx;sex:xx;  
	        StringBuffer sb = new StringBuffer();  
	        sb.append("name:").append(msg.getName()).append(";");  
	        sb.append("age:").append(msg.getAge()).append(";");  
	        sb.append("sex:").append(msg.getSex()).append(";");  
	        out.writeBytes(sb.toString().getBytes());  
	    }  
}
