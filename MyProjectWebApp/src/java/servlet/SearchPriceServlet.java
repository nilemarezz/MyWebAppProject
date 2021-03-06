/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import jpa.controller.ProductJpaController;
import jpa.model.Product;

/**
 *
 * @author INT303
 */
public class SearchPriceServlet extends HttpServlet {

    @PersistenceUnit(unitName = "WebApplication1PU")
    EntityManagerFactory emf;

    @Resource
    UserTransaction utx;

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
        String min = request.getParameter("min");
        String max = request.getParameter("max");
        HttpSession session = request.getSession(false);
        ProductJpaController productJpaCtrl = new ProductJpaController(utx, emf);
        
                

        BigDecimal minPrice = new BigDecimal(min);
        BigDecimal maxPrice = new BigDecimal(max);
        
        int minP = Integer.valueOf(min);
        int maxP = Integer.valueOf(max);
        
        if(minP > maxP){
            getServletContext().getRequestDispatcher("/Product.jsp").forward(request, response);
            return;
        }
        
        List<Product> products = productJpaCtrl.findProductEntities();
        List<Product> productAdd = new ArrayList<>();

        
        for (Product productSet : products) {
            if (minPrice.compareTo(productSet.getProductprice()) == -1 && productSet.getProductprice().compareTo(maxPrice) == -1) {
                productAdd.add(productSet);
            }
        }
        session.setAttribute("products", productAdd);
        getServletContext().getRequestDispatcher("/Product.jsp").forward(request, response);
        
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
