package main.java.xml.entity;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Gem {
    private String name, preciousness, origin;
    private Double value;
    private Parameters parameters;
    private Date date;

    private static Logger logger = LogManager.getLogger();

    public Date getDate() {
        return date;
    }

    public void setDate(String dateLine) {
        try {
            this.date = new SimpleDateFormat("yyyy-MM-dd").parse(dateLine);
        } catch (ParseException ex) {
            logger.log(Level.ERROR, "Incorrect date pattern", ex);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPreciousness() {
        if (preciousness == null || preciousness.length() == 0) {
            return "unknown";
        }
        return preciousness;
    }

    public void setPreciousness(String preciousness) {
        this.preciousness = preciousness;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public static class Parameters {
        private String color;
        private int gemcutter, transparency;

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public int getGemcutter() {
            return gemcutter;
        }

        public void setGemcutter(int gemcutter) {
            this.gemcutter = gemcutter;
        }

        public int getTransparency() {
            return transparency;
        }

        public void setTransparency(int transparency) {
            this.transparency = transparency;
        }

        @Override
        public String toString() {
            return "color = " + getColor() + " gemcutter = " + getGemcutter() + " transparency = " + getTransparency();
        }
    }

    @Override
    public String toString() {
        return "\nname = " + getName() + " preciousness = " + getPreciousness() + " origin = " + getOrigin()
                + " value = " + getValue() + " date = " + getDate()
                + " parameters: " + getParameters();
    }
}
