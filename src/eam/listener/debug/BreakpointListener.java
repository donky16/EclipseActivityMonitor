package eam.listener.debug;

import org.eclipse.core.resources.IMarkerDelta;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.IBreakpointListener;
import org.eclipse.debug.core.model.IBreakpoint;

import eam.utils.date.CurrentDate;
import eam.utils.http.UploadData;

public class BreakpointListener implements IBreakpointListener {
	UploadData request = new UploadData();
	CurrentDate date = new CurrentDate();

	@Override
	public void breakpointAdded(IBreakpoint arg0) {
		// TODO Auto-generated method stub
		try {
			request.doPost("Debug-AddBreakpoint", "添加断点: " + arg0.getMarker().getResource().getFullPath() + " 第 "
					+ arg0.getMarker().getAttributes().get("lineNumber") + " 行", date.getDate());
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void breakpointChanged(IBreakpoint arg0, IMarkerDelta arg1) {
		// TODO Auto-generated method stub
		// System.out.println(new Date() + ": 改变断点 " + arg0.getMarker().toString());
	}

	@Override
	public void breakpointRemoved(IBreakpoint arg0, IMarkerDelta arg1) {
		// TODO Auto-generated method stub
		try {
			request.doPost("Debug-RemoveBreakpoint", "删除断点: " + arg0.getMarker().getResource().getFullPath() + " 第 "
					+ arg0.getMarker().getAttributes().get("lineNumber") + " 行", date.getDate());
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}