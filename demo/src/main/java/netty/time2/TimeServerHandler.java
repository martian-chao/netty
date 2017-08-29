package netty.time2;
/**
 * 服务处理
 */
import java.text.SimpleDateFormat;
import java.util.Date;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter{
	//计数器
	private int  counter; 
	//分割符
	private static String entry ="\r\n";
	//接收客户端消息
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//业务处理
		//接收客户端数据
		String body = (String)msg;
//		ByteBuf buf = (ByteBuf) msg;
//		byte[] req = new byte[buf.readableBytes()];
//		buf.readBytes(req);
//		String body = new String(req,"UTF-8");
//		Thread.sleep(100);
		System.out.println("服务端第"+(++counter)+"次，接收到客户端的命令："+body);
		//释放资源
//		buf.release();
		
		//向客户端发送数据
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//
		String currenttime="QUERY TIME ORDER".equalsIgnoreCase(body)? 
				simpleDateFormat.format(new Date(System.currentTimeMillis())):"BAD REQUEST";
		//
		//Unpooled缓冲池，通过该缓冲池拿到bytebuf发送缓冲池
		ByteBuf reqs = Unpooled.copiedBuffer((currenttime+entry).getBytes());
		//通过ChannelHandlerContext缓冲池里的内容写给客户端
		ctx.writeAndFlush(reqs);
		
		
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		//将缓冲池中的消息全部写出去
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		if (this==ctx.pipeline().last()) {
			System.out.println("用户:"+ctx.channel().id()+" 异常下线");
		}
		ctx.channel().close();
		ctx.close();
	}
	
	

}
