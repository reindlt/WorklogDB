package swt6.orm.dao.interfaces;

import swt6.orm.domain.Issue;

import java.util.List;

public interface IssueDAO extends BaseDAO<Issue> {
    List<Issue> getLeastNBuggyReleases(int n);
}
