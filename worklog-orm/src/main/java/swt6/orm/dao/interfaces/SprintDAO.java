package swt6.orm.dao.interfaces;

import swt6.orm.domain.Project;
import swt6.orm.domain.Sprint;
import swt6.orm.domain.UserStory;

import java.util.List;

public interface SprintDAO extends BaseDAO<Sprint> {
    Sprint addUserStories(Sprint sprint, UserStory... userStories);
    Sprint removeUserStories(Sprint sprint);
    Sprint addToProject(Sprint sprint, Project project);
    Sprint removeFromProject(Sprint sprint, Project project);
//    List<StoryPointsPerSprintDto> getStoryPointsPerSprint();
    List<Sprint> getActiveSprints();
}
