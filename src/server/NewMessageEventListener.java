package server;


import structs.Message;

class NewMessageEventListener
{
    interface NewGlobalMessageArrived
    {
        void newGlobalMessageArrived();
    }

    interface  NewDirectedMessageEventListener
    {
        void newDirectedMessageArrived(Message message, String username);
    }
}
