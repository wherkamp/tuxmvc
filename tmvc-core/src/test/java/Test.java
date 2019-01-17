import me.kingtux.tmvc.core.Controller;
import me.kingtux.tmvc.core.annotations.Path;
import me.kingtux.tmvc.core.annotations.RequestParam;
import me.kingtux.tmvc.core.request.Request;
import me.kingtux.tmvc.core.view.View;

import java.lang.reflect.Method;

public class Test implements Controller {
    @Path(path = "/")
    public void test(@RequestParam(key = "hey") String s, String[] args, Request request, View view) {
        //String[] will be the path requested so /test test will be returned in this situation.
        view.setTemplate("mytemplate").set("hey", s).render();

    }
}
