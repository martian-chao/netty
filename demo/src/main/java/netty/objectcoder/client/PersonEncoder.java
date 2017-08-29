package netty.objectcoder.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import netty.objectcoder.bean.Person;
import netty.objectcoder.server.PersonDecoder;
import netty.objectcoder.utils.ByteObjConverter;

/**
 * 把Person对象转换成ByteBuf进行传送
 * @author yanChaoLiu
 *
 */
public class PersonEncoder extends MessageToByteEncoder<Person>{
	private Logger  logger  = LoggerFactory.getLogger(PersonEncoder.class);
	@Override  
    protected void encode(ChannelHandlerContext ctx, Person msg, ByteBuf out) throws Exception {  
		logger.info("PersonEncoder");
        byte[] datas = ByteObjConverter.objectToByte(msg);  
        out.writeBytes(datas);  
        ctx.flush();  
    }  
}
