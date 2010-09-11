package disk.pages;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.hibernate.HibernateSessionManager;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.slf4j.Logger;
import org.hibernate.search.Search;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.FullTextQuery;
import org.hibernate.*;
import disk.entities.File;
import disk.entities.Disk;

/**
 * Start page of application helloworld.
 */
public class Index {
	private String fileName;

	@InjectPage
	private Files files;

	@Inject
	private Logger logger;

	@Inject
	private HttpServletRequest request;

    @Inject
    private HibernateSessionManager hibernateSessionManager;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	Object onSubmit() throws ParseException {
        files.setSearchString(fileName);
		return files;
	}

    public void onIndex() throws Exception {
        logger.error("we are here");
        FullTextSession fullTextSession = Search.getFullTextSession(hibernateSessionManager.getSession());
        fullTextSession.createIndexer().startAndWait();

    }
}
