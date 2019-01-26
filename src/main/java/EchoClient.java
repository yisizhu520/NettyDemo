import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class EchoClient {

    private static final String host = "localhost";
    private static final int port = 8888;

    public static void main(String[] args) throws Exception {
        new EchoClient().start();
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {    //←--  创建Bootstrap
            Bootstrap b = new Bootstrap();     //←--  指定 EventLoopGroup 以处理客户端事件；需要适用于 NIO 的实现
            b.group(group)
                    .channel(NioSocketChannel.class)     //←--  适用于 NIO 传输的 Channel 类型
                    .remoteAddress(new InetSocketAddress(host, port))     //←--  设置服务器的 InetSocketAddr-ess
                    .handler(new ChannelInitializer<SocketChannel>() {    //←--  在创建 Channel 时，向 ChannelPipeline 中添加一个 Echo-ClientHandler 实例
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(
                                    new EchoClientHandler());
                        }
                    });
            ChannelFuture f = b.connect().sync();     //←--  连接到远程节点，阻塞等待直到连接完成
            f.channel().closeFuture().sync();     // ←--  阻塞，直到 Channel 关闭
        } finally {
            group.shutdownGracefully().sync();      // ←--  关闭线程池并且释放所有的资源
        }
    }

}
