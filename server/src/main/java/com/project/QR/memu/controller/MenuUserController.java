package com.project.QR.memu.controller;

import com.project.QR.memu.mapper.MenuMapper;
import com.project.QR.memu.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/business/{business-id}")
@AllArgsConstructor
public class MenuUserController {
  private final MenuService menuService;
  private final MenuMapper mapper;
}
