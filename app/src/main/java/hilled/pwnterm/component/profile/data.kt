package hilled.pwnterm.component.profile

import hilled.neolang.frontend.ConfigVisitor
import hilled.pwnterm.component.ComponentManager
import hilled.pwnterm.component.ConfigFileBasedObject
import hilled.pwnterm.component.codegen.CodeGenObject
import hilled.pwnterm.component.codegen.CodeGenParameter
import hilled.pwnterm.component.codegen.CodeGenerator
import hilled.pwnterm.component.codegen.NeoProfileGenerator
import hilled.pwnterm.component.config.ConfigureComponent
import hilled.pwnterm.component.config.NeoConfigureFile
import hilled.pwnterm.utils.NLog
import org.jetbrains.annotations.TestOnly
import java.io.File

abstract class NeoProfile : CodeGenObject, ConfigFileBasedObject {
  companion object {
    private const val PROFILE_NAME = "name"
  }

  abstract val profileMetaName: String
  private val profileMetaPath
    get() = arrayOf(profileMetaName)

  var profileName = "Unknown Profile"

  override fun onConfigLoaded(configVisitor: ConfigVisitor) {
    profileName = configVisitor.getProfileString(PROFILE_NAME, profileName)
  }

  override fun getCodeGenerator(parameter: CodeGenParameter): CodeGenerator {
    return NeoProfileGenerator(parameter)
  }

  @TestOnly
  fun testLoadConfigure(file: File): Boolean {
    val loaderService = ComponentManager.getComponent<ConfigureComponent>()

    val configure: NeoConfigureFile?
    try {
      configure = loaderService.newLoader(file).loadConfigure()
      if (configure == null) {
        throw RuntimeException("Parse configuration failed.")
      }
    } catch (e: Exception) {
      NLog.e("Profile", "Failed to load profile: ${file.absolutePath}: ${e.localizedMessage}")
      return false
    }

    val visitor = configure.getVisitor()
    onConfigLoaded(visitor)
    return true
  }

  protected fun ConfigVisitor.getProfileString(key: String, fallback: String): String {
    return getProfileString(key) ?: fallback
  }

  protected fun ConfigVisitor.getProfileBoolean(key: String, fallback: Boolean): Boolean {
    return getProfileBoolean(key) ?: fallback
  }

  protected fun ConfigVisitor.getProfileString(key: String): String? {
    return this.getStringValue(profileMetaPath, key)
  }

  protected fun ConfigVisitor.getProfileBoolean(key: String): Boolean? {
    return this.getBooleanValue(profileMetaPath, key)
  }
}
