package cart.controller;

import java.io.IOException;
import java.util.List;

import cart.model.dto.OrderDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet implementation class CartProductDeleteServlet
 */
@WebServlet("/product/cart/item/delete")
public class CartItemDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CartItemDeleteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// 取得要刪除的商品 id
		int index = Integer.parseInt(req.getParameter("index"));
		System.out.println("index = " + index);
		// 取得 session
		HttpSession session = req.getSession();
		// 取得購物車
		if (session.getAttribute("cart") != null) {
			List<OrderDTO> cart = (List<OrderDTO>) session.getAttribute("cart");
			cart.remove(index);
			// 回存給 session (可以不用加)
			session.setAttribute("cart", cart);
		}

		resp.sendRedirect("/JavaWebCart/product/cart");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
