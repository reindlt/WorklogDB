package swt6.orm.dao;

import swt6.orm.dao.interfaces.SprintDAO;
import swt6.orm.domain.Project;
import swt6.orm.domain.Sprint;
import swt6.orm.domain.UserStory;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class SprintDAOImpl extends BaseDAOImpl<Sprint> implements SprintDAO {
    @Override
    public Sprint addUserStories(Sprint sprint, UserStory... userStories) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            sprint = em.merge(sprint);
            for (UserStory story : userStories) {
                sprint.addUserStory(story);
            }
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return sprint;
    }

    @Override
    public Sprint removeUserStories(Sprint sprint) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            sprint = em.merge(sprint);
            for (UserStory story : sprint.getUserStories()) {
                sprint.removeUserStory(story);
            }
            sprint.getUserStories().clear();
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return sprint;
    }

    @Override
    public Sprint addToProject(Sprint sprint, Project project) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            sprint = em.merge(sprint);
            sprint.getProject().getSprints().remove(sprint);
            project.getSprints().add(sprint);
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return sprint;
    }

    @Override
    public Sprint removeFromProject(Sprint sprint, Project project) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            sprint = em.merge(sprint);
            sprint.setProject(null);
            project.getSprints().remove(sprint);
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return sprint;
    }

//    @Override
//    public List<StoryPointsPerSprintDto> getStoryPointsPerSprint() {
//        List<StoryPointsPerSprintDto> results;
//        try {
//            EntityManager em = JpaUtil.getTransactedEntityManager();
//            results = em.createQuery(
//                    "select NEW swt6.orm.dao.dtos.StoryPointsPerSprintDto(us.sprint, sum(us.storyPoints)) " +
//                            "from UserStory us " +
//                            "group by us.sprint.id",
//                    StoryPointsPerSprintDto.class
//            ).getResultList();
//            JpaUtil.commit();
//        } catch (Exception e) {
//            JpaUtil.rollback();
//            throw e;
//        }
//        return results;
//    }

    @Override
    public List<Sprint> getActiveSprints() {
        List<Sprint> results;
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            results = em.createQuery(
                    "select s " +
                            "from Sprint s " +
                            "where startDate < current_date " +
                            "and endDate > current_date",
                    Sprint.class
            ).getResultList();
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
        return results;
    }

}
