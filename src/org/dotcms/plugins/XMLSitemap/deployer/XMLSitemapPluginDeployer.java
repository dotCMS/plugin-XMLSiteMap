package org.dotcms.plugins.XMLSitemap.deployer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.quartz.CronTrigger;

import com.dotmarketing.business.APILocator;
import com.dotmarketing.plugin.PluginDeployer;
import com.dotmarketing.plugin.business.PluginAPI;
import com.dotmarketing.quartz.CronScheduledTask;
import com.dotmarketing.quartz.QuartzUtils;
import com.dotmarketing.quartz.ScheduledTask;
import com.dotmarketing.util.Logger;
import com.dotmarketing.util.UtilMethods;

/**
 * This class create the Quartz Job that is going to create and update the XML Sitemap Index
 * for you sites. This job updates all the existing sites created under the dotcms server. 
 * @author Oswaldo Gallango
 * @version 1.1
 */
public class XMLSitemapPluginDeployer implements PluginDeployer {

	private PluginAPI pAPI = APILocator.getPluginAPI();
	private String pluginId = "org.dotcms.plugins.XMLSitemap"; 
	private String xmlSitemapJavaClass ="org.dotcms.plugins.XMLSitemap.thread.XMLSitemapThread";
	

	public boolean deploy() {
		try {

			String xmlSitemapJobName = pAPI.loadProperty(pluginId, "org.dotcms.plugins.XMLSitemap.JOB_NAME");
			String xmlSitemapJobGroup = pAPI.loadProperty(pluginId, "org.dotcms.plugins.XMLSitemap.JOB_GROUP");
			String xmlSitemapJobDescription = pAPI.loadProperty(pluginId, "org.dotcms.plugins.XMLSitemap.JOB_DESCRIPTION");
			String xmlSitemapCronExpression = pAPI.loadProperty(pluginId, "org.dotcms.plugins.XMLSitemap.CRON_EXPRESSION");
			
			CronScheduledTask cronScheduledTask = new CronScheduledTask(xmlSitemapJobName,
																		xmlSitemapJobGroup,
																		xmlSitemapJobDescription,
																		xmlSitemapJavaClass,
																		new Date(),
																		null,
																		CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW,
																		new HashMap<String, Object>(),
																		xmlSitemapCronExpression);
			
			QuartzUtils.scheduleTask(cronScheduledTask);

		}catch(Exception e){
			Logger.error(this,e.getMessage(),e);
		}

		return true;
	}

	public boolean redeploy(String version) {
		try {
			
			String xmlSitemapJobName = pAPI.loadProperty(pluginId, "org.dotcms.plugins.XMLSitemap.JOB_NAME");
			String xmlSitemapJobGroup = pAPI.loadProperty(pluginId, "org.dotcms.plugins.XMLSitemap.JOB_GROUP");
			String xmlSitemapJobDescription = pAPI.loadProperty(pluginId, "org.dotcms.plugins.XMLSitemap.JOB_DESCRIPTION");
			String xmlSitemapCronExpression = pAPI.loadProperty(pluginId, "org.dotcms.plugins.XMLSitemap.CRON_EXPRESSION");
			
			List<ScheduledTask> xmlSitemapSchedules = QuartzUtils.getScheduledTask(xmlSitemapJobName, xmlSitemapJobGroup);

			CronScheduledTask cronScheduledTask;
			for (ScheduledTask xmlSitemapSchedule: xmlSitemapSchedules) {
				if (UtilMethods.isSet(xmlSitemapSchedule)) {
					QuartzUtils.removeJob(xmlSitemapJobName, xmlSitemapJobGroup);
				}
				
				cronScheduledTask = new CronScheduledTask(xmlSitemapJobName,
														  xmlSitemapJobGroup,
														  xmlSitemapJobDescription,
														  xmlSitemapJavaClass,
														  new Date(),
														  null,
														  CronTrigger.MISFIRE_INSTRUCTION_FIRE_ONCE_NOW,
														  new HashMap<String, Object>(),
														  xmlSitemapCronExpression);
				
				QuartzUtils.scheduleTask(cronScheduledTask);
			}
		}catch(Exception e){
			Logger.error(this,e.getMessage(),e);
		}
		return true;
	}
}