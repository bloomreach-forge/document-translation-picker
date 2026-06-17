package org.onehippo.forge.documenttranslationpicker;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LinkTranslationsPluginTest {
    @Test
    void pluginClass_isAccessible() {
        assertDoesNotThrow(() ->
            Class.forName("org.onehippo.forge.documenttranslationpicker.plugin.LinkTranslationsWorkflowPlugin"));
    }
}
