package com.example.utils.reports;

import java.io.Serializable;

import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.PatternLayout;

import com.aventstack.extentreports.Status;

@Plugin(name = "ExtentAppender", category = "Core", elementType = Appender.ELEMENT_TYPE)
public class ExtentAppender extends AbstractAppender {

    private final Layout<? extends Serializable> layout;

    protected ExtentAppender(String name, Layout<?> layout) {
        super(name, null, layout, false, null);
        this.layout = layout == null ? PatternLayout.createDefaultLayout() : layout;
    }

    @Override
    public void append(LogEvent event) {
        if (ExtentTestListener.getTest() != null) {
            String formattedMessage = new String(layout.toByteArray(event));
            ExtentTestListener.getTest().log(Status.INFO, formattedMessage);
        }
    }

    @PluginFactory
    public static ExtentAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginElement("Layout") Layout<? extends Serializable> layout) {
        return new ExtentAppender(name, layout);
    }
}
