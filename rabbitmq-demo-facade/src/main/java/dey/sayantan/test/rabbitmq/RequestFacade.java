package dey.sayantan.test.rabbitmq;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeoutException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Cal/*")
public class RequestFacade extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private RequestHandler requestHandler;

	
	public RequestFacade() throws IOException, TimeoutException {
		requestHandler = new RequestHandler();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Double result = null;
		try {
			String Operation = request.getParameter("Service");
			Double param1 = Double.parseDouble(request.getParameter("Param1"));
			Double param2 = Double.parseDouble(request.getParameter("Param2"));
			result = calculate(param1, Operation, param2);
		} catch (Exception e) {
			response.sendError(500, e.getLocalizedMessage());
		}
		response.setContentType("text/html");
		PrintWriter outP = response.getWriter();
		outP.append(result.toString());

	}

	private Double calculate(Double param1, String operation, Double param2) throws IOException {
		Double calculationResult;
		switch (operation) {
		case "ADD":
			calculationResult = requestHandler.add(param1, param2);
			break;
		case "DIV":
			calculationResult = requestHandler.divide(param1, param2);
			break;
		default:
			throw new UnsupportedOperationException("Operation  not supported");
		}
		return calculationResult;
	}
}