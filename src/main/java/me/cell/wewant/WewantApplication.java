package me.cell.wewant;

import me.cell.wewant.core.Main;
import me.cell.wewant.core.SiteRegistry;
import me.cell.wewant.core.index.ES;
import me.cell.wewant.core.repository.ItemRepositry;
import org.jobrunr.jobs.mappers.JobMapper;
import org.jobrunr.storage.InMemoryStorageProvider;
import org.jobrunr.storage.StorageProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WewantApplication {

	@Bean
	public StorageProvider storageProvider(JobMapper jobMapper) {
		InMemoryStorageProvider storageProvider = new InMemoryStorageProvider();
		storageProvider.setJobMapper(jobMapper);
		return storageProvider;
	}
	@Bean
	public ES es(){
		return new ES("172.16.205.243", 9200);
	}

	@Bean
	public SiteRegistry siteRegistry(){
		SiteRegistry siteRegistry = new SiteRegistry();
		siteRegistry.init();
		return siteRegistry;
	}

	@Bean
	public Main main(ItemRepositry itemRepositry, ES es, SiteRegistry siteRegistry){
		return new Main(itemRepositry, es, siteRegistry);
	}

	public static void main(String[] args) {
		SpringApplication.run(WewantApplication.class, args);
	}

}
