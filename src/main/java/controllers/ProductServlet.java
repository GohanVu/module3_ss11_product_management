package controllers;

import model.Product;
import service.ProductService;
import service.ProductServiceImpl;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "ProductServlet", value = "/products")
public class ProductServlet extends HttpServlet {
    private  final ProductService productService = new ProductServiceImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null){
            action="";
        }
        switch (action){
            case "create":
                showCreateForm(req,resp);
                break;
            case "edit":
                showEditForm(req,resp);
                break;
            case "delete":
                showDeleteForm(req,resp);
                break;
            case "view":
                viewProduct(req,resp);
                break;
            case "search":
                showSearchForm(req,resp);
                break;
            default:
                listProducts(req,resp);
                break;
        }
    }

    private void showSearchForm(HttpServletRequest req, HttpServletResponse resp) {
        List<Product> products = this.productService.findAll();
        req.setAttribute("products", products);

        RequestDispatcher dispatcher = req.getRequestDispatcher("product/search.jsp");
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listProducts(HttpServletRequest req, HttpServletResponse resp) {
        List<Product> products = this.productService.findAll();
        req.setAttribute("products", products);
        RequestDispatcher dispatcher = req.getRequestDispatcher("product/list.jsp");
        try {
            dispatcher.forward(req, resp);
        } catch (ServletException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    private void viewProduct(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = this.productService.findById(id);
        RequestDispatcher dispatcher ;
        if(product == null){
            dispatcher = req.getRequestDispatcher("error-404.jsp");
        } else {
            req.setAttribute("product",product);
            dispatcher = req.getRequestDispatcher("product/view.jsp");
        }
        try {
            dispatcher.forward(req,resp);
        } catch (ServletException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    private void showDeleteForm(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = this.productService.findById(id);
        RequestDispatcher dispatcher;
        if (product == null){
            dispatcher = req.getRequestDispatcher("error-404.jsp");
        } else {
            req.setAttribute("product", product);
            dispatcher = req.getRequestDispatcher("product/delete.jsp");
        }
        try {
            dispatcher.forward(req,resp);
        } catch (ServletException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = this.productService.findById(id);
        RequestDispatcher dispatcher;
        if (product == null){
            dispatcher = req.getRequestDispatcher("error-404.jsp");
        } else {
            req.setAttribute("product", product);
            dispatcher = req.getRequestDispatcher("product/edit.jsp");
        }
        try {
            dispatcher.forward(req,resp);
        } catch (ServletException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) {
        RequestDispatcher dispatcher = req.getRequestDispatcher("product/create.jsp");
        try {
            dispatcher.forward(req,resp);
        } catch (ServletException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null){
            action = "";
        }
         switch (action){
             case "create":
                 createProduct(req,resp);
                 break;
             case "edit":
                 updateProduct(req,resp);
                 break;
             case "delete":
                 deleteProduct(req,resp);
                 break;
             case "search":
                 searchProduct(req,resp);
                 break;
             default:
                 break;
         }
    }

    private void searchProduct(HttpServletRequest req, HttpServletResponse resp) {
        List<Product> products = new ArrayList<>();
        String option = req.getParameter("option");
        switch (option){
            case "id":
                int id = Integer.parseInt(req.getParameter("id"));
                Product product = productService.findById(id);
                if (product != null){
                    products.add(product);
                }
                break;
            case "name" :
                String name = req.getParameter("name");
                products = productService.searchByName(name);
                break;
            case "Price":
                double minPrice = Double.parseDouble(req.getParameter("minPrice"));
                double maxPrice = Double.parseDouble(req.getParameter("maxPrice"));
                products = productService.searchByPrice(minPrice,maxPrice);
                break;
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("/product/search.jsp");
        req.setAttribute("products",products);
        if(products.size() == 0){
            req.setAttribute("message","Product not found");
        } else {
            req.setAttribute("message","Product Found");
            try {
                dispatcher.forward(req,resp);
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void deleteProduct(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        Product product = productService.findById(id);
        RequestDispatcher dispatcher ;
        if(product ==null){
            dispatcher = req.getRequestDispatcher("error-404.jsp");
        } else {
            productService.remove(id);
            try {
                resp.sendRedirect("/products");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateProduct(HttpServletRequest req, HttpServletResponse resp) {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        double price = Double.parseDouble(req.getParameter("price"));
        String producer = req.getParameter("producer");
        String description = req.getParameter("description");
        Product product = productService.findById(id);
        RequestDispatcher dispatcher;
        if (product == null){
            dispatcher = req.getRequestDispatcher("error-404.jsp");
        } else {
            product.setName(name);
            product.setPrice(price);
            product.setProducer(producer);
            product.setDescription(description);
            productService.update(id, product);
            req.setAttribute("product", product);
            req.setAttribute("message", "Product information was updated");
            dispatcher = req.getRequestDispatcher("product/edit.jsp");
        }
        try {
            dispatcher.forward(req,resp);
        } catch (ServletException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void createProduct(HttpServletRequest req, HttpServletResponse resp) {
        String name = req.getParameter("name");
        double price = Double.parseDouble(req.getParameter("price"));
        String description = req.getParameter("description");
        String producer = req.getParameter("producer");
        int id = (int) (Math.random()*1000);
        Product product= new Product(id,name,price,producer,description);
        productService.save(product);
        RequestDispatcher dispatcher = req.getRequestDispatcher("product/create.jsp");
        req.setAttribute("message", "New product was created");
        try {
            dispatcher.forward(req,resp);
        } catch (ServletException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}