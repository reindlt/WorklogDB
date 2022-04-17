package swt6.orm.dao;

import swt6.orm.dao.interfaces.UserStoryDAO;
import swt6.orm.domain.Sprint;
import swt6.orm.domain.Task;
import swt6.orm.domain.UserStory;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class UserStoryDAOImpl extends BaseDAOImpl<UserStory> implements UserStoryDAO {
    @Override
    public UserStory addTasks(UserStory story, Task... tasks) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            story = em.merge(story);
            for (Task task : tasks) {
                story.addTask(task);
            }
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return story;
    }

    @Override
    public UserStory removeTasks(UserStory story) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            story = em.merge(story);
            for (Task task : story.getTasks()) {
                task.setUserStory(null);
            }
            story.getTasks().clear();
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return story;
    }

//    @Override
//    public List<PointsPerUserStoryDto> getPointsPerUserStory() {
//        List<PointsPerUserStoryDto> results;
//        try {
//            EntityManager em = JpaUtil.getTransactedEntityManager();
//            results = em.createQuery(
//                    "select NEW swt6.orm.dao.dtos.PointsPerUserStoryDto(t.userStory, sum(t.points)) " +
//                            "from Task t " +
//                            "group by t.userStory",
//                    PointsPerUserStoryDto.class
//            ).getResultList();
//            JpaUtil.commit();
//        } catch (Exception e) {
//            JpaUtil.rollback();
//            throw e;
//        }
//        return results;
//    }

    @Override
    public List<UserStory> getForSprint(Sprint sprint) {
        List<UserStory> results;

        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            results = em.createQuery(
                    "select us " +
                            "from UserStory us " +
                            "where us.sprint = :sprint",
                    UserStory.class
            ).setParameter("sprint", sprint).getResultList();
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
        return results;
    }

//    @Override
//    public List<UserStory> getStoryPointsPerUserStory() {
//        List<UserStory> results;
//        try {
//            EntityManager em = JpaUtil.getTransactedEntityManager();
//            results = em.createQuery(
//                    "select t.userStory, count(t.points)" +
//                            "from Task t " +
//                            "group by t.userStory",
//                    UserStory.class
//            ).getResultList();
//            JpaUtil.commit();
//        } catch (Exception e) {
//            JpaUtil.rollback();
//            throw e;
//        }
//        return results;
//    }
}
