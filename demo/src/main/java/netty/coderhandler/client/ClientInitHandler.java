package netty.coderhandler.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import netty.coderhandler.server.BusinessHandler;
import netty.utils.ByteBufToBytes;

/**
 * ClientInitHandler 从Server端读取响应信息
 * @author yanChaoLiu
 *
 */
public class ClientInitHandler extends ChannelInboundHandlerAdapter{
	private Logger  logger  = LoggerFactory.getLogger(ClientInitHandler.class);
	 private ByteBufToBytes  reader;  
	  //接收服务器的消息
	    @Override  
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
	    	logger.info("ClientInitHandler_channelRead"+msg.getClass());
	        if (msg instanceof HttpResponse) {  
	            HttpResponse response = (HttpResponse) msg;  
	            if (HttpHeaders.isContentLengthSet(response)) {  
	                reader = new ByteBufToBytes((int) HttpHeaders.getContentLength(response));  
	            }  
	        }  
	  
	        if (msg instanceof HttpContent) {  
	            HttpContent httpContent = (HttpContent) msg;  
	            ByteBuf content = httpContent.content();  
	            reader.reading(content);  
	            content.release();  
	  
	            if (reader.isEnd()) {  
	                String resultStr = new String(reader.readFull());  
	                System.out.println("Server said:" + resultStr);  
	            }  
	        }  
	    }  
	  
	    @Override  
	    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {  
	        ctx.close();  
	    }  

}
