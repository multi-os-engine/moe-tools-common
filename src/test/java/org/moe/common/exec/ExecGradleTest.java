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

package org.moe.common.exec;

import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;

public class ExecGradleTest extends TestCase {

    public void testCanFindExec() throws IOException {
        GradleExec gradle = new GradleExec(new File("../master"), null);

        assertNotNull(gradle.getExecPath());
        assertFalse(gradle.getExecPath().isEmpty());
    }

}
