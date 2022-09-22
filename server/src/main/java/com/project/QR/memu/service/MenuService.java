package com.project.QR.memu.service;

import com.project.QR.business.service.BusinessService;
import com.project.QR.exception.BusinessLogicException;
import com.project.QR.exception.ExceptionCode;
import com.project.QR.file.service.FileSystemStorageService;
import com.project.QR.memu.entity.Menu;
import com.project.QR.memu.repository.MenuRepository;
import com.project.QR.util.CustomBeanUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class MenuService {
  private final MenuRepository menuRepository;
  private final BusinessService businessService;
  private final FileSystemStorageService fileSystemStorageService;
  private final CustomBeanUtils<Menu> beanUtils;

  /**
   * 메뉴 등록
   */
  public Menu createMenu(Menu menu, MultipartFile multipartFile) {
    businessService.getBusiness(menu.getBusiness().getBusinessId(), menu.getBusiness().getMember().getMemberId());

    if(!multipartFile.isEmpty()) {
      menu.setImg(fileSystemStorageService.store(multipartFile,
        String.format("%d/menu", menu.getBusiness().getMember().getMemberId())));
    }
    return menuRepository.save(menu);
  }

  /**
   * 메뉴 변경
   */
  public Menu updateMenu(Menu menu, MultipartFile multipartFile) {
    businessService.getBusiness(menu.getBusiness().getBusinessId(), menu.getBusiness().getMember().getMemberId());
    Menu findMenu = findVerifiedMenu(menu.getMenuId(), menu.getBusiness().getBusinessId());
    if(!multipartFile.isEmpty()) {
      if(findMenu.getImg() != null) {
        fileSystemStorageService.remove(findMenu.getImg());
      }
      menu.setImg(fileSystemStorageService.store(multipartFile,
        String.format("%d/menu", menu.getBusiness().getMember().getMemberId())));
    }
    Menu updatingMenu = beanUtils.copyNonNullProperties(menu, findMenu);
    return menuRepository.save(updatingMenu);
  }

  /**
   * 메뉴 리스트 조회(관리자)
   */
  public Page<Menu> getAdminMenuList(long businessId, long memberId, int page, int size) {
    businessService.getBusiness(businessId, memberId);
    return menuRepository.findAllByBusinessId(businessId,
      PageRequest.of(page, size, Sort.by("MENU_ID").descending()));
  }

  /**
   * 메뉴 리스트 조회(사용자)
   */
  public Page<Menu> getUserMenuList(long businessId, int page, int size) {
    return menuRepository.findAllByBusinessId(businessId,
      PageRequest.of(page, size, Sort.by("MENU_ID").descending()));
  }

  /**
   * 메뉴 조회
   */
  @Transactional
  public Menu findMenu(long menuId, long businessId, Long memberId) {
    businessService.getBusiness(businessId, memberId);
    return findVerifiedMenu(menuId, businessId);
  }

  /**
   * 메뉴 삭제
   */
  public void deleteMenu(long menuId, long businessId, Long memberId) {
    businessService.getBusiness(businessId, memberId);
    Menu menu = findVerifiedMenu(menuId, businessId);
    if(menu.getImg() != null)
      fileSystemStorageService.remove(menu.getImg());
    menuRepository.delete(menu);
  }

  /**
   * 유효한 메뉴찾기
   */
  @Transactional(readOnly = true)
  private Menu findVerifiedMenu(long menuId, long businessId) {
    Optional<Menu> optionalMenu = menuRepository.findByIdAndBusinessId(menuId, businessId);
    return optionalMenu.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MENU_NOT_FOUND));
  }

  /**
   * 메뉴 등록 여부 확인
   */
  @Transactional(readOnly = true)
  private void existMenu(long menuId, long businessId) {
    Optional<Menu> optionalMenu = menuRepository.findByIdAndBusinessId(menuId, businessId);
    if(optionalMenu.isPresent())
      throw new BusinessLogicException(ExceptionCode.MENU_ALREADY_EXISTS);
  }
}
