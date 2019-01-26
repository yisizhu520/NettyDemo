import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.nio.charset.Charset;

public class ByteBufTest {

    @Test
    public void testSlice() {
        char a = '啊';
        System.out.println(a);
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty啊 in Action rocks!", utf8);  //← --  创建一个用于保存给定字符串的字节的 ByteBuf
        ByteBuf sliced = buf.slice(0, 15);  //← --  创建该 ByteBuf 从索引0 开始到索引15结束的一个新切片
        System.out.println(sliced.toString(utf8));  // ← --  将打印“Netty in Action”
        buf.setByte(0, (byte) 'J');  // ← --  更新索引0 处的字节
        assert buf.getByte(0) == sliced.getByte(0); //← --  将会成功，因为数据是共享的，对其中一个所做的更改对另外一个也是可见的
        System.out.println(sliced.toString(utf8));
    }

    @Test
    public void sss() throws Exception {
        char c = '\uD87E';
        System.out.printf("The value of char %c is %d.%n", c, (int) c);

        String str = String.valueOf(c);
        byte[] bys = str.getBytes("Unicode");
        for (int i = 0; i < bys.length; i++) {
            System.out.printf("%X ", bys[i]);
        }
        System.out.println();

        int unicode = (bys[2] & 0xFF) << 8 | (bys[3 & 0xFF]);
        System.out.printf("The unicode value of %c is %d.%n", c, unicode);
    }


}
