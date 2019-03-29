import me.kingtux.tuxmvc.WebsiteBuilder;
import me.kingtux.tuxmvc.core.Website;

public class Test {
    public static void main(String[] args) {
        Website site = WebsiteBuilder.createSiteFromLocalProperties();
        site.registerController(new TestController());
        site.getEmailManager().sendEmail("wherkamp@kingtux.me", "Test", "test");
    }
}
