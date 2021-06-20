package hilled.pwnterm.component.codegen

import hilled.pwnterm.component.NeoComponent


class CodeGenComponent : NeoComponent {
  override fun onServiceInit() {
  }

  override fun onServiceDestroy() {
  }

  override fun onServiceObtained() {
  }

  fun newGenerator(codeObject: CodeGenObject): CodeGenerator {
    val parameter = CodeGenParameter()
    return codeObject.getCodeGenerator(parameter)
  }
}

class CodeGenParameter
