package eam.listener.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.jar.Attributes.Name;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.spi.DirStateFactory.Result;
import javax.tools.Diagnostic;

import org.eclipse.ui.IWindowListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import eam.utils.date.CurrentDate;
import eam.utils.http.UploadData;

public class WindowListener implements IWindowListener {
	CurrentDate date = new CurrentDate();
	private String d1 = date.getDate();
	private String d2;
	UploadData request = new UploadData();

	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	IWorkbench workbench = PlatformUI.getWorkbench();

	@Override
	public void windowActivated(IWorkbenchWindow arg0) {
		// TODO Auto-generated method stub
		// 最小化或者焦点不再是eclipse
		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage workbenchPage;
		d2 = date.getDate();

		try {
			Date dd1 = df.parse(d1);
			Date dd2 = df.parse(d2);
			Integer second = (int) ((dd2.getTime() - dd1.getTime()) / 1000);
			if (second > 60) {
				// 焦点不在Eclipse主界面，持续时间（秒），起止时间
				request.doPost("Eclipse-Leave", Integer.toString(second), d1 + " - " + d2);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		workbenchPage = workbenchWindow.getPages()[0];
		String name = workbenchPage.getActivePart().getTitle();
		Pattern mpattern = Pattern.compile("\\.(java|jsp|xml|js|json|css|html|class|properties|sql|md)",
				Pattern.CASE_INSENSITIVE);
		Matcher mmatcher = mpattern.matcher(name);
		if (mmatcher.find()) {
			PartListener.startDate = d2;
		}
		// System.out.println(new Date() + ": 窗口激活");
	}

	@Override
	public void windowClosed(IWorkbenchWindow arg0) {
		// TODO Auto-generated method stub
		System.out.println(new Date() + ": 窗口关闭");
	}

	@Override
	public void windowDeactivated(IWorkbenchWindow arg0) {
		// TODO Auto-generated method stub
		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage workbenchPage;
		d1 = date.getDate();
		workbenchPage = workbenchWindow.getPages()[0];
		String name = workbenchPage.getActivePart().getTitle();
		Pattern mpattern = Pattern.compile("\\.(java|jsp|xml|js|json|css|html|class|properties|sql|md)",
				Pattern.CASE_INSENSITIVE);
		Matcher mmatcher = mpattern.matcher(name);
		if (mmatcher.find()) {
			Date dd1;
			if (PartListener.startDate != null) {
				try {
					dd1 = df.parse(PartListener.startDate);
					Date dd2 = df.parse(d1);
					Integer second = (int) ((dd2.getTime() - dd1.getTime()) / 1000);
					if (second > 10)
						// 编辑文件，起止时间，持续秒数
						request.doPost("Edit-EditFile", name,
								PartListener.startDate + " - " + d1 + " : " + second + "s");
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
		// System.out.println(new Date() + ": 窗口取消激活");
	}

	@Override
	public void windowOpened(IWorkbenchWindow arg0) {
		// TODO Auto-generated method stub

	}

}
