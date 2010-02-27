package disk.pages;

import java.util.List;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.corelib.components.Grid;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.chenillekit.tapestry.core.components.InPlaceEditor;

import disk.data.DiskDAO;
import disk.data.FileDAO;
import disk.entities.Disk;
import disk.entities.File;

public class Disks {
	@Inject
	private DiskDAO diskDAO;
	
	@Inject
	private FileDAO fileDAO;

	@SuppressWarnings("unused")
	@Component
	private InPlaceEditor inPlaceEditor;

	private Disk disk;

	@Component(id = "diskGrid")
	private Grid grid;

	public List<Disk> getDisks() {
		return diskDAO.findAllDisks();
	}

	public void onActivate(int page) {
		grid.setCurrentPage(page);
	}

	public int onPassivate() {
		return grid.getCurrentPage();
	}

	public int getCurrentPage() {
		return grid.getCurrentPage();
	}

	public Disk getDisk() {
		return disk;
	}

	public void setDisk(Disk disk) {
		this.disk = disk;
	}

	public Object[] getCon() {
		return new Object[] { getDisk().getDiskNumber(), grid.getCurrentPage() };
	}

	@OnEvent(component = "inPlaceEditor", value = InPlaceEditor.SAVE_EVENT)
	void actionFromEditor(int diskId, String given) {
		Disk disk = diskDAO.findDiskByNumber(diskId);
		disk.setGivenTo(given);
		diskDAO.updateDisk(disk);
	}
	
	public long getSummarySize() {
		return fileDAO.getSummarySize();
	}
	
}
