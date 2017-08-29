package netty.multihandler.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class OutboundHandler1 extends ChannelOutboundHandlerAdapter{
	private static Logger   logger  = LoggerFactory.getLogger(OutboundHandler1.class);  
    @Override  
    // 向client发送消息  
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {  
        logger.info("OutboundHandler1.write");  
//        System.out.println("OutboundHandler1.write");
        String response = "I am ok!";  
//        ByteBuf buf = Unpooled.buffer(response.length());
//        buf.writeBytes(response.getBytes());  
        ByteBuf buf = Unpooled.copiedBuffer(response.getBytes());
        ctx.writeAndFlush(buf);
    }  
    
}
