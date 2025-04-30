package cart.controller;

import java.io.IOException;
import java.util.Base64;

import cart.service.ProductService;
import cart.service.impl.ProductServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

/**
 * Servlet implementation class ProductAddServlet
 */
@WebServlet("/product/add")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10) // 設定圖片上傳大小 10M
public class ProductAddServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProductService productService = new ProductServiceImpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductAddServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 接收一般表單資料
		String productName = req.getParameter("productName");
		String price = req.getParameter("price");
		String qty = req.getParameter("qty");
		// 接收上傳檔案資料
		// 將 Part 物件 -> 轉 byte[] -> 轉字串 -> 存入到資料表
		Part productImage = req.getPart("productImage");
		// 將 productImage 轉 byte[]
		byte[] bytes = productImage.getInputStream().readAllBytes();
		// 將 byte[] 轉 base64 字串方便儲存到資料庫中
		String productImageBase64 = Base64.getEncoder().encodeToString(bytes);

		// 儲存
		productService.add(productName, price, qty, productImageBase64);

		// 重導到 result.jsp
		String message = String.format(
				"商品新增成功<p/>商品名稱：%s<p/>商品價格：%s<p/>商品庫存：%s<p/>商品照片：<img src='data:image/png;base64,%s'><p/>", productName,
				price, qty, productImageBase64);
		req.setAttribute("resultTitle", "商品新增");
		req.setAttribute("resultMessage", message);
		req.getRequestDispatcher("/WEB-INF/view/cart/result.jsp").forward(req, resp);

	}

}
