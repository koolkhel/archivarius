package disk.controller;

import java.util.LinkedList;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;

import disk.data.DiskDAO;
import disk.data.FileAddState;
import disk.data.FileDAO;
import disk.entities.Disk;
import disk.entities.File;

public class ControllerImpl implements Controller {
	@Inject
	private DiskDAO diskDao;

	@Inject
	private FileDAO fileDao;

	public void addFiles(FileAddState state, List<String> fileNames,
			List<Long> sizes) {
		state.setFiles(fileNames);
		state.setSizes(sizes);
		state.setDiskNumber(fileDao.proposeLastDiskNumber() + 1);
	}

	public void submitFiles(FileAddState state) {
		int diskNumber = state.getDiskNumber();
		Disk disk = new Disk();
		disk.setDescription(state.getDescription());
		disk.setDiskNumber(diskNumber);

		List<File> files = new LinkedList<File>();
		int i = 0;
		for (String string : state.getFiles()) {
			File file = new File();
			file.setDiskNumber(diskNumber);
			file.setFileName(string);
			file.setSize(state.getSizes().get(i++));
			file.setDisk(disk);
			files.add(file);
		}
		disk.setFiles(files);
		diskDao.saveDisk(disk);
	}
}
