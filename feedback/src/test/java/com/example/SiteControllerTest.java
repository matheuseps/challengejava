import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.example.model.Site;
import com.example.repository.SiteRepository;
import com.example.service.SiteService;
import com.example.web.SiteController;

import static org.mockito.Mockito.when;

@WebMvcTest(SiteController.class)
public class SiteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SiteRepository siteRepository;

    @MockBean
    private SiteService siteService;

    @Test
    public void testGetSites() throws Exception {
        when(siteService.findAll()).thenReturn(siteRepository.findAll());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/sites")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Site 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Site 2"));
    }

    @Test
    public void testCreateSite() throws Exception {
        Site site = new Site();
        site.setName("Test Site");
        site.setUrl("https://www.testsite.com");

        when(siteService.save(site)).thenReturn(siteRepository.save(site));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/sites")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Test Site\",\"url\":\"https://www.testsite.com\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Test Site"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("https://www.testsite.com"));
    }

    @Test
    public void testGetSite() throws Exception {
        Site site = new Site();
        site.setId(1L);
        site.setName("Site 1");
        site.setUrl("https://www.site1.com");

        when(siteRepository.findById(1L)).thenReturn(java.util.Optional.of(site));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/sites/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Site 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.url").value("https://www.site1.com"));
    }

    @Test
    public void testUpdateSite() throws Exception {
        Site site = new Site();
        site.setId(1L);
        site.setName("Site 1");
        site.setUrl("https://www.site1.com");

        when(siteRepository.findById(1L)).thenReturn(java.util.Optional.of(site));
        when(siteService.save(site)).thenReturn(siteRepository.save(site));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/sites/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Site 1 Updated\",\"url\":\"https://www.site1updated.com\"}