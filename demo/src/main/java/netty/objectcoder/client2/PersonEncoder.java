package netty.objectcoder.client2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import netty.objectcoder.bean.Person;
import netty.objectcoder.utils.ByteObjConverter;

/**
 * PersonEncoder 把Person对象转换成二进制进行传送
 * @author yanChaoLiu
 *
 */
public class PersonEncoder extends MessageToByteEncoder<Person>{
	 @Override  
	    protected void encode(ChannelHandlerContext ctx, Person msg, ByteBuf out) throws Exception {  
		 	System.out.println("PersonEncoder");
	        out.writeBytes(ByteObjConverter.objectToByte(msg));  
	    } 
}
