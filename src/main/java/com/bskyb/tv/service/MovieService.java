package com.bskyb.tv.service;

import com.bskyb.tv.exception.TechnicalFailureException;
import com.bskyb.tv.exception.TitleNotFoundException;

public interface MovieService {
        String getParentalControlLevel(String titleId) throws TitleNotFoundException, TechnicalFailureException;
}
