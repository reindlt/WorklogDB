package swt6.orm.tests;

import org.junit.*;
import swt6.orm.dao.SprintDAOImpl;
import swt6.orm.dao.interfaces.SprintDAO;
import swt6.orm.domain.Sprint;
import swt6.orm.domain.UserStory;
import swt6.util.JpaUtil;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class SprintTests {

    SprintDAO dao = new SprintDAOImpl();
    Sprint testSprint = new Sprint();

    @Before
    public void init(){
        JpaUtil.getTransactedEntityManager();
    }

    @After
    public void cleanup(){
        JpaUtil.closeEntityManagerFactory();
    }

    @Test
    public void insertSprintTest() {
        dao.insert(testSprint);
    }

    @Test
    public void getSprintTest() {
        dao.insert(testSprint);
        Assert.assertEquals(testSprint, dao.get(Sprint.class, 1));
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
        Assert.assertEquals(null, dao.get(Sprint.class, 1));
    }

    @Test
    public void insertSprintWithSeveralUserStoriesTest() {
        UserStory story1 = new UserStory();
        story1.setTitle("story1");
        UserStory story2 = new UserStory();
        story2.setTitle("story2");
        //UserStory[] stories = { story1, story2};
        testSprint.addUserStory(story1);
        testSprint.addUserStory(story2);
        dao.insert(testSprint);
    }

}
