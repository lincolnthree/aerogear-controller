package org.jboss.aerogear.core;

import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import java.lang.annotation.Annotation;

import static org.fest.assertions.Assertions.assertThat;

public class AeroGearTest {

    private AeroGear aeroGear;
    private Annotation[] annotations;
    private MediaType mediaType;

    @Before
    public void setUp() throws Exception {
        aeroGear = new AeroGear();
        annotations = new Annotation[]{};
        mediaType = new MediaType("text", "html");
    }

    @Test
    public void onlyProcessViewActions() {
        assertThat(aeroGear.isWriteable(View.class, View.class, annotations, mediaType)).isTrue();
        assertThat(aeroGear.isWriteable(Object.class, View.class, annotations, mediaType)).isFalse();
    }

    @Test
    public void neverKnowsTheContentSize() {
        assertThat(aeroGear.getSize(new Object(), View.class, View.class, annotations, mediaType)).isEqualTo(AeroGear.UNKNOWN_SIZE);
    }

}
