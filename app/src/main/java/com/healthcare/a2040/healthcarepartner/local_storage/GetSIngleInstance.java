package com.healthcare.a2040.healthcarepartner.local_storage;

import java.net.Socket;

/**
 * Created by My Computer on 05-04-2017.
 */
public class GetSIngleInstance {


    private static Socket ourInstance;

    private GetSIngleInstance() {
    }

    public static Socket getInstance() {
        return ourInstance;
    }

    public static void setOurInstance(Socket ourInstance) {
        GetSIngleInstance.ourInstance = ourInstance;
    }


}
