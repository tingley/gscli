package com.spartansoftwareinc.globalsight.gscli;

import net.sundell.cauliflower.Command;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

@SuppressWarnings("static-access")
public abstract class GSCommand extends Command {

    @Override
    protected GSUserData getUserData() {
        return (GSUserData)super.getUserData();
    }

}
