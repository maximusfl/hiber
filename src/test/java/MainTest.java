

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MainTest {
    @BeforeAll
    public static void setUp(){
        new Helper().generate_item();
    }


    @Test
    public void changeItemInStorage(){
        new Helper().changeItemInStorage();
    }

    @Test
    public void lazyInit() {
        new Helper().checkLazyInit();
    }


    @Test
    public void orphanTest() {
        new Helper().orphanRemovalTest();
    }

    @Test
    public void purchaseTest(){
        new Helper().purchaseHelper();
    }


}
