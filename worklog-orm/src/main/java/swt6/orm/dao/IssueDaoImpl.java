package swt6.orm.dao;

import swt6.orm.dao.interfaces.IssueDAO;
import swt6.orm.domain.Issue;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import java.util.List;

public class IssueDaoImpl extends BaseDAOImpl<Issue> implements IssueDAO {
    @Override
    public List<Issue> getLeastNBuggyReleases(int n) {
        List<Issue> results;
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            results = em.createQuery(
                    "select i.title, count(i.releaseVersion) as bugs " +
                            "from Issue i " +
                            " group by i.title " +
                            " order by bugs",
                    Issue.class
                ).setMaxResults(n).getResultList();
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
        return results;
    }
}
