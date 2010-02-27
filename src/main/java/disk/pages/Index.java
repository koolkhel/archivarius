package disk.pages;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.slf4j.Logger;

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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	Object onSubmit() {
		files.setFileName(fileName);
		return files;
	}
}
