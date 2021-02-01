package com.tireadev.shadowengine.messaging;

import java.util.ArrayList;

public class MessageBus {

    private static final ArrayList<EMessages> messagesBuffer = new ArrayList<>();

    public static boolean dispatchMessage(EMessages eMessage) {
        return messagesBuffer.add(eMessage);
    }

    public static boolean getMessage(EMessages eMessage) {
        for (EMessages eMessageFor : messagesBuffer)
            if (eMessage == eMessageFor) {
                messagesBuffer.remove(eMessageFor);
                return true;
            }
        return false;
    }

    public static ArrayList<EMessages> getMessages() {
        return messagesBuffer;
    }

    public static boolean removeMessage(EMessages eMessage) {
        return messagesBuffer.remove(eMessage);
    }

}
