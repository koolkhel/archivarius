package disk.pages;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;

import disk.components.HibernateEntityDataSource;
import disk.data.DiskDAO;
import disk.entities.Disk;
import disk.entities.File;

public class ShowDisk {
	@Inject 
	private DiskDAO diskDAO;
	
	private int diskId;

	private int page = 1;
	
	/**
	 * если диска нет, нужно заблокировать контролы
	 */
	private boolean disabled = false;

	@Inject
	private Session session;
	
	@Inject
	private Logger logger;
	
	private String description;
	
	@Inject
	private ComponentResources resources;

	void onActivate(int disk) {
		diskId = disk;
		Disk diskObj = diskDAO.findDiskByNumber(diskId);
		if (diskObj.getDiskNumber() == -1) {
			disabled = true;
		}
		description = diskObj.getDescription();
	}
	
	public Object onSubmitFromSearch() {
		return this;
	}

	void onActivate(int disk, int page_) {
		diskId = disk;
		page = page_;
		Disk diskObj = diskDAO.findDiskByNumber(diskId);
		if (diskObj.getDiskNumber() == -1) {
			disabled = true;
		}
		description = diskObj.getDescription();
	}
	
	public Object onSubmitFromDeleteDisk() {
		Disk disk = diskDAO.findDiskByNumber(diskId);
		diskDAO.deleteDisk(disk);
		return this;
	}
	
	public Object onSubmitFromUpdateDescription() {
		Disk disk = diskDAO.findDiskByNumber(diskId);
		disk.setDescription(description);
		diskDAO.updateDisk(disk);
		return this;
	}

	Object[] onPassivate() {
		return new Object[] { diskId, page };
	}

	public GridDataSource getDiskContents() {
		HibernateEntityDataSource<File> files = HibernateEntityDataSource.create(session, File.class, resources);
		logger.warn("diskId - " + diskId);
        files.getCriteria().createAlias("disk", "d");
		files.addCriterion(Restrictions.eq("d.diskNumber", diskId));
		return files;
	}
	
	public Object[] getPrev() {
		return new Object [] {diskId - 1, page};
	}
	
	public Object[]  getNext() {
		return new Object [] {diskId + 1, page};
	}

	public int getPage() {
		return page;
	}

	public int getDiskNumber() {
		return diskId;
	}
	
	public void setDiskId(int diskId_) {
		diskId = diskId_;
	}
	
	public int getDiskId() {
		return diskId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
}
