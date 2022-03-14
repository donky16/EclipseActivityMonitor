package eam.handlers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.attribute.standard.PrinterMessageFromOperator;

import org.apache.commons.httpclient.methods.multipart.Part;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;
import org.eclipse.ui.console.*;
import org.eclipse.ui.part.ISetSelectionTarget;

import eam.listener.ui.PartListener;
import eam.listener.ui.WorkbenchListener;
import eam.listener.debug.BreakpointListener;
import eam.listener.debug.DebugEventSetListener;
import eam.listener.edit.CodeAssistListener;
import eam.listener.edit.CommandListener;
import eam.listener.edit.KeyEventListener;
import eam.listener.resource.ResourceChangeListener;
import eam.listener.ui.PageListener;
import eam.listener.ui.PerspectiveLister;
import eam.listener.ui.WindowListener;
import eam.utils.date.CurrentDate;
import eam.utils.http.UploadData;
import eam.utils.console.PrintConsoleMessage;;

/**
 * Our sample handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class StartHandler extends AbstractHandler {

	CurrentDate date = new CurrentDate();

	PrintConsoleMessage printConsoleMessage = new PrintConsoleMessage();
	UploadData request = new UploadData();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	static IWorkbench workbench;
	static WorkbenchListener workbenchListener;
	static WindowListener windowListener;
	static IWorkbenchWindow workbenchWindow;
	static PageListener pageListener;
	static PerspectiveLister perspectiveLister;
	static PartListener partListener;
	static IWorkbenchPage workbenchPage;
	static IEditorPart editorPart;
	static KeyListener keyListener;
	static ICommandService commandService;
	static CommandListener commandListener;
	static DebugPlugin debugPlugin;
	static BreakpointListener breakpointListener;
	static DebugEventSetListener debugEventSetListener;
	static ResourcesPlugin resourcesPlugin;
	static ResourceChangeListener resourceChangeListener;

	public static boolean isStart = false;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		String startDate = date.getDate();
		PartListener.startDate = startDate;
		System.out.println(PartListener.startDate);
		String commandType = "";
		try {
			commandType = event.getCommand().getName();
		} catch (NotDefinedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printConsoleMessage.printMessage("测试监控环境");
		if (commandType.equals("startCommand")) {
			if (!StartHandler.isStart) {
				UploadData request = new UploadData();
				if (UploadData.isConfigFile) {
					if (request.doPost("Eclipse-Start", "开始监控", startDate) == null) {
						printConsoleMessage.printMessage("启动监控失败");
					} else {
						StartHandler.isStart = true;
						printConsoleMessage.printMessage("正在监控，请保持联网状态");

						StartHandler.workbench = PlatformUI.getWorkbench();
						StartHandler.workbenchListener = new WorkbenchListener();
						StartHandler.windowListener = new WindowListener();
						StartHandler.workbenchWindow = workbench.getActiveWorkbenchWindow();
						StartHandler.pageListener = new PageListener();
						StartHandler.perspectiveLister = new PerspectiveLister();
						StartHandler.partListener = new PartListener(startDate);
						StartHandler.workbenchPage = workbenchWindow.getPages()[0];
						StartHandler.editorPart = workbenchPage.getActiveEditor();
						StartHandler.keyListener = new KeyEventListener();
						StartHandler.commandService = workbench.getService(ICommandService.class);
						StartHandler.commandListener = new CommandListener();
						StartHandler.debugPlugin = DebugPlugin.getDefault();
						StartHandler.breakpointListener = new BreakpointListener();
						StartHandler.debugEventSetListener = new DebugEventSetListener();
						StartHandler.resourcesPlugin = new ResourcesPlugin();
						StartHandler.resourceChangeListener = new ResourceChangeListener();

						StartHandler.workbench.addWorkbenchListener(StartHandler.workbenchListener);
						StartHandler.workbench.addWindowListener(StartHandler.windowListener);
						StartHandler.workbenchWindow.addPageListener(StartHandler.pageListener);
						StartHandler.workbenchWindow.addPerspectiveListener(StartHandler.perspectiveLister);
						StartHandler.workbenchPage.addPartListener(StartHandler.partListener);
						// IWorkbenchPart workbenchPart = workbenchPage.getActivePart();
						try {
							if (editorPart != null) {
								editorPart.getAdapter(org.eclipse.swt.widgets.Control.class)
										.addKeyListener((org.eclipse.swt.events.KeyListener) keyListener);
								SourceViewer sourceViewer = (SourceViewer) editorPart
										.getAdapter(ITextOperationTarget.class);
							}
						} catch (Exception e) {
							// TODO: handle exception
						}
						// 获取插件的Service，再将监听器注册到对应Service上
						StartHandler.commandService.addExecutionListener(StartHandler.commandListener);
						StartHandler.debugPlugin.getBreakpointManager()
								.addBreakpointListener(StartHandler.breakpointListener);
						StartHandler.debugPlugin.addDebugEventListener(StartHandler.debugEventSetListener);
						StartHandler.resourcesPlugin.getWorkspace()
								.addResourceChangeListener(StartHandler.resourceChangeListener);
					}
				} else {
					printConsoleMessage.printMessage("配置文件不存在，请重新登录并点击开始监控");
					printConsoleMessage.printMessage("启动监控失败");
				}
			} else {
				printConsoleMessage.printMessage("正在监控中，请勿重复点击开始监控");
			}

		}
		if (commandType.equals("endCommand")) {
			if (StartHandler.isStart) {
				if (request.doPost("Edit-KeyEventTimes", "键盘点击总次数",
						Integer.toString(KeyEventListener.keyNum)) == null) {
					printConsoleMessage.printMessage("请重新联网之后，再点击结束监控，不然数据无法上传");
				}

				request.doPost("Edit-BackspaceTimes", "Backspace点击次数", Integer.toString(KeyEventListener.backspaceNum));
				request.doPost("Edit-CodeAssistTimes", "代码自动补全次数", Integer.toString(CommandListener.codeAssistNum));
				request.doPost("Edit-CodeFormatTimes", "代码格式化次数", Integer.toString(CommandListener.codeFormatNum));
				request.doPost("Edit-PasteTimes", "总粘贴次数", Integer.toString(CommandListener.pasteNum));
				request.doPost("Eclipse-End", "结束监控", date.getDate());

				KeyEventListener.keyNum = 0;
				KeyEventListener.backspaceNum = 0;
				CommandListener.codeAssistNum = 0;
				CommandListener.codeFormatNum = 0;
				CommandListener.pasteNum = 0;

				StartHandler.workbench.removeWorkbenchListener(StartHandler.workbenchListener);
				StartHandler.workbench.removeWindowListener(StartHandler.windowListener);
				StartHandler.workbenchWindow.removePageListener(StartHandler.pageListener);
				StartHandler.workbenchWindow.removePerspectiveListener(StartHandler.perspectiveLister);
				StartHandler.workbenchPage.removePartListener(StartHandler.partListener);
				StartHandler.commandService.removeExecutionListener(StartHandler.commandListener);
				StartHandler.debugPlugin.getBreakpointManager()
						.removeBreakpointListener(StartHandler.breakpointListener);
				StartHandler.debugPlugin.removeDebugEventListener(StartHandler.debugEventSetListener);
				StartHandler.resourcesPlugin.getWorkspace()
						.removeResourceChangeListener(StartHandler.resourceChangeListener);
				StartHandler.isStart = false;
				PartListener.startDate = null;
				printConsoleMessage.printMessage("结束监控成功");

			} else {
				printConsoleMessage.printMessage("监控未启动，不需结束监控");
			}
		}
		return null;
	}
}
