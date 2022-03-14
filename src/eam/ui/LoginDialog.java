package eam.ui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.core.commands.Command;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.NotEnabledException;
import org.eclipse.core.commands.NotHandledException;
import org.eclipse.core.commands.common.NotDefinedException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.commands.ICommandService;

import eam.utils.console.PrintConsoleMessage;
import eam.utils.http.RegisterSensor;
import eam.utils.http.UploadData;

public class LoginDialog extends Dialog {
	private Text usernameText;
	private Text snoText;
	public static final int RESET_ID = 101;
	RegisterSensor request = new RegisterSensor();
	PrintConsoleMessage printConsoleMessage = new PrintConsoleMessage();
	public LoginDialog(Shell shell) {
		super(shell);
	}

	@Override
	protected Control createContents(Composite parent) {
		Shell shell = this.getShell();
		shell.setSize(400, 200);
		Monitor primary = shell.getMonitor();
		Rectangle bounds = primary.getBounds();
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2 - 50;
		shell.setText("注册传感器");
		shell.setLocation(x, y);
		/* 布局开始 */
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(2, false);
		layout.marginBottom = 10;
		layout.marginTop = 10;
		layout.marginLeft = 10;
		layout.marginRight = 10;
		layout.horizontalSpacing = 30;
		layout.verticalSpacing = 10;
		composite.setLayout(layout);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		new Label(composite, SWT.NONE).setText("姓名");
		usernameText = new Text(composite, SWT.BORDER);
		usernameText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(composite, SWT.NONE).setText("学号");
		snoText = new Text(composite, SWT.BORDER);
		snoText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		return super.createContents(parent);
	}

	protected Button createButton(Composite parent, int id, String label, boolean defaultButton) {
		return null;
	}

	protected void initializeBounds() {
		// 取得按钮面板
		Composite comp = (Composite) getButtonBar();
		super.createButton(comp, IDialogConstants.OK_ID, "注 册", false);
		super.createButton(comp, IDialogConstants.CANCEL_ID, "取 消", false);
		super.initializeBounds();
	}

	@Override
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			String username = usernameText.getText();
			String sno = snoText.getText();

			if (username.isEmpty() || sno.isEmpty()) {
				MessageDialog msgDlg = new MessageDialog(this.getShell(), "注册失败", null, "注册失败，请重新输入姓名，学号！",
						MessageDialog.ERROR, new String[] { IDialogConstants.OK_LABEL }, 0);
				msgDlg.open();
				return;
			} else {
				String path = System.getProperty("user.home") + File.separator + "SensorId";
				String existId = request.findUser(username, sno);
				if (existId == null) {
					String id = request.doPost(username, sno);
					if (id != null) {		
						try {
							FileWriter fileWriter = new FileWriter(path);
							fileWriter.write(id);
							fileWriter.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						MessageDialog msgDlg = new MessageDialog(this.getShell(), "注册成功", null,
								"用户ID: " + id + "，配置文件地址: " + path, MessageDialog.CONFIRM,
								new String[] { IDialogConstants.OK_LABEL }, 0);
						msgDlg.open();
						printConsoleMessage.printMessage("注册成功，" + "用户ID: " + id + "，配置文件地址: " + path);
						UploadData.isConfigFile = true;
						UploadData.sensorId = Integer.valueOf(id);
						printConsoleMessage.printMessage("如需使用监控插件，请点击开始监控");
				
						return;
					} else {
						MessageDialog msgDlg = new MessageDialog(this.getShell(), "注册失败", null, "注册失败，请确认网络是否连通！",
								MessageDialog.ERROR, new String[] { IDialogConstants.OK_LABEL }, 0);
						msgDlg.open();
						return;
					}
				} else {
					System.out.println("重复注册，只写配置文件");
					try {
						FileWriter fileWriter = new FileWriter(path);
						fileWriter.write(existId);
						fileWriter.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					MessageDialog msgDlg = new MessageDialog(this.getShell(), "注册成功", null,
							"用户ID: " + existId + "，配置文件地址: " + path, MessageDialog.CONFIRM,
							new String[] { IDialogConstants.OK_LABEL }, 0);
					printConsoleMessage.printMessage("此用户已注册，" + "用户ID: " + existId + "，重写配置文件: " + path);
					UploadData.isConfigFile = true;
					UploadData.sensorId = Integer.valueOf(existId);
					printConsoleMessage.printMessage("如需使用监控插件，请点击开始监控");
					msgDlg.open();
					return;
				}
			}

		} else if (buttonId == RESET_ID) {
			this.close();
			this.open();
		}

		super.buttonPressed(buttonId);
	}
}