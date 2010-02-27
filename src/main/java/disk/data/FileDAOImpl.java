package disk.data;

import java.math.BigInteger;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import disk.entities.File;

public class FileDAOImpl implements FileDAO {

    @Inject
    private Session session;

    public void delete(File file) {
        session.delete(file);
    }

    public void saveAllFiles(List<File> files) {
        for (File file : files) {
            session.persist(file);
        }
    }

    public File find(long id) {
        return (File) session.createCriteria(File.class).add(
                Restrictions.eq("id", new Long(id))).list().get(0);
    }

    @SuppressWarnings("unchecked")
    public List<File> findAllFiles() {
        return session.createCriteria(File.class).list();
    }

    @SuppressWarnings("unchecked")
    public List<File> findFilesByDisk(int diskNumber) {
        return session.createCriteria(File.class).add(
                Restrictions.eq("diskNumber", diskNumber)).list();
    }

    @SuppressWarnings("unchecked")
    public List<File> findFilesByPartialName(String partialName) {
        return session.createCriteria(File.class).add(
                Restrictions.ilike("fileName", partialName, MatchMode.ANYWHERE)).list();
    }

    public void save(File file) {
        session.persist(file);
    }

    public int proposeLastDiskNumber() {
        List<Integer> number = (List<Integer>) session.createCriteria(File.class).setProjection(
                Projections.max("diskNumber")).list();

        if (number.size() > 0) {
            if (number.get(0) != null) {
                return (Integer) number.get(0);
            }
        }
        return 1;
    }

    public void update(File file) {
        session.update(file);
    }

    public long getSummarySize() {
        List<Long> result = (List<Long>) session.createCriteria(File.class).setProjection(Projections.sum("size")).list();
        if (result.size() > 0) {
            if (result.get(0) != null) {
                return result.get(0);
            }
        }
        return 0;
    }
}
