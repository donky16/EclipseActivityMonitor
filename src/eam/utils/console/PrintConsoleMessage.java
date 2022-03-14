package eam.utils.console;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class PrintConsoleMessage {
	public MessageConsole console = null;
	public IConsoleManager consoleManager = null;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss EE");
	public PrintConsoleMessage() {
	    
		consoleManager = ConsolePlugin.getDefault().getConsoleManager();
	    IConsole[]consoles = consoleManager.getConsoles();
	    //System.out.println(consoles.length);
	    boolean findFlag = false;
	    if(consoles.length > 0){
	    	for(int i = 0; i < consoles.length; i ++) {
	    		if(consoles[i].getName().equals("监控插件控制台")) {
	    			console = (MessageConsole)consoles[i];
	    			findFlag = true;
	    		}
	    	}
	    } 
	    if(!findFlag){
	    	console = new MessageConsole("监控插件控制台", null);
	        consoleManager.addConsoles(new IConsole[] { console });
	    }  
	}
	public void printMessage(String message) {	
	    MessageConsoleStream  consoleStream = console.newMessageStream();
	    consoleManager.showConsoleView(console);
	    consoleStream.println(df.format(new Date()) + ": " + message);
	}
	public void printPoetry(String message) {	
	    MessageConsoleStream  consoleStream = console.newMessageStream();
	    consoleManager.showConsoleView(console);
	    consoleStream.println(message);
	}
}
