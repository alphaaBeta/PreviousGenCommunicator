package server;


import structs.Message;

/**
 * Some interfaces handling new messages arriving.
 */
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
