package me.cell.wewant.core;


import me.cell.wewant.core.index.Dei;
import me.cell.wewant.core.index.ES;
import me.cell.wewant.core.mail.Email;
import me.cell.wewant.core.repository.ItemRepositry;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class Main {


    private ItemRepositry itemRepositry;

    private ES es;

    private SiteRegistry siteRegistry;

    public Main(ItemRepositry itemRepositry, ES es, SiteRegistry siteRegistry) {
        this.itemRepositry = itemRepositry;
        this.es = es;
        this.siteRegistry = siteRegistry;
    }


    public static void main(String[] args) {
//        if (args.length > 0 && args[0].equals("ydy")) {
//            System.out.println("check in");
//            YDY.checkin(0);
//        }else {
//            System.out.println("wewant");
//            wewant();
//        }
    }

    public void wewant() {


        List<Item> items = itemRepositry.findAll();


//        Stream<Optional<Crawler>> crawlers = items.stream().map(item -> siteRegistry.getCrawler(item.getUrl())).filter(Optional::isPresent);

//        Stream<Item> singleTypeItems = items.stream().filter(item -> item.getType().equals("single"));
//        Stream<Item> listTypeItems = items.stream().filter(item -> item.getType().equals("list"));

//        singleTypeItems.map(item -> siteRegistry.getCrawler(item.getUrl())).filter(Optional::isPresent)


        for (Item item : items) {
            crawl(item);

        }
//        System.exit(0);
    }

    public void crawl(Item item) {
        Optional<Crawler> optionalCrawler = siteRegistry.getCrawler(item.getUrl());
        if (optionalCrawler.isPresent()) {
            Crawler crawler = optionalCrawler.get();
            Optional<Result> result;
            if (item.getType().equals("list")) {
                result = crawler.list(item.getUrl());
            } else {
                result = crawler.single(item.getUrl());
            }
            if (result.isPresent()) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.CHINA);
                Date catchTime = new Date(System.currentTimeMillis());
                Dei dei = Dei.builder().url("").catchTime(sdf.format(catchTime)).name(item.getName()).price(result.get().getPrice()).build();
                es.index(dei);

                if (dei.getPrice().intValue() <= item.getExpect()) {
                    try {
                        if (item.getNotify()) {
                            Email.send(dei.getName() + " : " + dei.getPrice(), "商品链接:" + dei.getUrl());
                        }
                    } catch (MessagingException | GeneralSecurityException e) {
                        System.out.println(e);
                    }
                }
            }

            System.out.println("[" + item.getName() + "]  " + (result.isPresent() ? result.get().getPrice() + ":" + (result.get().getStockStatus() != null ? result.get().getStockStatus() : "unknow") : "failed"));
        }
    }

}
