package com.nnamdi.transaction;

//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import com.nnamdi.transaction.service.MessageSenderService;
import com.nnamdi.transaction.service.MessageSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableBinding(MessageSenderService.class)
public class TransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionApplication.class, args);
	}
//	@Value("${spring.rabbitmq.host}")
//	private String host;
//
//	@Value("${spring.rabbitmq.username}")
//	private String username;
//
//	@Value("${spring.rabbitmq.password}")
//	private String password;
//
//	@Value("${rabbitmq.url}")
//	private String rabbitMQUri;
//
//
//
//	@Bean
//	CachingConnectionFactory connectionFactory() {
//		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(host);
//		cachingConnectionFactory.setUri(rabbitMQUri);
//		cachingConnectionFactory.setRequestedHeartBeat(30);
//		cachingConnectionFactory.setConnectionTimeout(30);
////		cachingConnectionFactory.setUsername(username);
////		cachingConnectionFactory.setPassword(password);
//
//		return cachingConnectionFactory;
//	}
//
//
//	@Bean
//	public MessageConverter jsonMessageConverter() {
//		return  new Jackson2JsonMessageConverter();
//	}
//
//	@Bean
//	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//		rabbitTemplate.setMessageConverter(jsonMessageConverter());
//		return rabbitTemplate;
//	}

}
