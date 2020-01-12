package dey.sayantan.test.rabbit;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

public class AdderHandler {
	static ConnectionFactory factory;
	static Connection connection;
	static Channel channel;
	static ObjectMapper mapper = new ObjectMapper();

	private static void processAddEvent(String jsonString)
			throws JsonParseException, JsonMappingException, IOException {
		Double parameter1 = null;
		Double parameter2 = null;
		Map<String,Object> requestMap = mapper.readValue(jsonString, Map.class);
		parameter1 = (Double) requestMap.get("param1");
		parameter2 = (Double) requestMap.get("param2");

	}

	public static void main(String[] args) throws IOException, TimeoutException {
		factory = new ConnectionFactory();
		factory.setHost("localhost");
		// factory.setPort(15672);
		factory.setUsername("test");
		factory.setPassword("test");
		connection = factory.newConnection();
		channel = connection.createChannel();
		channel.queueDeclare("addQueue", false, false, false, null);
		channel.basicConsume("addQueue", true, new DeliverCallback() {

			@Override
			public void handle(String consumerTag, Delivery message) throws IOException {
				String jsonString = new String(message.getBody(), "UTF-8");
				processAddEvent(jsonString);

			}

		}, new CancelCallback() {

			@Override
			public void handle(String consumerTag) throws IOException {
				// TODO Auto-generated method stub

			}
		});
	}

}
