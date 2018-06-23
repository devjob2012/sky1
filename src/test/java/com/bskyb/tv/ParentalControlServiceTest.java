package com.bskyb.tv;

import static com.bskyb.tv.enums.ParentalControlLevelEnum.FIFTEEN;
import static com.bskyb.tv.enums.ParentalControlLevelEnum.U;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.bskyb.tv.enums.ParentalControlLevelEnum;
import com.bskyb.tv.exception.TechnicalFailureException;
import com.bskyb.tv.exception.TitleNotFoundException;
import com.bskyb.tv.service.MovieService;
import com.bskyb.tv.service.ParentalControlService;
import com.bskyb.tv.service.impl.ParentalControlServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class ParentalControlServiceTest {
	private ParentalControlService parentalControlServiceTest;
	private String movieId;
	private String customerParentalControlLevelPreference;
	@Mock
	private MovieService movieService;

	@Before
	public void setup() {
		parentalControlServiceTest = new ParentalControlServiceImpl(movieService);
	}

	@After
	public void tearDown() throws TitleNotFoundException, TechnicalFailureException {
		verifyNoMoreInteractions(movieService);
	}

	@Test
	public void canWatch_whenMovieParentalControlLevelEqualToCustomerParentalControlLevelPref() throws Exception {
		customerParentalControlLevelPreference = FIFTEEN.name();
		when(movieService.getParentalControlLevel(movieId)).thenReturn(FIFTEEN.name());
		boolean allowed = parentalControlServiceTest.canWatchMovie(customerParentalControlLevelPreference, movieId);
		assertTrue(allowed);
		verify(movieService).getParentalControlLevel(movieId);
	}

	@Test
	public void canWatch_whenMovieParentalControlLevelLessThenCustomerParentalControlLevelPref() throws Exception {
		customerParentalControlLevelPreference = FIFTEEN.name();
		when(movieService.getParentalControlLevel(movieId)).thenReturn(U.name());
		boolean allowed = parentalControlServiceTest.canWatchMovie(customerParentalControlLevelPreference, movieId);
		assertTrue(allowed);
		verify(movieService).getParentalControlLevel(movieId);
	}

	@Test
	public void cannotWatch_whenParentalControlLevelGreaterThenParentalControlLevelPref() throws Exception {
		customerParentalControlLevelPreference = FIFTEEN.name();

		when(movieService.getParentalControlLevel(movieId)).thenReturn(ParentalControlLevelEnum.EIGHTEEN.name());
		boolean allowed = parentalControlServiceTest.canWatchMovie(customerParentalControlLevelPreference, movieId);
		assertFalse(allowed);
		verify(movieService).getParentalControlLevel(movieId);
	}

	@Test
	public void throwTitleNotFoundException_whenMovieIdNotPresent() throws Exception {
		customerParentalControlLevelPreference = FIFTEEN.name();
		when(movieService.getParentalControlLevel(movieId)).thenThrow(TitleNotFoundException.class);
		try {
			parentalControlServiceTest.canWatchMovie(customerParentalControlLevelPreference, movieId);
			fail("Expected Exception.");
		} catch (Exception expected) {
			assertEquals("Movie not found", expected.getMessage());
		}
		verify(movieService).getParentalControlLevel(movieId);
	}

	@Test
	public void cannotWatch_whenTechnicalErrorOccur() throws Exception {
		customerParentalControlLevelPreference = FIFTEEN.name();
		when(movieService.getParentalControlLevel(movieId)).thenThrow(TechnicalFailureException.class);
		boolean allowed = parentalControlServiceTest.canWatchMovie(customerParentalControlLevelPreference, movieId);
		assertFalse(allowed);
		verify(movieService).getParentalControlLevel(movieId);
	}

	@Test
	public void isFailSafe_whenServiceDown() throws Exception {
		parentalControlServiceTest = new ParentalControlServiceImpl(null);
		boolean allowed = parentalControlServiceTest.canWatchMovie(customerParentalControlLevelPreference, movieId);
		assertFalse(allowed);
	}
}
