package com.kovalenko.coursework.service;


import com.kovalenko.coursework.model.Corpus;

import java.io.InputStream;

public interface MainService {

    Corpus generateStat(InputStream file);
}
