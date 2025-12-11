package org.cowary.airmonitorback;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Configuration
public class BeanConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(createHttpClient()))
                .build();
    }

    private HttpClient createHttpClient() {
        return HttpClient.create(ConnectionProvider.newConnection()) // Новое соединение для каждого запроса
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) // Таймаут подключения
                .responseTimeout(Duration.ofSeconds(5))             // Таймаут ожидания ответа
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(5))   // Таймаут чтения после подключения
                                .addHandlerLast(new WriteTimeoutHandler(5))  // Таймаут записи после подключения
                );
    }
}
