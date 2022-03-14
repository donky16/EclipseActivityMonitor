package eam.listener.ui;

import java.util.Date;

import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveListener;
import org.eclipse.ui.IWorkbenchPage;

public class PerspectiveLister implements IPerspectiveListener {

	@Override
	public void perspectiveActivated(IWorkbenchPage arg0, IPerspectiveDescriptor arg1) {
		// TODO Auto-generated method stub
		// System.out.println(new Date() + ": 透视图激活");
	}

	@Override
	public void perspectiveChanged(IWorkbenchPage arg0, IPerspectiveDescriptor arg1, String arg2) {
		// TODO Auto-generated method stub
		// System.out.println(new Date() + ": 透视图改变");
	}

}
