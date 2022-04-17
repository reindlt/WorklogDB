package swt6.orm.dao;

import swt6.orm.dao.interfaces.IssueDAO;
import swt6.orm.domain.Issue;

public class IssueDAOImpl extends BaseDAOImpl<Issue> implements IssueDAO {
//    @Override
//    public List<BuggyReleaseDto> getLeastNBuggyReleases(int n) {
//        List<BuggyReleaseDto> results;
//        try {
//            EntityManager em = JpaUtil.getTransactedEntityManager();
//            results = em.createQuery(
//                    "select NEW swt6.orm.dao.dtos.BuggyReleaseDto(i.releaseVersion, count(i.releaseVersion))" +
//                            "from Issue i " +
//                            " group by i.releaseVersion " +
//                            " order by count(i.releaseVersion) desc",
//                    BuggyReleaseDto.class
//                ).setMaxResults(n).getResultList();
//            JpaUtil.commit();
//        } catch (Exception e) {
//            JpaUtil.rollback();
//            throw e;
//        }
//        return results;
//    }
}
