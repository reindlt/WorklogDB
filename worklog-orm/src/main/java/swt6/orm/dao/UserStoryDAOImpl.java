package swt6.orm.dao;

import swt6.orm.dao.interfaces.UserStoryDAO;
import swt6.orm.domain.Sprint;
import swt6.orm.domain.Task;
import swt6.orm.domain.UserStory;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;

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
}