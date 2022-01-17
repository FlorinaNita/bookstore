package sda.store.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import sda.store.entities.BookEntity;
import sda.store.repositories.BookRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class BookController {

    public BookController() {
        System.out.println(getClass().getSimpleName() + " created");
    }

    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/frontpage")
    public ModelAndView getIndexPage() {
        ModelAndView modelAndView = new ModelAndView("frontpage");
        modelAndView.addObject("bookList", bookRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/book/add")
    public ModelAndView bookAdd(){
        ModelAndView modelAndView = new ModelAndView("bookAdd");
        modelAndView.addObject("book", new BookEntity());
        return modelAndView;
    }

    @PostMapping(value="/book/save", consumes = {"multipart/form-data"})
    public ModelAndView bookSave(@ModelAttribute("product") BookEntity productEntity,
                                 @RequestParam("file") MultipartFile file) throws IOException {
        ModelAndView modelAndView = new ModelAndView("redirect:/frontpage");
        String path1 = "target/classes/static/images";
        String path2 = "src/main/resources/static/images";
        String filename = file.getOriginalFilename();
        saveFile(path1, filename, file);
        saveFile(path2, filename, file);
        productEntity.setImagesUrl("/images/" + filename);
        bookRepository.save(productEntity);
        return modelAndView;
    }

    public void saveFile(String path, String fileName, MultipartFile file) throws IOException {
        Path uploadPath = Paths.get(path);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new IOException(ex.getMessage());
        }
    }
    @GetMapping("/book/view/{id}")
    public ModelAndView bookView(@PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView("bookView");
        modelAndView.addObject("book", bookRepository.getById(id));
        return modelAndView;
    }

    @GetMapping("/book/edit/{id}")
    public ModelAndView bookEdit(@PathVariable Integer id){
        ModelAndView modelAndView = new ModelAndView("bookEdit");
        modelAndView.addObject("book",bookRepository.getById(id));
        return modelAndView;
    }

    @PostMapping("/book/save")
    public ModelAndView bookSave(@ModelAttribute("book")BookEntity bookEntity){
        ModelAndView modelAndView = new ModelAndView("redirect:/frontpage");
        bookRepository.save(bookEntity);
        return modelAndView;
    }

    @GetMapping("/book/delete/{id}")
    public ModelAndView bookDelete(@PathVariable Integer id){
        BookEntity bookEntity = bookRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("No book found"));
        bookRepository.delete(bookEntity);
        return new ModelAndView("redirect:/frontpage");
    }

    @GetMapping("/specialOffers")
    public ModelAndView getSpecialOffersPage() {
        ModelAndView modelAndView = new ModelAndView("specialOffers");
        modelAndView.addObject("bookList", bookRepository.findByCategorySpecialOffers());
        return modelAndView;
    }

    @GetMapping("/bestsellers")
    public ModelAndView getBestsellersPage() {
        ModelAndView modelAndView = new ModelAndView("bestsellers");
        modelAndView.addObject("bookList", bookRepository.findByCategoryBestsellers());
        return modelAndView;
    }
    @GetMapping("/fiction")
    public ModelAndView getFictionPage() {
        ModelAndView modelAndView = new ModelAndView("fiction");
        modelAndView.addObject("bookList", bookRepository.findByCategoryFiction());
        return modelAndView;
    }

    @GetMapping("/nonfiction")
    public ModelAndView getNonfictionPage() {
        ModelAndView modelAndView = new ModelAndView("nonfiction");
        modelAndView.addObject("bookList", bookRepository.findByCategoryNonfiction());
        return modelAndView;
    }

    @GetMapping("/eBooks")
    public ModelAndView getEBooksPage() {
        ModelAndView modelAndView = new ModelAndView("eBooks");
        modelAndView.addObject("bookList", bookRepository.findByCategoryEbooks());
        return modelAndView;
    }

    @GetMapping("/article")
    public ModelAndView getArticlePage() {
        ModelAndView modelAndView = new ModelAndView("article");
        return modelAndView;
    }
}
