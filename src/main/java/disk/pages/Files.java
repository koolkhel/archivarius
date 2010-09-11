package disk.pages;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.tapestry5.annotations.Parameter;
import org.apache.tapestry5.grid.GridDataSource;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.FullTextQuery;

import disk.data.FileDAO;
import disk.entities.File;

import java.util.List;

public class Files {
	@Inject
	private ComponentResources _resources;

	@Inject
	private HibernateSessionManager hsm;

	private File file;

	@Parameter
	private GridDataSource files;

	@Inject
	private FileDAO fileDAO;
    
	private String searchString = "";

	public File getFile() {
		return file;
	}

	public List<File> getFiles() throws ParseException {
        FullTextSession fullTextSession = Search.getFullTextSession(hsm.getSession());
        QueryParser queryParser = new QueryParser(org.apache.lucene.util.Version.LUCENE_29,
                "fileName", new StandardAnalyzer(org.apache.lucene.util.Version.LUCENE_29));

        org.apache.lucene.search.Query query = queryParser.parse(searchString);
        FullTextQuery ftq = fullTextSession.createFullTextQuery(query, File.class);
        return ftq.list();
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

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
}
