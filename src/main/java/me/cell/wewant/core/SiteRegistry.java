package me.cell.wewant.core;



import me.cell.wewant.core.stie.Bhphotovideo;
import me.cell.wewant.core.stie.amazon.AmazonCN;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SiteRegistry {

    private Map<String, Crawler> registered = new HashMap<>();

    public static void main(String[] args) {
        SiteRegistry siteRegistry = new SiteRegistry();
        siteRegistry.init();
        Optional<Crawler> crawler = siteRegistry.getCrawler("https://www.amazon.cn/s?k=western+digital+elements+10tb&sprefix=western+digital+elements%2Caps%2C69&ref=nb_sb_ss_ts-doa-p_3_24");
        if (crawler.isPresent()) {
            System.out.println(crawler);
        } else {
            System.out.println("not found ");
        }
    }

    public void init() {
        registered.put("https://www.amazon.cn/", new AmazonCN());
        registered.put("https://www.bhphotovideo.com/", new Bhphotovideo());
    }

    public Optional<Crawler> getCrawler(String url) {
        Optional<Map.Entry<String, Crawler>> crawlerEntry = registered.entrySet().stream().filter(r -> url.startsWith(r.getKey()))
                .findFirst();
        if (crawlerEntry.isPresent()) {
            return Optional.of(crawlerEntry.get().getValue());
        }
        return Optional.empty();

    }
}
