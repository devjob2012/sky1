package com.bskyb.tv.service;

public interface ParentalControlService {
    boolean canWatchMovie(String customerParentalControlLevel, String movieId) throws Exception;
}
