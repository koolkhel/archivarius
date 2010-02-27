package disk.data;

import java.util.List;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;

import disk.entities.Disk;

public interface DiskDAO {
	List<Disk> findAllDisks();

	List<Disk> findDisksByDescription(String desc);

	@CommitAfter
	void saveDisk(Disk disk);

	@CommitAfter
	void deleteDisk(Disk disk);

	@CommitAfter
	void saveAllDisks(List<Disk> disks);

	@CommitAfter
	void updateDisk(Disk disk);

	Disk findDiskByNumber(int number);
}
