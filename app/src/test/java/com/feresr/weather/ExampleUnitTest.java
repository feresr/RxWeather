package com.feresr.weather;

import android.content.Context;
import android.content.res.Resources;

import com.feresr.weather.utils.IconManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {

    @Mock
    Context mockContext;
    @Mock
    Resources mockResources;


    @Test
    public void iconManager_getIconResource() throws Exception {
        when(mockContext.getString(R.string.rain))
                .thenReturn("&#xf019;");

        assertEquals(IconManager.getIconResource("rain", mockContext), "&#xf019;");
    }

    @Test
    public void iconManager_getColorResource() {;
        when(mockContext.getResources()).thenReturn(mockResources);
        when(mockResources.getColor(R.color.rain)).thenReturn(2);

        assertEquals(IconManager.getColorResource("rain", mockContext), 2);
    }
}