package me.cell.wewant.core;

import java.util.ArrayList;

public class ItemRegistry extends ArrayList<Item> {


    public static void main(String[] args) {
        ItemRegistry itemRegistry = new ItemRegistry();
        itemRegistry.init();

    }

    public void init() {
        Item wde10 = Item.of("western digital elements 10tb", "list", "https://www.amazon.cn/s?k=western+digital+elements+10tb&sprefix=western+digital+elements%2Caps%2C69&ref=nb_sb_ss_ts-doa-p_3_24", 1000, false);
        Item rf35 = Item.of("canon rf35 f1.8", "single", "https://www.bhphotovideo.com/c/product/1433714-REG/canon_rf_35mm_f_1_8_is.html", 479, false);
        Item rf35amazoncn = Item.of("canon rf35 f1.8", "single", "https://www.amazon.cn/dp/B07H9RZQ57/ref=sr_1_1?__mk_zh_CN=", 3500, false);
        Item kioxiaBG4256 = Item.of("东芝(Kioxia) 256GB PCIe NVMe 2230 SSD (KBG40ZNS256G) (OEM)", "single", "https://www.amazon.cn/dp/B09HY56CS4/ref=sr_1_1?crid=1LL5JPO53B2IN&keywords=nvme+2230&qid=1661048969&sprefix=%2Caps%2C62&sr=8-1", 350, false);
        Item PolarPro25 = Item.of("PolarPro - 偏振镜 Vario VND - 82 毫米 - 2/5 档 - 版本 II - Peter McKinnon", "single", "https://www.amazon.cn/gp/product/B08KJHVP3K/ref=ox_sc_saved_title_2?smid=A3CQWPW49OI3BQ&psc=1", 1400, false);
        add(wde10);
        add(PolarPro25);
        add(kioxiaBG4256);
        add(rf35);
    }


}
