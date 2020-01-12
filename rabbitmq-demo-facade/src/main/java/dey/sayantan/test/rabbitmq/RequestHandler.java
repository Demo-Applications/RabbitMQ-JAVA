package dey.sayantan.test.rabbitmq;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RequestHandler {
	ConnectionFactory factory;
	Channel channel;

	public RequestHandler() throws IOException, TimeoutException {
		factory = new ConnectionFactory();
		initialize();
	}

	private void initialize() throws IOException, TimeoutException {
		factory.setHost("localhost");
		//factory.setPort(15672);
		factory.setUsername("test");
		factory.setPassword("test");
		Connection connection = factory.newConnection();
		channel = connection.createChannel();
		channel.queueDeclare("addQueue", false, false, false, null);
		channel.queueDeclare("divQueue", false, false, false, null);
	}

	public Double add(Double param1, Double param2) throws IOException {
		Double addResult = null;
		byte[] body = getBody(param1, param2);
		channel.basicPublish("", "addQueue", null, body);
		return param2;
	}

	public Double divide(Double param1, Double param2) {
		// TODO Auto-generated method stub
		return null;
	}

	private byte[] getBody(Double param1, Double param2) throws IOException {
		// Create the jsonFactory with an object mapper to serialize object to json
		JsonFactory jsonFactory = new JsonFactory(new ObjectMapper());

		// Create the byte array output stream
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		// Create the json generator
		JsonGenerator generator = jsonFactory.createGenerator(outputStream);

		// Write the start object, ie. {}
		generator.writeStartObject();
		// Write the car "parameter 1":{}
		generator.writeObjectField("param1", param1);
		// Write the car "parameter 2":{}
		generator.writeObjectField("param2", param1);
		// Close the object
		generator.writeEndObject();
		// And the generator
		generator.close();
		return outputStream.toByteArray();
	}

}
