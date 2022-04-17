package swt6.statistic;

import swt6.orm.dao.*;
import swt6.orm.dao.interfaces.*;
import swt6.orm.domain.*;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class Statistics {
    private static final int BUGGY_RELEASES = 5;

    private final IssueDAO issueDAO = new IssueDAOImpl();
    private final UserStoryDAO userStoryDAO = new UserStoryDAOImpl();
    private final SprintDAO sprintDAO = new SprintDAOImpl();
    private final FeatureDAO featureDAO = new FeatureDAOImpl();
    private final LogbookEntryDAO logbookEntryDAO = new LogbookEntryDAOImpl();

    public void printLeast5BuggyReleases() {
//        System.out.println(BUGGY_RELEASES + " most buggy releases are:");
//        var result = issueDAO.getLeastNBuggyReleases(BUGGY_RELEASES);
//        for (var item : result) {
//            System.out.println(item);
//        }

//        Variante 2
        var issues = issueDAO.getAll(Issue.class);
        Map<String, Integer> releaseWithBugs = new HashMap<>();

        for (var issue : issues) {
            String releaseVersion = issue.getReleaseVersion();
            if (releaseWithBugs.containsKey(releaseVersion)) {
                releaseWithBugs.computeIfPresent(releaseVersion, (key, value) -> value + 1);
            } else {
                releaseWithBugs.put(releaseVersion, 1);
            }
        }

        var list = new ArrayList<>(releaseWithBugs.entrySet());
        list.sort(Map.Entry.<String, Integer>comparingByValue().reversed());

        System.out.println(BUGGY_RELEASES + " most buggy releases are:");
        for (var i = 0; i < list.size() && i < BUGGY_RELEASES; i++) {
            System.out.println("--> " + list.get(i).getKey() + "(" + list.get(i).getValue() + ")");
        }

//        Variante 1
//        var items = issueDao.getFromQuery(
//                "select i.releaseVersion, count(i.id) as bugs " +
//                        "from Issue i " +
//                        " group by i.releaseVersion " +
//                        " order by bugs desc");
//
//        System.out.println(BUGGY_RELEASES + " most buggy releases are:");
//        for (var i = 0; i < items.size() && i < BUGGY_RELEASES; i++) {
//            System.out.println("--> " + (String) items.get(i)[0] + "(" + (long) items.get(i)[1] + ")");
//        }
    }

    public void printEstimationPointRatio() {
        var sprints = sprintDAO.getAll(Sprint.class);

        long numOfSprints = 0;
        long sumStoryPoints = 0;
        long sumPoints = 0;
        double average = 0;

        for (var sprint : sprints) {
            numOfSprints++;

            var userStories = userStoryDAO.getForSprint(sprint);

            for (var userStory : userStories) {
                sumStoryPoints += userStory.getStoryPoints();

                for (var task : userStory.getTasks()) {
                    if ((task instanceof Issue && ((Issue) task).getFixedDate() != null)
                            || (task instanceof Feature && ((Feature) task).getStatus().equals(Status.done))) {
                        sumPoints += task.getPoints();
                    }
                }
            }

            average += sumPoints / (double)sumStoryPoints;
            sumPoints = 0;
            sumStoryPoints = 0;
        }

        System.out.println("Estimation point ratio is: " + average / numOfSprints);

//        var pointsPerUserStory = userStoryDAO.getPointsPerUserStory();
//
//        for (var sprint : storyPointsPerSprint) {
//            for (var task : sprint.getSprint().getUserStories()) {
//                System.out.println();
//            }
//        }

//        var items = issueDao.getFromQuery(
//                "select sum(t.points), sum(us.storyPoints) " +
//                        "from Task t " +
//                        "join t.userStory us " +
//                        "group by us.sprintId");
//
//        double sum = 0;
//        for (var i = 0; i < items.size(); i++) {
//            double taskPoints = (double)(long) items.get(i)[0];
//            double storyPoints = (double)(int) items.get(i)[1];
//            sum += taskPoints / storyPoints ;
//        }
//        System.out.println("Estimation point ratio is: " + sum / items.size());
    }

    public void printFeatureists() {
//        var items = issueDao.getFromQuery(
//                "select l.employee.firstName, max(l.endTime), t.dueDate " +
//                        "from LogbookEntry l " +
//                        "join l.employee e " +
//                        "join l.task t " +
//                        "where TYPE(t) = Feature " +
//                        "group by l.task, t.dueDate ");

        HashSet<Employee> featureists = new HashSet<>();
        var features = featureDAO.getWithStatus(Status.done);

        for (var feature : features) {
            LogbookEntry logbookEntry = null;
            try {
                logbookEntry = logbookEntryDAO.getLatestForTask(feature);
                if (logbookEntry.getEndTime().toLocalDate().isBefore(feature.getDueDate())) {
                    featureists.add(logbookEntry.getEmployee());
                }
            } catch (NoResultException e) {

            }
        }

        if (featureists.size() > 0) {
            System.out.println("Featureists are:");
            for (var featureist : featureists) {
                System.out.println(featureist);
            }
        } else {
            System.out.println("There are no featureists");
        }

    }

    public void printFeatureStateForCurrentSprint() {
        var activeSprints = sprintDAO.getActiveSprints();

        for (var sprint : activeSprints) {
            var features = featureDAO.getForSprint(sprint);
            int numOfFeatures = features.size();

            if (numOfFeatures == 0) {
                System.out.println("No features in " + sprint);
                return;
            }

            int numOfDoneFeatures = 0;
            int numOfInProgressFeatures = 0;

            for (var feature : features) {
                if (feature.getStatus().equals(Status.done)) {
                    numOfDoneFeatures++;
                } else if (feature.getStatus().equals(Status.in_progress)) {
                    numOfInProgressFeatures++;
                }
            }
            System.out.println("Feature state for " + sprint + ":");
            System.out.println("Done: " + numOfDoneFeatures / (double) numOfFeatures);
            System.out.println("In progress: " + numOfInProgressFeatures / (double) numOfFeatures);
        }
    }
}
