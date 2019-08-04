package com.nieat.gateway.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nieat.gateway.utils.ResponseBody;
import org.apache.commons.lang3.ArrayUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.lang.NonNull;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author NieAt
 * @Description Spring Gateway 统一返回格式
 * @create 2019/6/30 0030 下午 20:36
 */
public class WrapperResponseBody implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpResponse response = exchange.getResponse();
        DataBufferFactory bufferFactory = response.bufferFactory();

        ServerHttpResponseDecorator responseDecorator = new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(@NonNull Publisher<? extends DataBuffer> body) {
                if (body instanceof Flux) {
                    Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                    // 封装后的数据
                    StringBuffer responseContent = new StringBuffer();
                    // 响应原数据
                    List<Byte> originalResponse = new ArrayList<>();
                    Flux<DataBuffer> dataBufferFlux = fluxBody.buffer().map(dataBuffers -> {
                        dataBuffers.forEach(dataBuffer -> {
                            byte[] content = new byte[dataBuffer.readableByteCount()];
                            dataBuffer.read(content);
                            originalResponse.addAll(Arrays.asList(ArrayUtils.toObject(content)));
                            DataBufferUtils.release(dataBuffer);
                        });
                        Byte[] originalResponseBytes = originalResponse.toArray(new Byte[originalResponse.size()]);
                        responseContent.append(new String(ArrayUtils.toPrimitive(originalResponseBytes), Charset.defaultCharset()));
                        ResponseBody responseBody = new ResponseBody();
                        responseBody.setCode(response.getStatusCode().value());
                        responseBody.setData(JSONObject.parse(responseContent.toString()));
                        return bufferFactory.wrap(JSON.toJSONString(responseBody).getBytes());
                    });
                    return super.writeWith(dataBufferFlux);
                }
                return super.writeWith(body);
            }

            @Override
            public Mono<Void> writeAndFlushWith(@NonNull Publisher<? extends Publisher<? extends DataBuffer>> body) {
                return writeWith(Flux.from(body).flatMapSequential(p -> p));
            }
        };
        return chain.filter(exchange.mutate().response(responseDecorator).build());
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
