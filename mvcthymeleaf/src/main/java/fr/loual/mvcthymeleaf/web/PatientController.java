package fr.loual.mvcthymeleaf.web;

import fr.loual.mvcthymeleaf.entities.Patient;
import fr.loual.mvcthymeleaf.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@AllArgsConstructor
public class PatientController {

    private PatientRepository patientRepository;

    @GetMapping("/")
    public String home() {
        return "redirect:/index";
    }

    @GetMapping(path = "/index")
    public String patients(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "5") int size,
                           @RequestParam(name = "keywords", defaultValue = "") String keywords) {
        Page<Patient> pagePatients = patientRepository.findByNameContains(keywords, PageRequest.of(page,size));
        model.addAttribute("listPatients", pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keywords);
        return "patient";
    }

    @GetMapping("/delete") // ne pas utiliser getmapping pour le delete - voir deletemapping
    public String delete(long id, String keywords, int page) {
        patientRepository.deleteById(id);
        return "redirect:/index?page=" + page + "&keywords=" + keywords;
    }

    @GetMapping("/patients")
    @ResponseBody
    public List<Patient> listPatients() {
        return patientRepository.findAll();
    }

}

// dossier ressources/static pour css images etc