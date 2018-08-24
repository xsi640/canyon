package com.canyon.boot;

import com.canyon.inject.InjectorContext;

public abstract class Boot {

    protected InjectorContext injectorContext;

    protected int order = 0;

    public abstract void run();

    public abstract void destory();
}
