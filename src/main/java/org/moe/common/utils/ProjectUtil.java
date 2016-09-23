/*
Copyright 2014-2016 Intel Corporation

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package org.moe.common.utils;

import org.moe.common.exec.ExecOutputCollector;
import org.moe.common.exec.GradleExec;

import java.io.File;

public class ProjectUtil {

    public static final String XCODE_PROJECT_PATH_KEY = "moe.xcode.xcodeProjectPath";
    public static final String XCODE_PROJECT_PATH_TASK = "moeXcodeProperties";
    public static final String SDK_PROPERTIES_TASK = "moeSDKProperties";
    public static final String SDK_PATH_KEY = "moe.sdk.home";

    public static String retrieveXcodeProjectPathFromGradle(File projectFile) {
        return retrievePropertyFromGradle(projectFile, XCODE_PROJECT_PATH_TASK, XCODE_PROJECT_PATH_KEY);
    }

    public static String retrieveSDKPathFromGradle(File projectFile) {
        return retrievePropertyFromGradle(projectFile, SDK_PROPERTIES_TASK, SDK_PATH_KEY);
    }

    public static String retrievePropertyFromGradle(File projectFile, String taskName, String modulePropertyKey) {
        String property = null;

        GradleExec exec = new GradleExec(projectFile, null, projectFile);
        exec.getArguments().add(taskName);
        exec.getArguments().add("-Dorg.gradle.daemon=true");
        exec.getArguments().add("-Dorg.gradle.configureondemand=true");

        String keyWord = modulePropertyKey + '=';

        try {
            property = ExecOutputCollector.collect(exec);
            property = property.substring(property.lastIndexOf(keyWord) + keyWord.length()).replace("\t", "");
            property = property.substring(0, property.indexOf('\n'));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            property = null;
        }

        return property;
    }
}
