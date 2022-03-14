package eam.listener.edit;

import java.util.Date;

import org.eclipse.jface.text.contentassist.ContentAssistEvent;
import org.eclipse.jface.text.contentassist.ICompletionListener;
import org.eclipse.jface.text.contentassist.ICompletionProposal;

public class CodeAssistListener implements ICompletionListener {

	@Override
	public void assistSessionEnded(ContentAssistEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(new Date() + ": 代码自动补全");
	}

	@Override
	public void assistSessionStarted(ContentAssistEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void selectionChanged(ICompletionProposal arg0, boolean arg1) {
		// TODO Auto-generated method stub

	}

}
