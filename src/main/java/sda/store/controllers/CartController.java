package sda.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import sda.store.entities.CartEntity;
import sda.store.entities.UserEntity;
import sda.store.repositories.CartRepository;
import sda.store.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CartController {

    public CartController() {
        System.out.println(this.getClass().getSimpleName() + " created");
    }

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;


    public Optional<User> getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (null != auth && auth.getPrincipal() instanceof User) {
            User user = (User) auth.getPrincipal();
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @GetMapping("/cart")
    public ModelAndView getCart() {
        ModelAndView modelAndView = new ModelAndView("/cart");
        Optional<User> user = getLoggedInUser();
        if (user.isPresent()) {
            modelAndView.addObject("cart", cartRepository.findAllByUserEntity_Username(user.get().getUsername()));
            Integer userId = userRepository.findByUsername(user.get().getUsername()).getUserId();
            Long cartLength = cartRepository.countAllByUserId(userId);
            modelAndView.addObject("cartSize", cartLength);

            double totalCart = 0.0;
            List<CartEntity> carts = cartRepository.findAllByUserEntity_Username(user.get().getUsername());
            for (CartEntity item : carts) {
                totalCart += item.getTotal();
            }
            modelAndView.addObject("cart", carts);
            modelAndView.addObject("total", totalCart);
        } else {
            modelAndView.addObject("cart", new ArrayList<>());
        }
        return modelAndView;
    }


    @GetMapping("/delete-cart/{id}")
    public ModelAndView deleteCart(@PathVariable Integer id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/cart");
        Optional<CartEntity> cartEntity = cartRepository.findById(id);
        cartEntity.ifPresent(entity -> cartRepository.delete(entity));
        return modelAndView;
    }


    @GetMapping("/add-cart/{id}")
    public ModelAndView addCart(@PathVariable Integer id, CartEntity cartEntity) {
        ModelAndView modelAndView = new ModelAndView("redirect:/frontpage");

        Optional<User> user = getLoggedInUser();
        if (user.isPresent()) {
            UserEntity userEntity = userRepository.findByUsername(user.get().getUsername());
            cartEntity.setUserId(userEntity.getUserId());
            cartEntity.setBookId(id);
            cartEntity.setQuantity(1);
            cartRepository.save(cartEntity);
        } else {
            modelAndView.addObject("cart", new ArrayList<>());
        }
        return modelAndView;
    }

    @GetMapping("/successfully")
    public ModelAndView orderSuccessfully() {
        ModelAndView modelAndView = new ModelAndView("successfully");
        return modelAndView;
    }

    @GetMapping("/payment")
    public ModelAndView getPayment() {
        ModelAndView modelAndView = new ModelAndView("payment");
        return modelAndView;
    }
}
