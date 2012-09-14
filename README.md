plugin-XMLSiteMap
=================

Lastest Fixes
Enhancements Include 
1)	Configuration entry to exclude some structures with url map patters from site map 
2)	Configuration entry to remove a certain pattern from URL (this would be useful when we have custom url redirections defined on the webserver) 
Bug Fix's include 
1)	Current plugin was throwing null pointer exceptions when the url map pattern has more than one replacements - /index-profile/{assetClass}/{indexname} etc.. 
2)	URL's ending with index.dot have been corrected to just display the folder name (SEO friendly URL's) 
