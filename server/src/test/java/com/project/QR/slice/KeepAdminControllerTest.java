package com.project.QR.slice;
/*
import com.google.gson.Gson;
import com.project.QR.helper.WithMockCustomUser;
import com.project.QR.keep.dto.KeepRequestDto;
import com.project.QR.keep.dto.KeepResponseDto;
import com.project.QR.keep.entity.Keep;
import com.project.QR.keep.mapper.KeepMapper;
import com.project.QR.keep.service.KeepService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;


@WithMockCustomUser
@WebMvcTest(KeepAdminControllerTest.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class KeepAdminControllerTest {
  @Autowired
  private MockMvc mockmvc;

  @Autowired
  private Gson gson;

  @MockBean
  KeepService keepService;

  @MockBean
  KeepMapper mapper;

  @Test
  @DisplayName("자재 등록 테스트")
  public void createKeepTest() throws Exception {
    //given
    long businessId = 1L, qrCodeId = 1L;
    KeepRequestDto.CreateKeepDto createKeepDto = CreateStubData.createKeepDto();
    Keep keep = KeepStubData.keep(1L, "apple", "충주산", 10);
    KeepResponseDto.KeepInfoDto keepInfoDto = KeepStubData.createKeepInfoDto(keep);
    String content = gson.toJson(createKeepDto);

    given(mapper.createKeepDtoToKeep(Mockito.any(KeepRequestDto.CreateKeepDto.class))).willReturn(new Keep());
    given(keepService.createKeep(Mockito.any(Keep.class))).willReturn(keep);
    given(mapper.keepToKeepInfoDto(Mockito.any(Keep.class))).willReturn(keepInfoDto);

    //when
    ResultActions actions = mockmvc.perform(
            post("/api/v1/business/{business-id}/keep/qr-code/{qr-code-id}", businessId)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("Authorization", "Bearer {ACCESS_TOKEN}")
                    .content(objectMapper.writeValueAsString(createKeepDto))
    );


  }

}
*/