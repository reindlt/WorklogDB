package swt6.statistic;

import swt6.orm.dao.IssueDaoImpl;
import swt6.orm.dao.TaskDAOImpl;
import swt6.orm.dao.UserStoryDAOImpl;
import swt6.orm.dao.interfaces.IssueDAO;
import swt6.orm.dao.interfaces.TaskDAO;
import swt6.orm.dao.interfaces.UserStoryDAO;

public class Statistics {
    private static final int BUGGY_RELEASES = 5;
    private IssueDAO issueDao = new IssueDaoImpl();
    private TaskDAO taskDAO = new TaskDAOImpl();
    private UserStoryDAO userStoryDAO = new UserStoryDAOImpl();

    public void printLeast5BuggyReleases() {
        var items = issueDao.getFromQuery(
                "select i.releaseVersion, count(i.id) as bugs " +
                        "from Issue i " +
                        " group by i.releaseVersion " +
                        " order by bugs");

        System.out.println(BUGGY_RELEASES + " most buggy releases are:");
        for (var i = 0; i < items.size() && i < BUGGY_RELEASES; i++) {
            System.out.println("--> " + (String) items.get(i)[0] + "(" + (long) items.get(i)[1] + ")");
        }
    }

    public void printEstimationPointRatio() {
        var items = issueDao.getFromQuery(
                "select sum(t.points), us.storyPoints " +
                        "from Task t " +
                        "join t.userStory us " +
                        "group by us.id, us.storyPoints");

        double sum = 0;
        for (var i = 0; i < items.size(); i++) {
            double taskPoints = (double)(long) items.get(i)[0];
            double storyPoints = (double)(int) items.get(i)[1];
            sum += taskPoints / storyPoints ;
        }
        System.out.println("Estimation point ratio is: " + sum / items.size());
    }

    public void printFeatureists() {
        var items = issueDao.getFromQuery(
                "select l.employee.firstName, max(l.endTime), t.dueDate " +
                        "from LogbookEntry l " +
                        "join l.employee e " +
                        "join l.task t " +
                        "where TYPE(t) = Feature " +
                        "group by l.task, t.dueDate ");
    }

    public void printFeatureStateForCurrentSprint() {

    }
}
