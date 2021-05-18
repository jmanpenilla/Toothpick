/*
 * This file is part of Toothpick, licensed under the MIT License.
 *
 * Copyright (c) 2020-2021 Jason Penilla & Contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package xyz.jpenilla.toothpick

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.maven
import org.gradle.kotlin.dsl.repositories
import xyz.jpenilla.toothpick.Constants.Dependencies.PaperMinecraftServer
import xyz.jpenilla.toothpick.Constants.Repositories

public class Toothpick : Plugin<Project> {
  override fun apply(project: Project) {
    project.extensions.create<ToothpickExtension>("toothpick", project)

    project.afterEvaluate {
      for (subproject in toothpick.subprojects) {
        configureRepositories(subproject)
        configureDependencies(subproject)
      }
      initToothpickTasks()
    }
  }

  private fun configureRepositories(subproject: ToothpickSubproject) {
    subproject.project.repositories {
      mavenCentral()
      maven(Repositories.MINECRAFT)
      maven(Repositories.AIKAR)
      mavenLocal {
        content {
          includeModule(PaperMinecraftServer.GROUP_ID, PaperMinecraftServer.ARTIFACT_ID)
        }
      }
      loadRepositories(subproject)
    }
  }

  private fun configureDependencies(subproject: ToothpickSubproject) {
    subproject.project.dependencies {
      loadDependencies(subproject)
    }
  }
}
