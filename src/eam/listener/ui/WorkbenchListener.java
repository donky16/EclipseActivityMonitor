package eam.listener.ui;

import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;

import eam.handlers.StartHandler;
import eam.listener.edit.CommandListener;
import eam.listener.edit.KeyEventListener;
import eam.utils.date.CurrentDate;
import eam.utils.http.UploadData;

public class WorkbenchListener implements IWorkbenchListener {
	CurrentDate date = new CurrentDate();
	UploadData request = new UploadData();

	@Override
	public void postShutdown(IWorkbench arg0) {
		// TODO Auto-generated method stub
		if (StartHandler.isStart) {
			request.doPost("Edit-KeyEventTimes", "键盘点击总次数", Integer.toString(KeyEventListener.keyNum));
			request.doPost("Edit-BackspaceTimes", "Backspace点击次数", Integer.toString(KeyEventListener.backspaceNum));
			request.doPost("Edit-CodeAssistTimes", "代码自动补全次数", Integer.toString(CommandListener.codeAssistNum));
			request.doPost("Edit-CodeFormatTimes", "代码格式化次数", Integer.toString(CommandListener.codeFormatNum));
			request.doPost("Edit-PasteTimes", "总粘贴次数", Integer.toString(CommandListener.pasteNum));
			request.doPost("Eclipse-End", "结束监控", date.getDate());
		}
	}

	@Override
	public boolean preShutdown(IWorkbench arg0, boolean arg1) {
		// TODO Auto-generated method stub

		return true;
	}

}