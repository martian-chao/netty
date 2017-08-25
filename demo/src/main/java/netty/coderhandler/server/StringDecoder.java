package netty.coderhandler.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import netty.utils.ByteBufToBytes;

/**
 * StringDecoder 把httpRequest转换成String，
 * 其中ByteBufToBytes是一个工具类，负责对ByteBuf中的数据进行读取
 * @author yanChaoLiu
 *
 */
public class StringDecoder extends ChannelInboundHandlerAdapter{
	private static Logger   logger  = LoggerFactory.getLogger(StringDecoder.class);  
    private ByteBufToBytes  reader;  
  
    @Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
        logger.info("StringDecoder : msg's type is " + msg.getClass());  
        System.out.println("StringDecoder : msg's type is " + msg.getClass());
        if (msg instanceof HttpRequest) {  
            HttpRequest request = (HttpRequest) msg;  
            reader = new ByteBufToBytes((int) HttpHeaders.getContentLength(request));  
        }  
  
        if (msg instanceof HttpContent) {  
            HttpContent content = (HttpContent) msg;  
            reader.reading(content.content());  
  
            if (reader.isEnd()) {  
                byte[] clientMsg = reader.readFull();  
                logger.info("StringDecoder : change httpcontent to string ");  
                System.out.println("StringDecoder : change httpcontent to string ");
                ctx.fireChannelRead(new String(clientMsg));  
            }  
        }  
    }  
}
