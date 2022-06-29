package fr.loual.mvcthymeleaf.web;

import fr.loual.mvcthymeleaf.entities.Patient;
import fr.loual.mvcthymeleaf.repositories.PatientRepository;
import fr.loual.mvcthymeleaf.security.repositories.AppRoleRepository;
import fr.loual.mvcthymeleaf.security.repositories.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
@AllArgsConstructor
public class PatientController {

    private PatientRepository patientRepository;
    private AppRoleRepository roleRepository;
    private AppUserRepository appUserRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping(path = "/user/index")
    public String patients(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "keywords", defaultValue = "") String keywords) {
        Page<Patient> pagePatients = patientRepository.findByNameContains(keywords, PageRequest.of(page,size));
        model.addAttribute("listPatients", pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keywords", keywords);
        return "patient";
    }

    @DeleteMapping("/admin/delete")
    public String delete(long id,
                         @RequestParam(defaultValue = "") String keywords,
                         @RequestParam(defaultValue = "0") int page) {
        patientRepository.deleteById(id);
        return "redirect:/user/index?page=" + page + "&keywords=" + keywords;
    }

    @GetMapping("/user/patients")
    @ResponseBody
    public List<Patient> listPatients() {
        return patientRepository.findAll();
    }

    @GetMapping(path = "/admin/formPatient")
    public String formPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "formPatient";
    }

    @PostMapping(path = "/admin/save")
    public String save(Model model,
                       @Valid Patient patient,
                       BindingResult bindingResult,
                       @RequestParam(defaultValue = "") String keywords,
                       @RequestParam(defaultValue = "0") int page) {
        if (bindingResult.hasErrors()) {
            return "formPatient";
        }
        patientRepository.save(patient);
        return "redirect:/user/index?page=" + page + "&keywords=" + keywords;
    }

    @GetMapping(path = "/admin/editPatient")
    public String editPatient(Model model, long id, @RequestParam(defaultValue = "") String keywords, @RequestParam(defaultValue = "0") int page) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if(patient == null) throw new RuntimeException("patient introuvable");
        model.addAttribute("patient", patient);
        model.addAttribute("page",page);
        model.addAttribute("keywords", keywords);
        return "editPatient";
    }

//    @GetMapping(path = "/register")
//    public String register(Model model) {
//        model.addAttribute("user", new AppUser());
//        return "register";
//    }//@TODO déplacer dans un UserController et refaire pour prendre en compte les changements avec spring security
    // @TODO faire une page de gestion des utilisateurs pour les admin

//    @PostMapping(path = "/registration")
//    public String registration(Model model, @Valid AppUser appUser, BindingResult bindingResult) {
//        if(!appUser.getPassword().equals(appUser.getConfirmPassword())) {
//            model.addAttribute("passwordMessage", "les mots de passe ne correspondent pas");
//            return "register";
//        }
//        if(bindingResult.hasErrors()) return "register";
//        AppUser u = appUserRepository.findByUsername(appUser.getUsername());
//        if(u != null) throw new RuntimeException("utilisateur déjà enregistré");
//        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
//        appUserRepository.save(appUser);
//        return "home";
//    }//@TODO déplacer dans un UserController

}

// dossier ressources/static pour css images etc