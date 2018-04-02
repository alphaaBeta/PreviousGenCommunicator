package server;


import structs.Message;

interface  NewDirectedMessageEventListener
{
    void newDirectedMessageArrived(Message message, String username);
}
