package tests;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.runner.RunWith;
import structs.UserStorage;

/** http://arquillian.org/guides/getting_started/ */

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class UserStorageTest
{
    @Deployment
    public static JavaArchive createDeployment()
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(UserStorage.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @org.junit.Test
    public void get_currentUsers()
    {
    }

    @org.junit.Test
    public void addCurrentUser()
    {
    }

    @org.junit.Test
    public void removeCurrentUser()
    {
    }

    @org.junit.Test
    public void getUserInfo()
    {
    }
}
