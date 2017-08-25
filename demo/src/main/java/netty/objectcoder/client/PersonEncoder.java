package netty.objectcoder.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import netty.objectcoder.bean.Person;
import netty.objectcoder.utils.ByteObjConverter;

/**
 * 把Person对象转换成ByteBuf进行传送
 * @author yanChaoLiu
 *
 */
public class PersonEncoder extends MessageToByteEncoder<Person>{
	@Override  
    protected void encode(ChannelHandlerContext ctx, Person msg, ByteBuf out) throws Exception {  
        byte[] datas = ByteObjConverter.objectToByte(msg);  
        out.writeBytes(datas);  
        ctx.flush();  
    }  
}
