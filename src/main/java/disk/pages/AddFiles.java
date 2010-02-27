package disk.pages;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Asset;
import org.apache.tapestry5.annotations.ApplicationState;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.Path;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.upload.services.MultipartDecoder;
import org.apache.tapestry5.upload.services.UploadedFile;
import org.slf4j.Logger;

import disk.controller.Controller;
import disk.data.FileAddState;

public class AddFiles {
	@Inject
	private Controller controller;

	@Inject
	private MultipartDecoder decoder;

	@Property
	private UploadedFile file;

	@Inject
	private Logger logger;

	@ApplicationState
	private FileAddState state;

    @Inject
    @Path("context:diskApplet-0.0.3-SNAPSHOT.jar")
    private Asset applet;

    @Inject
    private ComponentResources componentResources;

	public String getAppletPath() {
		return applet.toClientURL();
	}

	public String getRedirectUrl() {
        return componentResources.createPageLink(FileReceiver.class, true)
                .toAbsoluteURI();
	}

	public String getUploadUrl() {
	    return componentResources.createEventLink("fileUploaded")
                .toAbsoluteURI();
	}

	public void onActivate() {
	}

	public Object onFileUploaded() {
		logger.debug("onFileUploaded");
		UploadedFile temp = decoder.getFileUpload("files");
		BufferedReader br = new BufferedReader(new InputStreamReader(temp
				.getStream()));
		List<String> files = new LinkedList<String>();
		List<Long> sizes = new LinkedList<Long>();
		String str = "";
		try {
			while ((str = br.readLine()) != null) {
				files.add(str);
				str = br.readLine();
				sizes.add(Long.parseLong(str));
			};
		} catch (IOException e) {
			logger.error("IOException in onFileUploaded");
		}
		controller.addFiles(state, files, sizes);

		return Index.class;
	}
}
