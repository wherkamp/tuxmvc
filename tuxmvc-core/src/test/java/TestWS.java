import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.kingtux.tuxmvc.core.annotations.Websocket;
import me.kingtux.tuxmvc.core.ws.WSHandler;
import me.kingtux.tuxmvc.core.ws.WSSession;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;


@Websocket(path="/ws/test1")
public class TestWS implements WSHandler, Runnable {
    private Map<WSSession, Long> sessions = new HashMap<>();
    private Gson gson = new Gson();
    public TestWS(){
        new Thread(this).start();
    }
    @Override
    public void onConnect(WSSession session) {
        Test.logger.info(session.queryParam("me", "Default")+ " Joined the server");
        sessions.put(session,System.currentTimeMillis());

    }

    @Override
    public void onClose(WSSession session, int statusCode, String reason) {
        sessions.remove(session);
        System.out.println("Bye "+reason);
    }

    @Override
    public void onError(WSSession session, Throwable throwable) {
        sessions.remove(session);
        System.out.println("Error");
        throwable.printStackTrace();
    }

    @Override
    public void onMessage(WSSession session, String message) {
        JsonObject m  = gson.fromJson(message, JsonObject.class);

        if(m.get("goal").getAsString().equals("message")) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("goal", "message");
            jsonObject.addProperty("message", m.get("message").getAsString());
            jsonObject.addProperty("author", session.queryParam("me", "Default"));
            Gson gson = new Gson();
            for (Map.Entry<WSSession, Long> e : sessions.entrySet()) {
                e.getKey().send(gson.toJson(jsonObject));
            }
        }else if(m.get("goal").getAsString().equals("keep-alive")){
            sessions.put(session, System.currentTimeMillis());
        }
    }

    @Override
    public void run() {
        while(true){
            for (Map.Entry<WSSession, Long> e : sessions.entrySet()) {
                if ((System.currentTimeMillis() - e.getValue()) > 150000) {
                    e.getKey().close(9,"no-keep-alive" );
                    continue;
                }
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("goal","keep-alive");
                e.getKey().send(gson.toJson(jsonObject));
            }
            try {
                sleep(150000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
