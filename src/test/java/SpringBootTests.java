import com.egen.Application;
import com.egen.spring.AlertsController;
import com.egen.spring.MetricsController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by Harjinder Singh on 5/4/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class SpringBootTests {

    final String BASE_URL = "http://localhost:8080/";

    private MockMvc mockMvcAlerts;
    private MockMvc mockMvcMetrics;

    @Before
    public void setup() {
        this.mockMvcAlerts = standaloneSetup(new AlertsController()).build();
        this.mockMvcMetrics = standaloneSetup(new MetricsController()).build();
    }

    @Test
    public void testMetricsCreate() throws Exception{
        MvcResult result =this.mockMvcMetrics.perform(post("/metrics/create")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"timestamp\":3232340,\"value\":130}"))
                            .andExpect(status().isOk())
                            .andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
                            .andReturn();
        String content = result.getResponse().getContentAsString().trim();
        System.out.println(content);
        Assert.assertEquals(true, content.contains("\"Timestamp\": 3232340,\n" +
                "          \"Value\": 130"));


        this.mockMvcMetrics.perform(post("/metrics/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"timestamp\":3232340,\"value\":200}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=ISO-8859-1"));

        result = this.mockMvcMetrics.perform(get("/metrics/read")
                .accept(MediaType.parseMediaType("application/json")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();

        content = result.getResponse().getContentAsString();
        Assert.assertEquals(true, content.contains("3232340"));
    }

    @Test
    public void testMetricsReadFilter() throws Exception{
        MvcResult result = this.mockMvcMetrics.perform(post("/metrics/readByTimeRange")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"minTime\":3232339,\"maxTime\":3232347}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assert.assertEquals(true, content.contains("3232340"));

    }


    @Test
    public void testMetricsRead() throws Exception{

        MvcResult result = this.mockMvcMetrics.perform(get("/metrics/read")
                .accept(MediaType.parseMediaType("application/json")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assert.assertEquals(true, content.contains("3232340"));
    }

    @Test
    public void testAlertsRead() throws Exception{

        MvcResult result = this.mockMvcAlerts.perform(get("/alerts/read")
                .accept(MediaType.parseMediaType("application/json")))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn();
        String content = result.getResponse().getContentAsString();
        Assert.assertEquals(true, content.contains("\"value\":180"));

    }

    @Test
    public void contextLoads() {
    }

}
