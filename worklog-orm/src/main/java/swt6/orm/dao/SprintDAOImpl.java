package swt6.orm.dao;

import swt6.orm.dao.interfaces.SprintDAO;
import swt6.orm.domain.Project;
import swt6.orm.domain.Sprint;
import swt6.orm.domain.UserStory;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;

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
}
