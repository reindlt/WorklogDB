package swt6.orm.dao.interfaces;

import swt6.orm.domain.Task;
import swt6.orm.domain.UserStory;

import java.util.List;

public interface UserStoryDAO extends BaseDAO<UserStory> {
    UserStory addTasks(UserStory story, Task... tasks);
    UserStory removeTasks(UserStory story);
    //List<UserStory> getStoryPointsPerUserStory();
}
