## XML SITEMAPS PLUGIN


### Configuration:

This plugin allows you to configure the following properties on the conf/plugin.properties file:

1. Job Information: The sitemaps are generated on a recurrent basis based on a cron like expression defined on this property:
org.dotcms.plugins.XMLSitemap.CRON_EXPRESSION=1 1 1 * * ?
By default it runs once a day, this can be changed to run more often but you need to be careful with sites with a lot of content not to run it too often
so it doesn't affect the site's performance.
For more information on how to configure this expression click here: http://quartz.sourceforge.net/javadoc/org/quartz/CronTrigger.html
If you make a change in the cron expression you need to update the Plugin-Version in the Manifest.MF file and redeploy the plugin.

2. Links format: By default the links created for each content object in your sitemap files will be created as a permalink. If you don't want to use
permalinks you can make it so by changing these properties:
```
org.dotcms.plugins.XMLSitemap.USE_PERMALINKS=true to org.dotcms.plugins.XMLSitemap.USE_PERMALINKS=false
org.dotcms.plugins.XMLSitemap.USE_STRUCTURE_URL_MAP=true or org.dotcms.plugins.XMLSitemap.USE_STRUCTURE_URL_MAP=false
```


### Usage:

This plugin generates a sitemap index file for each host with the following path:
```
http://server.name.com/sitemap_index.xml
http://server1.name.com/sitemap_index.xml
http://server2.name.com/sitemap_index.xml
http://server3.name.com/sitemap_index.xml
```
Each sitemap index file will have a link to every sitemap file on your host. The plugin will create one sitemap file for every 50,000 entries. (50,000 URLs and must be no larger than 9MB (10,485,760))
and will compress it and add it to your sitemap index. Each file will be saved on your host under the folder: /XMLSitemaps/ with the name: xmlSitemapGenerated<N>.xml.gz.

Each sitemap file has a list of all the pages, folders, files and menu links that are noted as Show on Menu in dotCMS. It also adds one permalink entry for each piece of
content for each structure that has a detail page defined on your host.
 
This is an example of a generated sitemap file:
```xml
<?xml version='1.0' encoding='UTF-8'?>
<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.sitemaps.org/schemas/sitemap/0.9 http://www.sitemaps.org/schemas/sitemap/0.9/sitemap.xsd">
<url><loc>http://test.dotcms.org/</loc><lastmod>2009-07-08</lastmod></url>
<url><loc>http://test.dotcms.org/permalink/1876/1846</loc><lastmod>2008-06-02</lastmod></url>
<url><loc>http://test.dotcms.org/permalink/1889/1846</loc><lastmod>2008-06-02</lastmod></url>
<url><loc>http://test.dotcms.org/permalink/1899/1848</loc><lastmod>2008-05-29</lastmod></url>
<url><loc>http://test.dotcms.org/permalink/1901/1848</loc><lastmod>2008-05-29</lastmod></url>
<url><loc>http://test.dotcms.org/permalink/1903/1848</loc><lastmod>2008-05-29</lastmod></url>
<url><loc>http://test.dotcms.org/permalink/1893/1848</loc><lastmod>2008-05-29</lastmod></url>
<url><loc>http://test.dotcms.org/permalink/1897/1848</loc><lastmod>2008-05-29</lastmod></url>
<url><loc>http://test.dotcms.org/index.dot</loc><lastmod>2009-07-02</lastmod></url>
<url><loc>http://test.dotcms.org/getting_started/professional_support/index.dot</loc><lastmod>2009-07-08</lastmod></url>
<url><loc>http://test.dotcms.org/getting_started/professional_support/index.dot#vendor</loc><lastmod>2009-07-07</lastmod></url><url><loc>http://localhost/getting_started/professional_support/index.dot#design</loc><lastmod>2009-07-07</lastmod></url><url><loc>http://localhost/getting_started/professional_support/index.dot#integration</loc><lastmod>2009-07-07</lastmod></url><url><loc>http://localhost/getting_started/professional_support/index.dot#customer</loc><lastmod>2009-07-07</lastmod></url><url><loc>http://localhost/getting_started/macros/index.dot</loc><lastmod>2009-07-08</lastmod></url>
<url><loc>http://test.dotcms.org/webcasts.dot</loc><lastmod>2008-03-28</lastmod></url>
<url><loc>http://test.dotcms.org/community-support.dot</loc><lastmod>2008-10-22</lastmod></url>
<url><loc>http://test.dotcms.org/training.dot</loc><lastmod>2008-03-27</lastmod></url>
<url><loc>http://test.dotcms.org/a-news-page.dot</loc><lastmod>2008-10-10</lastmod></url>
</urlset>
```

### More Info:

If you need more info about sitemaps protocol you could check this page http://www.sitemaps.org/protocol.php

Here you can find search engine especific information on how to submit your sitemap:

* Google: http://www.google.com/support/webmasters/bin/answer.py?hl=en&answer=34609
* Yahoo: https://siteexplorer.search.yahoo.com/submit
* MSN: Submit via HTTP directly to: http://api.moreover.com/ping?u=http://yourdomain.com/yoursitemap.xml
* Ask.com: Submit via HTTP directly to: http://submissions.ask.com/ping?sitemap=http%3A//www.yourdomain.com/sitemap.xml
* Bing: http://www.bing.com/webmaster




