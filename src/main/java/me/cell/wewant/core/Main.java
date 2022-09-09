package me.cell.wewant.core;



import me.cell.wewant.core.index.Dei;
import me.cell.wewant.core.index.ES;
import me.cell.wewant.core.mail.Email;
import me.cell.wewant.tools.YDY;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

@Component
public class Main {


    // Conversion

    public static void main(String[] args) {
        if (args.length > 0 && args[0].equals("ydy")) {
            System.out.println("check in");
            YDY.checkin();
        }else {
            System.out.println("wewant");
            wewant();
        }
    }

    public static void wewant() {
        SiteRegistry siteRegistry = new SiteRegistry();
        siteRegistry.init();

        ItemRegistry items = new ItemRegistry();
        items.init();
        ES es = new ES("172.16.205.243", 9200);


        for (Item item : items) {
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
                            Email.send(dei.getName() + " : " + dei.getPrice(), dei.getUrl());
                        } catch (MessagingException | GeneralSecurityException e) {
                            System.out.println(e);
                        }
                    }
                }

                System.out.println("[" + item.getName() + "]  " + (result.isPresent() ? result.get().getPrice() + ":" + (result.get().getStockStatus() != null ? result.get().getStockStatus() : "unknow") : "failed"));
            }

        }
//        System.exit(0);
    }

}
