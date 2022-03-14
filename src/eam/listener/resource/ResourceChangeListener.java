package eam.listener.resource;

import java.util.ArrayList;

import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IResourceDeltaVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.source.projection.ProjectionAnnotationModel;

import org.omg.CORBA.PRIVATE_MEMBER;

import eam.utils.date.CurrentDate;
import eam.utils.http.UploadData;

public class ResourceChangeListener implements IResourceChangeListener {
	String projectName;
	String folderName;
	String fileName;
	UploadData request = new UploadData();
	CurrentDate date = new CurrentDate();

	@Override
	public void resourceChanged(IResourceChangeEvent arg0) {
		// TODO Auto-generated method stub

		try {
			arg0.getDelta().accept(new IResourceDeltaVisitor() {

				@Override
				public boolean visit(IResourceDelta delta) throws CoreException {
					// TODO Auto-generated method stub
					int flag = 0;
					String type = "";
					StringBuffer buffer = new StringBuffer(80);

					switch (delta.getKind()) {

					case IResourceDelta.ADDED:
						buffer.append("ADDED");
						type = "A";
						if (delta.getResource().toString().startsWith("F/")
								&& delta.getResource().toString().indexOf("/bin") == -1
								&& delta.getResource().toString().indexOf("/.") == -1) {
							folderName = delta.getResource().toString().split("F/")[1];
							// folderName = delta.getResource().getLocation().toString();
						} else if (delta.getResource().toString().startsWith("L/")
								&& delta.getResource().toString().indexOf("/bin") == -1
								&& delta.getResource().toString().indexOf("/.") == -1) {
							fileName = delta.getResource().toString().split("L/")[1];
							// fileName = delta.getResource().getLocation().toString();
						}

						break;
					case IResourceDelta.REMOVED:
						buffer.append("REMOVED");
						type = "R";
						if (delta.getResource().toString().startsWith("F/")
								&& delta.getResource().toString().indexOf("/bin") == -1
								&& delta.getResource().toString().indexOf("/.") == -1
								&& delta.getResource().toString().indexOf("/.") == -1) {
							folderName = delta.getResource().toString().split("F/")[1];
							// folderName = delta.getResource().getLocation().toString();
						} else if (delta.getResource().toString().startsWith("L/")
								&& delta.getResource().toString().indexOf("/bin") == -1
								&& delta.getResource().toString().indexOf("/.") == -1) {
							fileName = delta.getResource().toString().split("L/")[1];
							// fileName = delta.getResource().getLocation().toString();
						}
						break;
					case IResourceDelta.CHANGED:
						buffer.append("CHANGED");
						flag = 1;
						break;
					default:
						buffer.append("[");
						buffer.append(delta.getKind());
						buffer.append("]");
						break;
					}
					buffer.append(" ");
					buffer.append(delta.getResource().toString());
//					System.out.println(buffer);
					if ((delta.getResource().toString().startsWith("P/"))) {
						projectName = delta.getResource().toString().split("P/")[1];

					}

					if (buffer.toString().indexOf("ADDED P/") != -1) {
						// 创建项目
						request.doPost("Resource-NewProject", projectName, date.getDate());
					} else if (buffer.toString().indexOf("REMOVED P/") != -1) {
						// 删除项目
						request.doPost("Resource-RemoveProject", projectName, date.getDate());
					} else {
						if (flag != 1) {
							if (folderName != null) {
								if (type == "A")
									// 新建文件夹
									request.doPost("Resource-NewFolder", "/" + folderName, date.getDate());
								if (type == "R")
									// 删除文件夹
									request.doPost("Resource-RemoveFolder", "/" + folderName, date.getDate());
								folderName = null;
							}
							if (fileName != null) {
								if (type == "A")
									// 新建文件
									request.doPost("Resource-NewFile", "/" + fileName, date.getDate());
								if (type == "R")
									// 删除文件
									request.doPost("Resource-RemoveFile", "/" + fileName, date.getDate());
								fileName = null;
							}
						}

					}
					// System.out.println(buffer);
					return true;
				}
			});
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
