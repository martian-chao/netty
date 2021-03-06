package netty.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class HelloClientIntHandler  extends ChannelInboundHandlerAdapter{
	private static Logger logger = LoggerFactory.getLogger(HelloClientIntHandler.class);  
	  
    // 接收server端的消息，并打印出来  
    @Override  
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
        logger.info("HelloClientIntHandler.channelRead");  
//    	System.out.println("HelloClientIntHandler.channelRead");
        ByteBuf result = (ByteBuf) msg;  
        byte[] result1 = new byte[result.readableBytes()];  
        result.readBytes(result1);  
        System.out.println("Server said:" + new String(result1));  
        result.release();  
    }  
  
    // 连接成功后，向server发送消息  
    @Override  
    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
        logger.info("HelloClientIntHandler.channelActive");  
//    	System.out.println("HelloClientIntHandler.channelActive");
        String msg = "Are you ok?";  
//        ByteBuf encoded = ctx.alloc().buffer(4 * msg.length());  
//        encoded.writeBytes(msg.getBytes());  
//        ctx.write(encoded);  
//        ctx.flush();  
        byte [] req = msg.getBytes();
        ByteBuf buf = Unpooled.buffer(req.length);
        buf.writeBytes(req);
        ctx.writeAndFlush(buf);
        
    }  
}
