package eam.listener.edit;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IExecutionListener;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import eam.utils.date.CurrentDate;
import eam.utils.http.UploadData;

public class CommandListener implements IExecutionListener {
	IWorkbench workbench = PlatformUI.getWorkbench();
	
	DocumentListener documentListener = new DocumentListener();

	UploadData request = new UploadData();
	CurrentDate date = new CurrentDate();

	public static int codeAssistNum = 0;
	public static int codeFormatNum = 0;
	public static int pasteNum = 0;

	@Override
	public void notHandled(String arg0, NotHandledException arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postExecuteFailure(String arg0, ExecutionException arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postExecuteSuccess(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		// System.out.println(arg0.toString());
		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage workbenchPage = workbenchWindow.getPages()[0];
		if (arg0.equals("org.eclipse.ui.edit.paste")) {
			pasteNum++;
			IEditorPart editorPart = workbenchPage.getActiveEditor();
			SourceViewer sourceViewer = (SourceViewer) editorPart.getAdapter(ITextOperationTarget.class);
			sourceViewer.getDocument().removeDocumentListener(documentListener);
		}
		if (arg0.equals("org.eclipse.wst.server.launchShortcut.debug")) {
			request.doPost("Debug-DebugOnServer", "服务器上调试", date.getDate());
		}
		if (arg0.equals("org.eclipse.jdt.debug.ui.localJavaShortcut.debug")) {
			request.doPost("Debug-DebugOnApp", "本地调试", date.getDate());
		}
		if (arg0.equals("org.eclipse.debug.ui.commands.StepOver")) {
			request.doPost("Debug-StepOver", "调试过程StepOver", date.getDate());
		}
		if (arg0.equals("org.eclipse.debug.ui.commands.StepInto")) {
			request.doPost("Debug-StepInto", "调试过程StepInto", date.getDate());
		}
		if (arg0.equals("org.eclipse.wst.server.launchShortcut.run")) {
			request.doPost("Run-RunOnServer", "服务器上运行", date.getDate());
		}
		if (arg0.equals("org.eclipse.jdt.debug.ui.localJavaShortcut.run")) {
			request.doPost("Run-RunOnApp", "本地运行", date.getDate());
		}
		if (arg0.equals("org.eclipse.ui.edit.text.contentAssist.proposals")) {
			codeAssistNum++;
		}
		if (arg0.equals("org.eclipse.jdt.ui.edit.text.java.format")) {
			codeFormatNum++;
		}

	}

	@Override
	public void preExecute(String arg0, ExecutionEvent arg1) {
		// TODO Auto-generated method stub
		IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
		IWorkbenchPage workbenchPage = workbenchWindow.getPages()[0];
		if (arg0.equals("org.eclipse.ui.edit.paste")) {
			IEditorPart editorPart = workbenchPage.getActiveEditor();
			SourceViewer sourceViewer = (SourceViewer) editorPart.getAdapter(ITextOperationTarget.class);
			sourceViewer.getDocument().addDocumentListener(documentListener);
		}

	}

}
