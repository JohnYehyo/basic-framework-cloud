package com.rongji.rjsoft.search.config;

import com.rongji.rjsoft.search.handler.MinaServerHandler;
import com.yskj.minaserver.TextLineChineseCodecFactory;
import com.yskj.minaserver.tcpKeepAliveMessageFactory;
import com.yskj.service.org.appSessionListener;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.serialization.ObjectSerializationCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.logging.MdcInjectionFilter;
import org.apache.mina.integration.beans.InetSocketAddressEditor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.beans.PropertyEditor;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description: 中心配置
 * @author: JohnYehyo
 * @create: 2021-11-09 16:01:51
 */
@Configuration
public class CenterConfig implements EnvironmentAware {

    private Environment env;

    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    @Bean
    public ThreadPoolTaskExecutor threadPool() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        // 线程池维护线程的最少数量
        pool.setCorePoolSize(20);
        // 线程池维护线程的最大数量
        pool.setMaxPoolSize(2000);
        pool.setQueueCapacity(5);
        pool.setKeepAliveSeconds(1);
        return pool;
    }

    @Bean
    public ExecutorFilter executorFilter() {
        return new ExecutorFilter(threadPool());
    }

    @Bean
    public MdcInjectionFilter mdcInjectionFilter() {
        return new MdcInjectionFilter(MdcInjectionFilter.MdcKey.valueOf("remoteAddress"));
    }

    @Bean
    public ProtocolCodecFilter codecFilter() {
        return new ProtocolCodecFilter(new TextLineChineseCodecFactory());
    }

    public ObjectSerializationCodecFactory objectSerializationCodecFactory() {
        ObjectSerializationCodecFactory objectSerializationCodecFactory = new ObjectSerializationCodecFactory();
        objectSerializationCodecFactory.setDecoderMaxObjectSize(2147483647);
        return objectSerializationCodecFactory;
    }

    @Bean
    public ProtocolCodecFilter codecFilter_obj() {
        return new ProtocolCodecFilter(objectSerializationCodecFactory());
    }

    @Bean
    public KeepAliveFilter keepAliveFilter() {
        KeepAliveFilter keepAliveFilter = new KeepAliveFilter(new tcpKeepAliveMessageFactory(),
                IdleStatus.BOTH_IDLE);
        keepAliveFilter.setRequestInterval(5);
        keepAliveFilter.setRequestTimeout(10);
        keepAliveFilter.setForwardEvent(true);
        return keepAliveFilter;
    }

    @Bean
    public LoggingFilter loggingFilter() {
        return new LoggingFilter();
    }

    @Bean
    public DefaultIoFilterChainBuilder filterChainBuilder() {
        DefaultIoFilterChainBuilder defaultIoFilterChainBuilder = new DefaultIoFilterChainBuilder();
        Map<String, IoFilter> filters = new LinkedHashMap<>();
        filters.put("codecFilter_obj", codecFilter_obj());
        filters.put("keepAliveFilter", keepAliveFilter());
        filters.put("mdcInjectionFilter", mdcInjectionFilter());
        filters.put("executor", executorFilter());
        defaultIoFilterChainBuilder.setFilters(filters);
        return defaultIoFilterChainBuilder;
    }

    @Bean
    public CustomEditorConfigurer customEditorConfigurer() {
        CustomEditorConfigurer customEditorConfigurer = new CustomEditorConfigurer();
        Map<Class<?>, Class<? extends PropertyEditor>> customEditors = new HashMap<>(1);
        customEditors.put(java.net.SocketAddress.class, new InetSocketAddressEditor().getClass());
        customEditorConfigurer.setCustomEditors(customEditors);
        return customEditorConfigurer;

    }

    /**
     * 端口号
     */
    @Bean
    public InetSocketAddress inetSocketAddress() {
        int port = Integer.parseInt(env.getProperty("yskj.mina.port"));
        return new InetSocketAddress(port);
    }

    @Bean
    public MinaServerHandler minaServerHandler(){
        return new MinaServerHandler();
    }


    @Bean
    public IoAcceptor ioAcceptor(){
        IoAcceptor ioAcceptor = new NioSocketAcceptor();
        ioAcceptor.setDefaultLocalAddress(inetSocketAddress());
        //自己实现的服务处理handler继承BaseServerHandler
        ioAcceptor.setHandler(minaServerHandler());
        ioAcceptor.setFilterChainBuilder(filterChainBuilder());
        try {
            ioAcceptor.bind();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ioAcceptor;
    }

}