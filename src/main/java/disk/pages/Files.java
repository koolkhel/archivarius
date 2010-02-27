package disk.pages;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import disk.components.HibernateEntityDataSource;
import disk.data.FileDAO;
import disk.entities.File;

public class Files {
	@Inject
	private ComponentResources _resources;

	@Inject
	private Session session;

	private File file;

	private String fileName;

	@Parameter
	private GridDataSource files;

	@Inject
	private FileDAO fileDAO;
	private String searchString = "";

	public File getFile() {
		return file;
	}

	public String getFileName() {
		return fileName;
	}

	public GridDataSource getFiles() {
		HibernateEntityDataSource<File> hibFiles = null;

		hibFiles = HibernateEntityDataSource.create(session, File.class,
				_resources);
		hibFiles.getCriteria().createAlias("disk", "d");
		Conjunction conj = Restrictions.conjunction();
		for (String substr : searchString.split(" ")) {
			conj
					.add(Restrictions.ilike("fileName", substr,
							MatchMode.ANYWHERE));
		}
		hibFiles.addCriterion(Restrictions.or(Restrictions.or(conj,
				Restrictions.ilike("d.description", searchString,
						MatchMode.ANYWHERE)), Restrictions.ilike("info",
				searchString, MatchMode.ANYWHERE)));

		files = hibFiles;

		return files;
	}

	public int getLastDisk() {
		return fileDAO.proposeLastDiskNumber();
	}

	public String getSearchString() {
		return searchString;
	}

	public void onActivate(String str) {
		searchString = str;
	}

	public String onPassivate() {
		return searchString;
	}

	public Object onSubmit() {
		return this;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
}
