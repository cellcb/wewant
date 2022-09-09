package me.cell.wewant.core.index;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

public class ES {

    private ElasticsearchClient elasticsearchClient;

    public ES(String host, Integer port){
            // Create the low-level client
            RestClient restClient;
            restClient = RestClient.builder(
                    new HttpHost(host, port)).build();

// Create the transport with a Jackson mapper
            ElasticsearchTransport transport = new RestClientTransport(
                    restClient, new JacksonJsonpMapper());

// And create the API client
            ElasticsearchClient client = new ElasticsearchClient(transport);
            this.elasticsearchClient = client;
    }


    @SneakyThrows
    public void index(Dei dei) {
//        Product product = new Product("bk-1", "City bike", 123.0);

        IndexResponse response = elasticsearchClient.index(i -> i
                        .index("wewant")
//                .id(product.getSku())
                        .document(dei)
        );

//        logger.info("Indexed with version " + response.version());
    }
}
