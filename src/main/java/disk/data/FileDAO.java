package disk.data;

import java.math.BigInteger;
import java.util.List;

import org.apache.tapestry5.hibernate.annotations.CommitAfter;

import disk.entities.File;

public interface FileDAO {
	List<File> findAllFiles();

	List<File> findFilesByDisk(int diskNumber);

	List<File> findFilesByPartialName(String partialName);

	File find(long id);

	@CommitAfter
	public void save(File file);

	@CommitAfter
	void saveAllFiles(List<File> files);

	@CommitAfter
	public void delete(File file);

	int proposeLastDiskNumber();

	@CommitAfter
	void update(File file);
	
	long getSummarySize();
}
