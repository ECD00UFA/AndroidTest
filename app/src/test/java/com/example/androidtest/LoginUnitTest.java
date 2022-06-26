package com.example.androidtest;

import android.app.Application;
import android.content.Context;

import com.example.androidtest.ui.login.FragmentLogin;
import com.example.androidtest.ui.login.VMFragmentLogin;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;


@RunWith(JUnit4.class)
public class LoginUnitTest {

    private static final String USER_NAME = "Faizan";
    private static final String PASS = "Test";

    @Mock
    Context mMockContext;

    @Mock
    Application application;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void validateUserLoginParamsWithUserNameEmpty() {
        VMFragmentLogin viewModel = new VMFragmentLogin(application);
        assertEquals(1, viewModel.validate("", PASS));
    }

    @Test
    public void validateUserLoginParamsWithPasswordEmpty() {
        VMFragmentLogin viewModel = new VMFragmentLogin(application);
        assertEquals(2, viewModel.validate(USER_NAME, ""));
    }

    @Test
    public void validateUserLoginSuccess() {
        VMFragmentLogin viewModel = new VMFragmentLogin(application);
        assertEquals(0, viewModel.validate(USER_NAME, PASS));
    }



}