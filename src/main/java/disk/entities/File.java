package disk.entities;

import javax.persistence.*;

import org.apache.tapestry5.beaneditor.NonVisual;
import org.hibernate.annotations.Type;
import org.hibernate.search.annotations.*;

@Entity
@Indexed
public class File {
	public File() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@NonVisual
	private Long id;

	@Type(type = "text")
    @Field(index = Index.TOKENIZED, store = Store.NO)
	private String fileName;

	private long size;

	@NonVisual
	@ManyToOne
    @JoinColumn(name = "disk_id")
    @IndexedEmbedded
	private Disk disk;

	@Type(type = "text")
    @Field(index = Index.TOKENIZED, store = Store.NO)
	private String info;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public Disk getDisk() {
		return disk;
	}

	public void setDisk(Disk disk) {
		this.disk = disk;
	}

	public String getDiskDescription() {
		return disk.getDescription();
	}

	public void setDiskDescription(String description) {
		disk.setDescription(description);
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
}
