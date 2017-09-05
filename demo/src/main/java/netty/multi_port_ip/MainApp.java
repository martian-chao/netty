package netty.multi_port_ip;


import java.io.FileInputStream;
import java.util.*;

/**
 * Created by yanChaoLiu on 2017/9/1.
 */
public class MainApp {

//    InputStream in = Object.getClass().getResourceAsStream("netty/app/a.properties");
    public static void main(String[] args) {
        try {
            //读取配置文件
            Properties pro = new Properties();
            FileInputStream in = new FileInputStream("a.properties");
            pro.load(in);

            Iterator<String> it = pro.stringPropertyNames().iterator();
            //配置文件数据的临时存储容器
            Map parameters = Collections.synchronizedMap(new HashMap());
            while(it.hasNext())
            {
                String keyStr = it.next();
                parameters.put(keyStr, pro.getProperty(keyStr));
            }
            System.out.println("map中的数据为："+parameters.size());

            MyCommConfig.refresh(parameters);


        } catch (Exception e) {
            e.printStackTrace();
        }




    }
}
