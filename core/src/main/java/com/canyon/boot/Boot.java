package com.canyon.boot;

import com.canyon.inject.InjectorContext;

import java.io.IOException;

public abstract class Boot {

    protected InjectorContext injectorContext;

    protected int order = 0;

    public abstract void run() throws Exception;

    public abstract void destory() throws Exception;
}
