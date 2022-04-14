package swt6.orm.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import swt6.orm.dao.IssueDaoImpl;
import swt6.orm.dao.UserStoryDAOImpl;
import swt6.orm.dao.interfaces.IssueDAO;
import swt6.orm.dao.interfaces.UserStoryDAO;
import swt6.orm.domain.Issue;
import swt6.orm.domain.UserStory;
import swt6.statistic.Statistics;
import swt6.util.JpaUtil;

public class StatisticsTests {

    Statistics statistics = new Statistics();
    private IssueDAO issueDao = new IssueDaoImpl();
    private UserStoryDAO userStoryDAO = new UserStoryDAOImpl();

    @Before
    public void init(){
        JpaUtil.getTransactedEntityManager();
    }

    @After
    public void cleanup(){
        JpaUtil.closeEntityManagerFactory();
    }

    @Test
    public void least5BuggyReleasesTest() {
        var issue = new Issue();
        issue.setTitle("Test1");
        issue.setReleaseVersion("Release1");
        issueDao.insert(issue);

        issue.setTitle("Test2");
        issue.setReleaseVersion("Release1");
        issueDao.insert(issue);

        issue.setTitle("Test3");
        issue.setReleaseVersion("Release2");
        issueDao.insert(issue);


        statistics.printLeast5BuggyReleases();
    }

    @Test
    public void estimationPointRatioTest() {
        var userStory = new UserStory();
        userStory.setTitle("Story1");
        userStory.setStoryPoints(10);

        var issue = new Issue();
        issue.setTitle("Test1");
        issue.setReleaseVersion("Release1");
        issue.setPoints(10);

        userStory.addTask(issue);

        var issue1 = new Issue();
        issue1.setTitle("Test2");
        issue1.setReleaseVersion("Release1");
        issue1.setPoints(5);

        userStory.addTask(issue1);

        userStoryDAO.insert(userStory);

        statistics.printEstimationPointRatio();
    }

    @Test
    public void featureistsTest() {
        statistics.printFeatureists();
    }
}
