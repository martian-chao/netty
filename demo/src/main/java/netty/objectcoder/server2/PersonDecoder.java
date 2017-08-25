package netty.objectcoder.server2;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import netty.objectcoder.utils.ByteBufToBytes;
import netty.objectcoder.utils.ByteObjConverter;
/**
 *  把二进制流转换成Person对象
 *  
 *  PersonDecoder和StringDecoder中有一个if判断，
 *  是为了判断消息究竟是什么协议。如果是String协议的话，
 *  格式是【name:xx;age:xx;sex:xx;】，第一个字母是英文字母n，
 *  所以判断协议类型时候是读取二进制流的第一个字符进行判断，
 *  当然这种判断方式非常幼稚，以后有机会可以进行改善。
 * @author yanChaoLiu
 *
 */
public class PersonDecoder extends ByteToMessageDecoder{
	@Override  
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {  
        byte n = "n".getBytes()[0];  
        byte p = in.readByte();  
        in.resetReaderIndex();  
        if (n != p) {  
        	System.out.println("PersonDecoder--n != p");
            // 把读取的起始位置重置  
            ByteBufToBytes reader = new ByteBufToBytes();  
            out.add(ByteObjConverter.byteToObject(reader.read(in)));  
        } else {  
        	//说明为字符串字节流
        	System.out.println("PersonDecoder--n == p");
            // 执行其它的decode  
            ctx.fireChannelRead(in);  
        }  
    } 
}
