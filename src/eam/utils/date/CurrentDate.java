package eam.utils.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentDate {
	public String getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(new Date());
	}
}