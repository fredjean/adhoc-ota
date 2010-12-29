package com.commandshiftn.hudson

import hudson.Extension
import hudson.model.AbstractProject
import hudson.tasks.BuildStepDescriptor
import hudson.tasks.Builder

/**
 * Created by IntelliJ IDEA.
 * User: fjean
 * Date: 12/28/10
 * Time: 10:33 PM
 * To change this template use File | Settings | File Templates.
 */

@Extension
class XcodebuildBuilderDescriptor extends BuildStepDescriptor<Builder> {

  XcodebuildBuilderDescriptor() {
    super(XcodebuildBuilder.class)
    load()
  }

  @Override
  boolean isApplicable(Class<? extends AbstractProject> aClass) {
    true
  }

  @Override
  String getDisplayName() {
    "Xcode Builder"
  }
}
