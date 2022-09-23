package com.project.QR.slice;

import com.project.QR.helper.WithMockCustomUser;
import com.project.QR.reservation.controller.ReservationStatisticsController;
import com.project.QR.review.controller.ReviewAdminController;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;

@WithMockCustomUser
@WebMvcTest(ReviewAdminController.class)
@AutoConfigureMockMvc(addFilters = false)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class ReviewAdminControllerTest {
}
