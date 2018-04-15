package com.cedex.automation.src.wrappers;

import com.cedex.automation.src.Lab;
import com.cedex.jsystem.ReporterLight;

public abstract class BaseWrapper implements ReporterLight {

    Lab lab;

    public BaseWrapper(Lab lab) {
        this.lab=lab;
    }
}
