package disk.pages;

import java.util.List;

import org.apache.tapestry5.annotations.ApplicationState;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

import disk.controller.Controller;
import disk.data.FileAddState;
import disk.data.DiskDAO;
import disk.entities.Disk;

public class FileReceiver {
	@Inject
	private Controller controller;

	private String description;

	private String fileName;

	private int fileNo = 0;

	@Inject
	private Logger logger;

	@ApplicationState
	private FileAddState state;

    @InjectPage
    private ShowDisk showDisk;

	public String getDescription() {
		return description;
	}

	public int getDiskNumber() {
		return state.getDiskNumber();
	}

	public String getFileName() {
		return fileName;
	}

	public int getFileNo() {
		fileNo++;
		return fileNo;
	}

	public List<String> getFiles() {
		return state.getFiles();
	}

	public Object onSubmitFromDiskForm() {
		logger.warn("from ok");
		controller.submitFiles(state);
        showDisk.setDiskId(state.getDiskNumber());
		return showDisk;
	}
	
	public Object onSubmitFromCancelForm() {
		logger.warn("from cancel");
		state.setDescription("");
		state.setFiles(null);
		state.setSizes(null);
		state.setDiskNumber(-1);
		return Files.class;
	}

	public void setDescription(String description) {
		this.description = description;
		state.setDescription(description);
	}

	public void setDiskNumber(int diskNumber) {
		state.setDiskNumber(diskNumber);
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
