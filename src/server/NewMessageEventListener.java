package server;


import structs.Message;

class NewMessageEventListener
{
    interface NewGlobalMessageArrived
    {
        void newGlobalMessageArrived();
    }

    interface NewDirectedMessageArrived
    {
        void newDirectedMessageArrived(Message message, String username);
    }
}
