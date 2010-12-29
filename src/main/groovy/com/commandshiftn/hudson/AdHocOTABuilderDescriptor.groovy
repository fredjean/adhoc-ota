/**
 * Created by IntelliJ IDEA.
 * User: fjean
 * Date: 12/28/10
 * Time: 3:28 PM
 * To change this template use File | Settings | File Templates.
 */

package com.commandshiftn.hudson

import hudson.Extension
import hudson.tasks.BuildStepDescriptor
import hudson.tasks.Builder
import hudson.model.AbstractProject
import org.kohsuke.stapler.StaplerRequest
import net.sf.json.JSONObject

@Extension
final class AdHocOTABuilderDescriptor extends BuildStepDescriptor<Builder> {
  def xcRunPath = "/usr/bin/xcrun"

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

  @Override
  boolean configure(StaplerRequest req, JSONObject json) {
    xcRunPath = json.getString("xcRunPath")
    save()
    true
  }


}