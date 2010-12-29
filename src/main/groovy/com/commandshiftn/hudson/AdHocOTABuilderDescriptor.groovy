/**
 * Created by IntelliJ IDEA.
 * User: fjean
 * Date: 12/28/10
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */

package com.commandshiftn.hudson

import hudson.Extension
import hudson.model.AbstractProject
import hudson.tasks.BuildStepDescriptor
import hudson.tasks.Builder

@Extension
final class AdHocOTABuilderDescriptor extends BuildStepDescriptor<Builder> {
  AdHocOTABuilderDescriptor() {
    super(AdHocOTABuilder.class)
    load()
  }

  @Override
  boolean isApplicable(Class<? extends AbstractProject> aClass) {
    true
  }

  @Override
  String getDisplayName() {
    "Package application for Adhoc OTA distribution."
  }
}