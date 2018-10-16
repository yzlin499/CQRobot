import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import java.util.List;

public class CQRobotAppMain {

    public static void main(String[] args) {
        ApplicationContext context=new FileSystemXmlApplicationContext("classpath:springconfig/main-config.xml");
        List <Thread> threadList=context.getBean("threadList",List.class);
        threadList.forEach(Thread::start);
    }
}
