import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.colors.EditorColorsListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.openapi.vfs.LocalFileSystem
import java.nio.file.Paths

class BackgroundSetter : StartupActivity {
    override fun runActivity(project: Project) {
        applyBackgroundForCurrentScheme()
        // Listen for scheme changes
        EditorColorsManager.getInstance().addEditorColorsListener { scheme ->
            applyBackgroundForCurrentScheme()
        }
    }

    private fun applyBackgroundForCurrentScheme() {
        val scheme = EditorColorsManager.getInstance().globalScheme
        val pluginPath = Paths.get(System.getProperty("user.dir")) // adjust to your plugin path
        val imageFile = when {
            scheme.isDark -> pluginPath.resolve("resources/images/dark-bg.png").toString()
            else -> pluginPath.resolve("resources/images/light-bg.png").toString()
        }
        val file = LocalFileSystem.getInstance().findFileByPath(imageFile) ?: return

        val settings = com.intellij.openapi.options.colors.ColorSettingsUtil.getBackgroundImageSettings()
        settings.file = file.toNioPath()
        settings.opacity = 0.2 // adjust transparency
        settings.visible = true
    }
}
