package swt6.orm.dao;

import org.junit.*;
import swt6.orm.dao.base.DataSourceDBUnitTest;
import swt6.orm.dao.interfaces.SprintDAO;
import swt6.orm.domain.Sprint;
import swt6.orm.domain.UserStory;

import java.time.LocalDate;

public class SprintDAOTests extends DataSourceDBUnitTest {

    SprintDAO dao = new SprintDAOImpl();
    Sprint testSprint = new Sprint();

//    @Before
//    public void init(){
//        JpaUtil.getTransactedTestEntityManager();
//    }
//
//    @After
//    public void cleanup(){
//        JpaUtil.closeEntityManagerFactory();
//    }

    @Test
    public void testInsert() {
        dao.insert(testSprint);
    }

    @Test
    public void testGetById() {
//        dao.insert(testSprint);
//        Assert.assertEquals(testSprint, dao.getById(Sprint.class, 1));
        var Sprint = dao.getById(Sprint.class, 1);
    }

    @Test
    public void updateSprintTest() {
        dao.insert(testSprint);
        testSprint.setStartDate(LocalDate.now());
        var updatedSprint = dao.update(testSprint);
        Assert.assertEquals(testSprint.getStartDate(), updatedSprint.getStartDate());
    }

    @Test
    public void deleteSprintTest(){
        dao.insert(testSprint);
        dao.delete(testSprint);
        Assert.assertEquals(null, dao.getById(Sprint.class, 1));
    }

    @Test
    public void insertSprintWithSeveralUserStoriesTest() {
        UserStory story1 = new UserStory();
        story1.setTitle("story1");
        UserStory story2 = new UserStory();
        story2.setTitle("story2");
        testSprint.addUserStory(story1);
        testSprint.addUserStory(story2);
        dao.insert(testSprint);
    }

}
