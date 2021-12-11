package com.apress.prospring5.ch13.mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import com.apress.prospring5.ch13.SingerController;
import com.apress.prospring5.ch13.entities.Singer;
import com.apress.prospring5.ch13.entities.Singers;
import com.apress.prospring5.ch13.services.SingerService;
import org.junit.Before;
import org.junit.Test;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;

public class SingerControllerTest {
    private final List<Singer> singers = new ArrayList<>();

    @Before
    public void initSingers() {
        Singer singer = new Singer();
        singer.setId(1l);
        singer.setFirstName("John");
        singer.setLastName("Mayer");
        singers.add(singer);
    }

    @Test
    public void testList() throws Exception {
        SingerService singerService = mock(SingerService.class);
        when(singerService.findAll()).thenReturn(singers);

        SingerController singerController = new SingerController();

        ReflectionTestUtils.setField(singerController, "singerService", singerService);

        ExtendedModelMap uiModel = new ExtendedModelMap();
        uiModel.addAttribute("singers", singerController.listData());

        Singers modelSingers = (Singers) uiModel.get("singers");

        assertEquals(1, modelSingers.getSingers().size());
    }

    @Test
    public void testCreate() {
        final Singer newSinger = new Singer();
        newSinger.setId(999l);
        newSinger.setFirstName("Stevie");
        newSinger.setLastName("Vaughan");

        SingerService singerService = mock(SingerService.class);
        when(singerService.save(newSinger)).thenAnswer(invocation -> {
			singers.add(newSinger);
			return newSinger;
		});

        SingerController singerController = new SingerController();
        ReflectionTestUtils.setField(singerController, "singerService",
                singerService);

        Singer singer = singerController.create(newSinger);
        assertEquals(Long.valueOf(999l), singer.getId());
        assertEquals("Stevie", singer.getFirstName());
        assertEquals("Vaughan", singer.getLastName());

        assertEquals(2, singers.size());
    }

    @Test
    public void testFindById() {
        final Singer newSinger = new Singer();
        Long id = 100L;
        newSinger.setId(id);
        newSinger.setFirstName("Jaebin");
        newSinger.setLastName("Joo");
        newSinger.setBirthDate(Date.valueOf("1996-06-15"));

        SingerService singerService = mock(SingerService.class);
        when(singerService.findById(id)).thenAnswer((Answer<Singer>) invocation -> {
            singers.add(newSinger);
            return singers.stream().filter(singer -> singer.getId() == invocation.getArgumentAt(0, Long.class))
                    .findFirst()
                    .orElseThrow(()-> new IllegalArgumentException("Could not find matched ID."));
        });

        SingerController singerController = new SingerController();
        ReflectionTestUtils.setField(singerController, "singerService", singerService);

        Singer singerFindById = singerController.findSingerById(id);
        assertEquals(singerFindById, newSinger);
    }
}
