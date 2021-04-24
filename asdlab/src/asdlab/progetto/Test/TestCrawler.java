package asdlab.progetto.Test;

import asdlab.progetto.Crawler.Crawler;
import asdlab.progetto.Crawler.CrawlerBFS;


public class TestCrawler {
	public static void main(String[] args) {
		if (args.length < 3){
	        System.out.println(
                    "sintassi: \n"
                            + "'TestCrawler startURL archivioDir numMaxDocs'");
		}else{
			String startUrl = args[0];
			String archivioDir = args[1];
			int numMaxDocs = 0;
			try{
				numMaxDocs = Integer.parseInt(args[2]);
			} catch (NumberFormatException e){
		        System.out.println(
	                    "sintassi: \n"
	                            + "'TestCrawler startURL archivioDir numMaxDocs'");
		        return;
			}
			Crawler crawler = new CrawlerBFS();
			crawler.crawl(startUrl, archivioDir, numMaxDocs, false);
					
			System.out.println("crawling completato.");
		}
	}
}
