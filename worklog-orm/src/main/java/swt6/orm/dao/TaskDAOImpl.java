package swt6.orm.dao;

import swt6.orm.dao.interfaces.TaskDAO;
import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Task;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;

public class TaskDAOImpl extends BaseDAOImpl<Task> implements TaskDAO {
    @Override
    public Task addLogBookEntries(Task task, LogbookEntry... logbookEntries) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            task = em.merge(task);
            for (LogbookEntry logbookEntry : logbookEntries) {
                task.addLogBookEntry(logbookEntry);
            }
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return task;
    }

    @Override
    public Task removeLogBookEntries(Task task) {
        return null;
    }
}
