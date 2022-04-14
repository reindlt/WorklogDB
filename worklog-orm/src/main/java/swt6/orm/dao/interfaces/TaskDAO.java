package swt6.orm.dao.interfaces;

import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Task;

import java.util.List;

public interface TaskDAO extends BaseDAO<Task> {
    Task addLogBookEntries(Task task, LogbookEntry... logbookEntries);
    Task removeLogBookEntries(Task task);
    List<Task> getAggregatedPointsPerUserStory();
}
