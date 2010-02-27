package disk.components;

import java.text.DecimalFormat;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.annotations.Parameter;

public class SpellSize {

	@Parameter(required = true)
	private long size;

	void beginRender(MarkupWriter writer) {
		writer.write(formatNumber(size));
	}

	private String formatNumber(long number) {
		String formatted;
		if (number > 1000000000) {
			formatted = Long.toString(number / 1000000000) + " Gb";
		} else if (number > 1000000) {
			formatted = Long.toString(number / 1000000) + " Mb";
		} else if (number > 1000) {
			formatted = Long.toString(number / 1000) + " Kb";
		} else {
			formatted = Long.toString(number) + " bytes";
		}
		return (formatted);

	}
}
