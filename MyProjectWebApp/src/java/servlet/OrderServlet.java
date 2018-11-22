/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jpa.model.Account;
import jpa.model.Orderdetail;
import jpa.model.Ordered;
import model.ShoppingCart;

/**
 *
 * @author Student
 */
public class OrderServlet extends HttpServlet {

    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        if(session != null){
             ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
             Account acc = (Account) session.getAttribute("username");
             
             for (Object object : cart) {
                Ordered order = new Ordered();
                order.setOrderdate(new Date());
                order.setOrderid(order.getOrderid()+1);
                order.setTotalprice(cart.getTotalPrice());
                order.setTotalquantity(cart.getTotalQuantity());
                order.setUsername(acc);
                
                Orderdetail orderdetail = new Orderdetail();
                orderdetail.setOrdered(order);
                orderdetail.setOrderid(orderdetail.getOrderid()+1);
                orderdetail.setProductcode(cart.);
            }
        }
        session.setAttribute("message", "Order");
        getServletContext().getRequestDispatcher("/History.jsp").forward(request, response);
        
     }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
