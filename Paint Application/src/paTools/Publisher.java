package paTools;
import java.util.*;

public class Publisher {

    Set<Subscriber> subscribers = new HashSet<Subscriber>();

    public void notifySubscribers(){
        for (Subscriber s : subscribers){
            s.update();
        }

    }
    public void subscribe(Subscriber s){
        subscribers.add(s);
    }

    public void unSubscribe(Subscriber s){
        subscribers.remove(s);
    };


}
