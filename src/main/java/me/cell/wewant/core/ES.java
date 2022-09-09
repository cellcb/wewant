package me.cell.wewant.core;//package cell;
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.json.jackson.JacksonJsonpMapper;
//import co.elastic.clients.transport.ElasticsearchTransport;
//import co.elastic.clients.transport.rest_client.RestClientTransport;
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//
//public class ES {
//
//    public static ElasticsearchClient client() {
//        // Create the low-level client
//        RestClient restClient = RestClient.builder(
//                new HttpHost("192.168.5.249", 9200)).build();
//
//// Create the transport with a Jackson mapper
//        ElasticsearchTransport transport = new RestClientTransport(
//                restClient, new JacksonJsonpMapper());
//
//// And create the API client
//        ElasticsearchClient client = new ElasticsearchClient(transport);
//        return client;
//    }
//}
