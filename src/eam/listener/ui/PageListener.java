package eam.listener.ui;

import java.util.Date;

import org.eclipse.ui.IPageListener;
import org.eclipse.ui.IWorkbenchPage;

public class PageListener implements IPageListener {

	@Override
	public void pageActivated(IWorkbenchPage arg0) {
		// TODO Auto-generated method stub
		// System.out.println(new Date() + ": " + arg0.getLabel() + "页面激活");
	}

	@Override
	public void pageClosed(IWorkbenchPage arg0) {
		// TODO Auto-generated method stub
		// System.out.println(new Date() + ": " + arg0.getLabel() + "页面关闭");
	}

	@Override
	public void pageOpened(IWorkbenchPage arg0) {
		// TODO Auto-generated method stub
		// System.out.println(new Date() + ": 工作台关闭");
	}

}
