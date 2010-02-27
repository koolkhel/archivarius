package disk.controller;

import java.util.List;

import disk.data.FileAddState;

public interface Controller {
	/**
	 * Добавить в базу данных файлы
	 * 
	 * @param fileNames
	 */
	public void addFiles(FileAddState state, List<String> fileNames,
			List<Long> sizes);

	public void submitFiles(FileAddState state);
}
