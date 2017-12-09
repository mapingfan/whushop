package com.whu.web.servlet;

import com.whu.domain.Product;
import com.whu.service.ProductService;
import com.whu.vo.History;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

@WebServlet(name = "Servlet6", urlPatterns = {"/productInfo"})
public class ProductInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        ProductService service = new ProductService();
        Product product = null;

        /*在这个地方记录浏览记录,只要记住商品的pid即可*/
        /*记录在session里即可*/
        HttpSession session = request.getSession();
        Object pids = session.getAttribute("pids");
        if (pids == null) {
            History tmp = new History(pid, new Date());
            Set<History> histories = new HashSet<>();
            histories.add(tmp);
            System.out.println(Arrays.toString(histories.toArray()));
            session.setAttribute("pids", histories);
        } else {
            Set<History> histories = (Set<History>) session.getAttribute("pids");
            History tmp = new History(pid, new Date());
            histories.add(tmp);
            System.out.println(Arrays.toString(histories.toArray()));
            session.setAttribute("pids", histories);
        }
        //根据session里的pid，获取商品集合；
        List<Product> historyProductList = new LinkedList<>();
        Set<History> historiesPid = (Set<History>) session.getAttribute("pids");
        List<History> historyList = new ArrayList<>();
        for (History history : historiesPid) {
            historyList.add(history);
        }
        Collections.sort(historyList);
        Collections.reverse(historyList);
        try {
            if (historyList != null) {
                for (History history : historyList) {
                    Product productByPid = service.findProductByPid(history.getPid());
                    historyProductList.add(productByPid);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (historyProductList.size() >= 8) {
            historyProductList = historyProductList.subList(0, 7);
        }

        session.setAttribute("historyProductList", historyProductList);
        try {
            product = service.findProductByPid(pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        request.setAttribute("product", product);
        request.getRequestDispatcher(request.getContextPath() + "/product_info.jsp").forward(request, response);

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
