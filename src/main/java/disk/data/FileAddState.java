package disk.data;

import java.util.List;

public class FileAddState {
	private int diskNumber;
	private List<String> files;
	private List<Long> sizes;
	private String description;

	public int getDiskNumber() {
		return diskNumber;
	}

	public void setDiskNumber(int diskNumber) {
		this.diskNumber = diskNumber;
	}

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

	public List<Long> getSizes() {
		return sizes;
	}

	public void setSizes(List<Long> sizes) {
		this.sizes = sizes;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
