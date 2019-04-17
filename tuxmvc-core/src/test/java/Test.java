import io.javalin.Javalin;
import me.kingtux.tuxmvc.WebsiteBuilder;
import me.kingtux.tuxmvc.core.Website;
import me.kingtux.tuxmvc.simple.impl.SimpleWSSession;
import me.kingtux.tuxmvc.simple.impl.SimpleWebsite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {
    static Logger logger = LoggerFactory.getLogger(Test.class);
        public static void main(String[] args) {
        Website site = WebsiteBuilder.createSiteFromLocalProperties();
        site.registerController(new TestController());
        site.registerWebsocketHandler(new TestWS());
       // site.getEmailManager().sendEmail("wherkamp@kingtux.me"," You Are gay", site.getViewManager().buildView(site).setTemplate("index"));
            SimpleWebsite ss = (SimpleWebsite) site;
            Javalin javalin = ss.getJavalin();
            javalin.ws("/ws/test/2", ws -> {
                ws.onConnect(session ->{
                    session.send("Welcome");
                });
                ws.onMessage(wsMessageContext -> {
                    wsMessageContext.send("Test");
                });
                ws.onError(e -> {

                });
                ws.onClose(c ->{

                });
            });
        }
}
