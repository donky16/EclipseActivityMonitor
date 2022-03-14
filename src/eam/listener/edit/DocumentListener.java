package eam.listener.edit;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.omg.CORBA.ARG_OUT;

import eam.utils.date.CurrentDate;
import eam.utils.http.UploadData;
import eam.utils.unicode.UnicodeUtils;

public class DocumentListener implements IDocumentListener {

	UploadData request = new UploadData();
	CurrentDate date = new CurrentDate();
	UnicodeUtils unicode = new UnicodeUtils();

	@Override
	public void documentAboutToBeChanged(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println(arg0.getOffset() + " " + arg0.getText() + " " +
		// arg0.getLength());
	}

	@Override
	public void documentChanged(DocumentEvent arg0) {
		// TODO Auto-generated method stub
		// System.out.println(arg0.getOffset() + " " + arg0.getText() + " " +
		// arg0.getText().length());
		if (arg0.getText().length() > 200 || arg0.getOffset() < 50) {
			// 粘贴偏移位置 arg0.getOffset(), 长度
			request.doPost("Edit-PastePY", Integer.toString(arg0.getText().length()), date.getDate());
		}
		if(20 < arg0.getText().length() && arg0.getText().length() < 5120) {
			IWorkbench workbench = PlatformUI.getWorkbench();
			IWorkbenchWindow workbenchWindow = workbench.getActiveWorkbenchWindow();
			IWorkbenchPage workbenchPage = workbenchWindow.getPages()[0];
			Base64.Encoder encoder = Base64.getEncoder();
			byte[] textByte;
			try {
				textByte = arg0.getText().getBytes("UTF-8");
				String encodedText = encoder.encodeToString(textByte);
				request.doPost("Edit-PasteContent", encodedText, workbenchPage.getActivePart().getTitle());
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
