package disk.entities;

import java.util.List;

import javax.persistence.*;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.hibernate.annotations.Type;

@Entity
public class Disk {
	@NonVisual
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private int diskNumber;

	@Type(type = "text")
	private String description;

	private String givenTo;

	@OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "disk_id")
	private List<File> files;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getDiskNumber() {
		return diskNumber;
	}

	public void setDiskNumber(int diskNumber) {
		this.diskNumber = diskNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public String getGivenTo() {
		return givenTo;
	}

	public void setGivenTo(String givenTo) {
		this.givenTo = givenTo;
	}
}
