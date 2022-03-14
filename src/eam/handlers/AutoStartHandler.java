package eam.handlers;

import org.eclipse.ui.IStartup;

import eam.utils.console.PrintConsoleMessage;
import eam.utils.http.DownloadPoetry;
//import eam.utils.http.DownloadPoetry;

public class AutoStartHandler implements IStartup {

	@Override
	public void earlyStartup() {
		// TODO Auto-generated method stub
		// try {
		// Thread.currentThread().sleep(20000);
		// } catch (InterruptedException e2) {
		// // TODO Auto-generated catch block
		// e2.printStackTrace();
		// }
		// ICommandService commandService =
		// PlatformUI.getWorkbench().getService(ICommandService.class);
		//
		// Command command = commandService.getCommand("EAM.commands.startCommand");
		// try {
		// Thread.currentThread().sleep(20000);
		// } catch (InterruptedException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }
		// try {
		// command.executeWithChecks(new ExecutionEvent());
		// } catch (ExecutionException | NotDefinedException | NotEnabledException |
		// NotHandledException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		PrintConsoleMessage printConsoleMessage = new PrintConsoleMessage();
		printConsoleMessage.printMessage("Eclipse编程行为监控插件 v3.0");
		printConsoleMessage.printMessage("如需使用监控插件，请点击开始监控");
		DownloadPoetry downloadPoetry = new DownloadPoetry();
		downloadPoetry.downloadPoetry();

	}

}
