package org.dotcms.plugins.XMLSitemap.servlet;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dotmarketing.beans.Host;
import com.dotmarketing.business.APILocator;
import com.dotmarketing.business.UserAPI;
import com.dotmarketing.business.web.HostWebAPI;
import com.dotmarketing.business.web.HostWebAPIImpl;
import com.dotmarketing.portlets.files.model.File;
import com.dotmarketing.portlets.folders.business.FolderAPI;
import com.dotmarketing.portlets.folders.model.Folder;
import com.dotmarketing.util.Config;
import com.dotmarketing.util.Logger;
import com.dotmarketing.util.UtilMethods;
import com.dotmarketing.util.XMLUtils;

public class XMLSitemapServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private FolderAPI folderAPI = APILocator.getFolderAPI();
	private UserAPI userAPI = APILocator.getUserAPI();
	
	public void init(ServletConfig config) throws ServletException {

	}


	protected void service(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {

		ServletOutputStream out = response.getOutputStream();
		try {
			if (Config.CONTEXT == null) {
				Logger.debug(this, "Link not Found");
				response.sendError(404, "Link not Found");
				return;
			}

			HostWebAPI hostWebAPI = new HostWebAPIImpl();
			Host host = hostWebAPI.getCurrentHost(request);
			String hostId = host.getIdentifier();
			java.util.List<File> itemsList = new ArrayList<File>();

			
			Folder folder = folderAPI.findFolderByPath(APILocator.getPluginAPI().loadProperty("org.dotcms.plugins.XMLSitemap", "org.dotcms.plugins.XMLSitemap.XML_SITEMAPS_FOLDER"), hostId, userAPI.getSystemUser(), true);
			itemsList = folderAPI.getLiveFiles(folder, userAPI.getSystemUser(), true);

			if(itemsList.size() > 0){
				StringBuilder sitemapIndex =  new StringBuilder();
				sitemapIndex.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				sitemapIndex.append("<sitemapindex xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/siteindex.xsd\">");

				for (File itemChild : itemsList) {
					
					if (itemChild instanceof File) {
						File file = (File) itemChild;
						if (file.isWorking() && !file.isDeleted()) {
							sitemapIndex.append("<sitemap>");
							sitemapIndex.append("<loc>"+XMLUtils.xmlEscape("http://www."+host.getHostname()+file.getURI())+"</loc>");
							sitemapIndex.append("<lastmod>"+UtilMethods.dateToHTMLDate(file.getModDate(), "yyyy-MM-dd")+"</lastmod>");
							sitemapIndex.append("</sitemap>");
						}
					}
				}

				sitemapIndex.append("</sitemapindex>");
				out.print(sitemapIndex.toString());

			}else {
				Logger.debug(this, "No Index found");
				response.sendError(404, "Link not Found");
				return;
			}

		}catch(Exception e){
			Logger.error(this, "Error getting XML SiteMap Index file. "+e.getMessage());
		}finally{
			out.close();
		}
	}
}