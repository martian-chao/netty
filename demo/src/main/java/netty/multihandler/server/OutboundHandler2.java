package netty.multihandler.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class OutboundHandler2 extends ChannelOutboundHandlerAdapter{
	private static Logger   logger  = LoggerFactory.getLogger(OutboundHandler2.class);  
    
    @Override  
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {  
        logger.info("OutboundHandler2.write");  
//    	System.out.println("OutboundHandler2.write");
        // 执行下一个OutboundHandler  
//        super.write(ctx, msg, promise);  
        ctx.write(msg);
    }  
}
