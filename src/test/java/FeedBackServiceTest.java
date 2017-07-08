import app.core.helper.ApplicationError;
import app.dao.FeedBackDAO;
import app.entities.FeedBack;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import test.TestConfig;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:test/import.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "classpath:test/clear.sql")
})
@WebAppConfiguration
public class FeedBackServiceTest {
    @Autowired
    FeedBackDAO feedBackService;

    @Test
    public void testAddNew() throws ApplicationError {
        FeedBack data = new FeedBack();
        data.setSurname("test");
        data.setQuantity(3);
        data.setWillCome(true);

        FeedBack result = feedBackService.addNew(data);
        assertNotNull(result);
        assertNotNull(result.getId());
    }

    @Test
    public void testGetBySurname() throws ApplicationError {
        UUID testId = UUID.fromString("634035e8-fd0d-4e29-bf12-eeb07b601770"); // surname test
        List<FeedBack> result = feedBackService.getBySurname("test");

        assertNotNull(result);
        assertEquals(testId, result.get(0).getId());
    }

    @Test
    public void testupdate() throws ApplicationError, Exception {
        UUID testId = UUID.fromString("634035e8-fd0d-4e29-bf12-eeb07b601770"); // surname test

        FeedBack data = new FeedBack();
        data.setSurname("tester");
        data.setQuantity(3);
        data.setWillCome(false);

        List<FeedBack> result = feedBackService.update("test", data);

        assertNotNull(result);
        assertEquals("tester", result.get(0).getSurname());
        assertEquals(3, result.get(0).getQuantity().intValue());
        assertFalse(result.get(0).getWillCome());
    }
}
