package netty.objectcoder.server;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import netty.objectcoder.utils.ByteBufToBytes;
import netty.objectcoder.utils.ByteObjConverter;

/**
 * 把ByteBuf流转换成Person对象，其中ByteBufToBytes是读取ButeBuf的工具类，
 * ByteObjConverter是byte和obj的互相转换的工具。
 * @author yanChaoLiu
 *
 */
public class PersonDecoder extends ByteToMessageDecoder{
	private Logger  logger  = LoggerFactory.getLogger(PersonDecoder.class);
	  @Override  
	    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {  
		  	logger.info("PersonDecoder");
	        ByteBufToBytes read = new ByteBufToBytes();  
	        Object obj = ByteObjConverter.byteToObject(read.read(in));  
	        out.add(obj);  
	    }  
}
