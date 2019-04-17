import me.kingtux.tuxmvc.core.annotations.Controller;
import me.kingtux.tuxmvc.core.request.Request;
import me.kingtux.tuxmvc.core.view.View;
import me.kingtux.tuxmvc.simple.impl.SimpleRequest;

public class TestController {
    @Controller(path="/",template = "index")
    public void index(Request request, View view){


    }
    @Controller(path="/hey", template = "hey")
    public void hey(View view){

    }
}
