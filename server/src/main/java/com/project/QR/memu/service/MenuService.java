package com.project.QR.memu.service;

import com.project.QR.memu.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class MenuService {
  private final MenuRepository menuRepository;

}
