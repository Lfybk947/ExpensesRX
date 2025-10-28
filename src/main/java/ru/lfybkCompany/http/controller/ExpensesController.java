package ru.lfybkCompany.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.lfybkCompany.database.entity.ExpensesColumns;
import ru.lfybkCompany.dto.fileDto.FileUpload;
import ru.lfybkCompany.dto.filterDto.ExpensesFilter;
import ru.lfybkCompany.dto.filterDto.ExpensesSessionFilter;
import ru.lfybkCompany.exception.FileProcessingException;
import ru.lfybkCompany.exception.FileUploadException;
import ru.lfybkCompany.service.entityService.*;
import ru.lfybkCompany.service.fileUploadService.FileUploadToDBService;

@Controller
@SessionAttributes("expensesSessionFilter")
@RequestMapping("/expenses")
@RequiredArgsConstructor
public class ExpensesController {
    private final ExpensesService expensesService;
    private final CategoriesService categoriesService;
    private final CurrencyOperationsService currencyOperationsService;
    private final DescriptionsService descriptionsService;
    private final UserService userService;
    private final FileUploadToDBService fileUploadToDBService;

    @ModelAttribute("expensesSessionFilter")
    public ExpensesSessionFilter setupExpensesFilter() {
        return new ExpensesSessionFilter();
    }


    @GetMapping
    public String findAllByFilter(Model model,
                                  @ModelAttribute("expensesSessionFilter") ExpensesSessionFilter filter) {
        try {
            model.addAttribute("fileUpload", new FileUpload());
            model.addAttribute("expensesSessionFilter", filter);
            model.addAttribute("selectedCategories", categoriesService.findAll());
            model.addAttribute("selectedDescriptions", descriptionsService.findAll());
            model.addAttribute("selectedCurrencyOperations", currencyOperationsService.findAll());
            model.addAttribute("selectedUsers", userService.findAll());
            model.addAttribute("userAuth", userService.getAuthorizationUser().get());
            model.addAttribute("columnsExpenses", ExpensesColumns.values());
            model.addAttribute("expensesi", expensesService.findAllByFilter(filter));
        } catch (IllegalArgumentException e) {
            model.addAttribute("errors", e.getMessage());
        }

        return "expenses/expenses";
    }

    @PostMapping("/filter")
    public String applyFilter(@ModelAttribute("expensesSessionFilter") @Validated ExpensesSessionFilter filter,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes,
                              Model model) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addAttribute("org.springframework.validation.BindingResult.expensesSessionFilter", bindingResult);
            return "redirect:/expenses";
        }

        return "redirect:/expenses";
    }

    @PostMapping("/upload")
    public String create(@ModelAttribute FileUpload file,
                         RedirectAttributes redirectAttributes) {
        try {
            redirectAttributes.addFlashAttribute("fileName",
                    fileUploadToDBService.uploadExpensesFileData(file.getMultipartFile()));
        } catch (FileProcessingException e) {
            redirectAttributes.addFlashAttribute("errorFile", ("Incorrect processing file, %S").formatted(e.getMessage()));
            return "redirect:/expenses";
        } catch (FileUploadException e) {
            redirectAttributes.addFlashAttribute("errorFile", ("Incorrect file, %S").formatted(e.getMessage()));
            return "redirect:/expenses";
        }

        return "redirect:/expenses";
    }

    @PostMapping("/filter/reset")
    public String resetFilter(@ModelAttribute("expensesSessionFilter") ExpensesSessionFilter filter) {
        filter.setFromDate(null);
        filter.setToDate(null);
        filter.setFromSum(null);
        filter.setToSum(null);
        filter.setCurrencyOperations(null);
        filter.setCategories(null);
        filter.setDescriptions(null);
        filter.setUsers(null);
        return "redirect:/expenses";
    }


}
