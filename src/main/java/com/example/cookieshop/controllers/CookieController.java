package com.example.cookieshop.controllers;

import com.example.cookieshop.models.Basket;
import com.example.cookieshop.models.Cookie;
import com.example.cookieshop.repositories.BasketRepository;
import com.example.cookieshop.repositories.CookieRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CookieController {
    CookieRepository repo = new CookieRepository();
    BasketRepository basketRepo = new BasketRepository();

    @GetMapping("/")
    public String index(HttpSession session) {
//        session.setAttribute("basket",1);
        return "index";
    }

    @GetMapping("/basket")
    public String basket(HttpSession session) {
        Basket basket = (Basket) session.getAttribute("basket");
        if (basket == null) {
            basket = new Basket(new ArrayList<>());
            session.setAttribute("basket", basket);
        }
        System.out.println(session.getAttribute("basket"));
        return "basket";
    }

    @GetMapping("/shop")
    public String basket(HttpSession session, Model cookieModel) {
        cookieModel.addAttribute("cookies", repo.getAllCookies());
        return "shop";
    }

    @GetMapping("/addToBasket")
    public String add(@RequestParam int id, HttpSession session) {
        Cookie cookie = repo.getCookieById(id);

        Basket basket = (Basket) session.getAttribute("basket");

        if (basket == null) basket = new Basket(new ArrayList<>());
        List<Cookie> cookies = basket.getCookieList();
        cookies.add(cookie);
        basket.setCookieList(cookies);
        session.setAttribute("basket", basket);
        System.out.println(session.getAttribute("basket"));
        return "redirect:/basket";
    }

    @GetMapping("/invalidate")
    public String invalidate(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }




}
