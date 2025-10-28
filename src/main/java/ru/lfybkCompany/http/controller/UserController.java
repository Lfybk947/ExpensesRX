package ru.lfybkCompany.http.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.lfybkCompany.database.entity.Gender;
import ru.lfybkCompany.database.entity.Role;
import ru.lfybkCompany.dto.createReadDto.UserCreateEditDto;
import ru.lfybkCompany.dto.filterDto.UserFilter;
import ru.lfybkCompany.dto.createReadDto.UserReadDto;
import ru.lfybkCompany.dto.createReadDto.UserUpdateEditDto;
import ru.lfybkCompany.dto.responseDto.PageResponse;
import ru.lfybkCompany.service.entityService.UserService;


@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/registration")
    public String registration(Model model,
                               @ModelAttribute("user") UserCreateEditDto user,
                               BindingResult bindingResult) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        model.addAttribute("genders", Gender.values());
        model.addAttribute("errors", bindingResult.getAllErrors());

        return "user/registration";
    }

    @GetMapping
    public String findAllByFilter(Model model, @Validated UserFilter filter,
                                  BindingResult bindingResult,
                                  RedirectAttributes redirectAttributes,
                                  Pageable pageable){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users";
        }
        var UserReadDto = userService.findAllByFilter(filter, pageable);
        model.addAttribute("users", PageResponse.of(UserReadDto));
        model.addAttribute("filter", filter);
        model.addAttribute("roles", Role.values());
        model.addAttribute("genders", Gender.values());
        return "user/users";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model,
                           @CurrentSecurityContext SecurityContext securityContext,
                           @AuthenticationPrincipal UserDetails userDetails) {
        return userService.findById(id)
                .map(userReadDto-> {
                    model.addAttribute("user", userReadDto);
                    model.addAttribute("roles", Role.values());
                    model.addAttribute("genders", Gender.values());
                    return "user/user";
                }).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public String create(@ModelAttribute @Validated UserCreateEditDto user,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users/registration";
        }
        UserReadDto userReadDto = userService.create(user);
        return "redirect:/users/" + userReadDto.id();
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute @Validated UserUpdateEditDto user,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/users/{id}";
        }
        return userService.update(id, user)
                .map(it-> "redirect:/users/{id}")
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (!userService.delete(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/users";
    }

}
