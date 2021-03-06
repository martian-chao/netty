package nettyconf.protocol.handler;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import nettyconf.protocol.vo.Header;
import nettyconf.protocol.vo.MessageType;
import nettyconf.protocol.vo.NettyMessage;

/**
 * 服务器登录返回handler
 *
 */
public class LoginResponseHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(LoginResponseHandler.class);
    //已登录的ip容器
    private static ConcurrentHashMap<String, String> onlineNode = new ConcurrentHashMap<String, String>();
    private static final String value = "online";
    private static Set<String> whiteHost = new HashSet<String>() {
        {
            //加载白名单
            add("127.0.0.1");
        }
    };

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("LoginResponseHandler-channelRead");
        NettyMessage request = (NettyMessage) msg;
        //判断是否握手消息
        if (request != null && request.getHeader().getType() == MessageType.ACCEPT_REQ) {
            if (request.getBody() == null || !request.getBody().equals("Hello Netty Private Protocol.")) {
                ctx.close();
            } else {
                System.out.println("登录成功,进行白名单、是否重复连接校验.");
                String address = ((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress();
                if (!whiteHost.contains(address)) {
                    System.out.println("该IP禁止访问服务器，断开链接.");
                    ctx.close();
                }
                if (onlineNode.containsKey(address)) {
                    System.out.println("该IP重复登录服务器，断开链接.");
                    ctx.close();
                }
                onlineNode.put(address, value);
                NettyMessage response = new NettyMessage();
                Header header = new Header();
                header.setType(MessageType.ACCEPT_RES);
                header.setSessionID(1);
                response.setHeader(header);
                response.setBody("登录成功，返回信息.");
                ctx.writeAndFlush(response);
            }
        } else {
            ctx.fireChannelRead(msg);
        }
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        onlineNode.remove(((InetSocketAddress) ctx.channel().remoteAddress()).getAddress().getHostAddress());
        System.out.println("关闭链接，释放资源，删除已登录信息防止客户端登录不了.");
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }
}