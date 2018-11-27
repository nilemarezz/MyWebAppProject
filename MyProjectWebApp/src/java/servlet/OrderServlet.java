/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jpa.controller.AccountJpaController;
import jpa.controller.HistoryorderJpaController;
import jpa.controller.HistoryorderdetailJpaController;
import jpa.controller.exceptions.RollbackFailureException;
import jpa.model.Account;
import jpa.model.Historyorder;
import jpa.model.Historyorderdetail;
import model.LineItem;
import model.ShoppingCart;

/**
 *
 * @author Nile
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
        Account accountObj = (Account) session.getAttribute("account");
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

        AccountJpaController accountJpaCtrl = new AccountJpaController(utx, emf);
        HistoryorderJpaController historyOrderJpaCtrl = new HistoryorderJpaController(utx, emf);
        HistoryorderdetailJpaController historyOrderDetailJpaCtrl = new HistoryorderdetailJpaController(utx, emf);

        //*--- Start of order ---*
        int orderId = historyOrderJpaCtrl.getHistoryorderCount() + 1;
        Historyorder historyOrder = new Historyorder();

        historyOrder.setUsername(accountObj);
        historyOrder.setMethod("Debit Card");
        historyOrder.setOrderid(orderId);
        historyOrder.setPrice((int) cart.getTotalPrice());
        historyOrder.setAmount(cart.getTotalQuantity());
        historyOrder.setTimedate(new Date());

        List<Historyorder> orderList = historyOrderJpaCtrl.findHistoryorderEntities();
        List<Historyorder> orderAccount = new ArrayList<>();

        List<Historyorderdetail> orderProductDetail = new ArrayList<>();

        int orderDetailId = historyOrderDetailJpaCtrl.getHistoryorderdetailCount() + 1;
        Historyorderdetail orderDetail = new Historyorderdetail();
        
        for (LineItem productLineItems : cart.getLineItems()) {
            
            orderDetail.set(historyOrder);
            orderDetail.setOrderid(orderDetailId);
            orderDetail.setProductcode(productLineItems.getProduct());
            orderDetail.setProductprice((int) productLineItems.getSalePrice());
            orderDetail.setProductquantity(productLineItems.getQuantity());
            
            
        }

        for (Historyorder order : orderList) {
            orderAccount.add(order);
        }

        accountObj.setHistoryorderList(orderAccount);
        //*--- End of Order ---*

        try {
            accountJpaCtrl.edit(accountObj);
            historyOrderJpaCtrl.create(historyOrder);
            historyOrderDetailJpaCtrl.create(orderDetail);
        } catch (RollbackFailureException ex) {
            Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CheckOutServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        accountObj.setHistoryorderList(orderAccount);

        for (LineItem productLineItems : cart.getLineItems()) {
            cart.remove(productLineItems.getProduct());
        }

        getServletContext().getRequestDispatcher("/Thanks.jsp").forward(request, response);
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
