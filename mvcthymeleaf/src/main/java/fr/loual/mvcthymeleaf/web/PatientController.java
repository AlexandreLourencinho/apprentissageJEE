package fr.loual.mvcthymeleaf.web;

import fr.loual.mvcthymeleaf.entities.Patient;
import fr.loual.mvcthymeleaf.entities.User;
import fr.loual.mvcthymeleaf.repositories.PatientRepository;
import fr.loual.mvcthymeleaf.repositories.RoleRepository;
import fr.loual.mvcthymeleaf.repositories.UserRepository;
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
import java.util.UUID;


@Controller
@AllArgsConstructor
public class PatientController {

    private PatientRepository patientRepository;
    private RoleRepository roleRepository;
    private UserRepository userRepository;
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

    @DeleteMapping("/admin/delete") // ne pas utiliser getmapping pour le delete - voir deletemapping
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

    @GetMapping(path = "/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }//@TODO déplacer dans un UserController

    @PostMapping(path = "/registration")
    public String registration(Model model, @Valid User user, BindingResult bindingResult) {
        if(!user.getPassword().equals(user.getConfirmPassword())) {
            model.addAttribute("passwordMessage", "les mots de passe ne correspondent pas");
            return "register";
        }
        if(bindingResult.hasErrors()) return "register";
        User u = userRepository.findByUsername(user.getUsername());
        if(u != null) throw new RuntimeException("utilisateur déjà enregistré");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "home";
    }//@TODO déplacer dans un UserController

}

// dossier ressources/static pour css images etc