

import helpers.CategoryHelper;
import helpers.MainHelper;
import helpers.QueryHelper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class MainTest {
    @Test
    public  void setUp(){
        new MainHelper().generate_item();
    }

    @Test
    public void criteriaTest() {
        new QueryHelper().criteria_3();
    }

    @Test
    public void clearTest() {
        new MainHelper().clearBeforeCommit();
    }

    @Test
    public void changeItemInStorage(){
        new MainHelper().changeItemInStorage();
    }

    @Test
    public void lazyInit() {
        new MainHelper().generate_item();
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

    @Test
    public void changeItemListInCategory(){
        new CategoryHelper().changeItemListInCategory();
    }


}
