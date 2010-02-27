package disk.data;

import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import disk.entities.Disk;

public class DiskDAOImpl implements DiskDAO {
	@Inject
	private Session session;

	public void deleteDisk(Disk disk) {
		session.delete(disk);
	}

	@SuppressWarnings("unchecked")
	public List<Disk> findAllDisks() {
		return session.createCriteria(Disk.class).addOrder(
				Order.asc("diskNumber")).list();
	}

	public void saveAllDisks(List<Disk> disks) {
		for (Disk disk : disks) {
			session.persist(disk);
		}
	}

	public void saveDisk(Disk disk) {
		session.persist(disk);
	}

	@SuppressWarnings("unchecked")
	public List<Disk> findDisksByDescription(String desc) {
		List<Disk>result = session.createCriteria(Disk.class).add(
				Restrictions.ilike("description", desc, MatchMode.ANYWHERE)).list();
		
		return result;
	}

	@SuppressWarnings("unchecked")
	public Disk findDiskByNumber(int number) {
		List<Disk> results =  session.createCriteria(Disk.class).add(
				Restrictions.eq("diskNumber", number)).list();
		Disk result;
		if (results.size() == 0) {
			Disk dummy = new Disk();
			dummy.setDiskNumber(-1);
			dummy.setDescription("нет такого диска");
			result = dummy;
		} else {
			result = results.get(0);
		}
		return result;
	}

	public void updateDisk(Disk disk) {
		session.update(disk);
	}

}
