package com.fst.learning.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testPublicEndpointAccessible() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(status().isOk());
    }

    @Test
    void testPublicCssAccessible() throws Exception {
        mockMvc.perform(get("/css/style.css"))
            .andExpect(status().isNotFound()); // Not found is expected if file doesn't exist
    }

    @Test
    void testPublicImagesAccessible() throws Exception {
        mockMvc.perform(get("/images/test.jpg"))
            .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "FORMATEUR")
    void testFormateurRoleAccess() throws Exception {
        mockMvc.perform(get("/formateur/dashboard"))
            .andExpect(status().isNotFound()); // Not found is ok as endpoint may not exist
    }

    @Test
    @WithMockUser(roles = "APPRENANT")
    void testApprenantRoleAccess() throws Exception {
        mockMvc.perform(get("/apprenant/courses"))
            .andExpect(status().isNotFound()); // Not found is ok as endpoint may not exist
    }

    @Test
    @WithMockUser(roles = "APPRENANT")
    void testApprenantCannotAccessFormateurEndpoint() throws Exception {
        mockMvc.perform(get("/formateur/manage"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "FORMATEUR")
    void testFormateurCannotAccessApprenantEndpoint() throws Exception {
        mockMvc.perform(get("/apprenant/mycourses"))
            .andExpect(status().isForbidden());
    }

    @Test
    void testUnauthenticatedUserCannotAccessProtectedEndpoint() throws Exception {
        mockMvc.perform(get("/apprenant/courses"))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void testLoginPageIsPublic() throws Exception {
        mockMvc.perform(get("/login"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void testLogoutEndpoint() throws Exception {
        mockMvc.perform(get("/logout").with(csrf()))
            .andExpect(status().isFound()); // Redirect is expected
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUndefinedRoleAccessDenied() throws Exception {
        mockMvc.perform(get("/apprenant/dashboard"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "FORMATEUR")
    void testFormateurAccessFormateurEndpoint() throws Exception {
        mockMvc.perform(get("/formateur/test"))
            .andExpect(status().isNotFound()); // Not found is expected
    }

    @Test
    @WithMockUser(roles = "APPRENANT")
    void testApprenantAccessApprenantEndpoint() throws Exception {
        mockMvc.perform(get("/apprenant/test"))
            .andExpect(status().isNotFound()); // Not found is expected
    }
}
