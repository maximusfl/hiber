

import helpers.CategoryHelper;
import helpers.MainHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MainTest {
    @BeforeAll
    public static void setUp(){
        new MainHelper().generate_item();
    }


    @Test
    public void changeItemInStorage(){
        new MainHelper().changeItemInStorage();
    }

    @Test
    public void lazyInit() {
        new MainHelper().checkLazyInit();
    }


    @Test
    public void orphanTest() {
        new MainHelper().orphanRemovalTest();
    }

    @Test
    public void purchaseTest(){
        new MainHelper().purchaseHelper();
    }

    @Test
    public void saveListAsField_Without_merge() {
        new CategoryHelper().categories_save_test();
    }


}
