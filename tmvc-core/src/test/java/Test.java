import me.kingtux.tmvc.core.Controller;
import me.kingtux.tmvc.core.Model;
import me.kingtux.tmvc.core.Request;
import me.kingtux.tmvc.core.annotations.Path;
import me.kingtux.tmvc.core.annotations.RequestParam;

public class Test implements Controller {
    @Path(path = "/")
    public void test(@RequestParam(key = "hey") String s, Request request, Model model) {

    }
}
