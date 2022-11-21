package me.cell.wewant.core;

import me.cell.wewant.core.repository.ItemRepositry;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    Main main;
    @Autowired
    private ItemRepositry itemRepositry;

    @PostMapping("")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        return new ResponseEntity<>(itemRepositry.save(item), HttpStatus.OK);
    }

    @GetMapping("/{id}/test")
    public ResponseEntity<Item> testCrawl(@PathVariable("id") long id) {
        Optional<Item> itemData = itemRepositry.findById(id);
        main.crawl(itemData.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("")
    public ResponseEntity<List<Item>> getItems() {
        return new ResponseEntity<>(itemRepositry.findByOrderByIdDesc(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable("id") long id, @RequestBody Item item) {
        Optional<Item> itemData = itemRepositry.findById(id);
        if (itemData.isPresent()) {
            Item _item = itemData.get();
            BeanUtils.copyProperties(item, _item, "id");
            return new ResponseEntity<>(itemRepositry.save(_item), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteItem(@PathVariable Long id) {
        itemRepositry.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("load")
    public ResponseEntity loadItemConfig() {
        ItemRegistry itemRegistry = new ItemRegistry();
        itemRegistry.loadItem();
        itemRepositry.saveAll(itemRegistry);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("dump")
    public ResponseEntity dump() {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(true);
        Yaml yaml = new Yaml(options);
        List<Item> all = itemRepositry.findAll();
        String s = yaml.dump(all);
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter("./dump.yaml"));
            writer.write(s);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
