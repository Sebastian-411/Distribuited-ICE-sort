import com.zeroc.Ice.Current;

import Sorting.Response;

public class CallbackImpl implements Sorting.Callback {

    @Override
    public void callbackClient(Response response, Current current) {
        System.out.println(response.value);
    }
    
}