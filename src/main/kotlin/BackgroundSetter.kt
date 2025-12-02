import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.editor.colors.EditorColorsListener
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.openapi.vfs.LocalFileSystem
import java.nio.file.Paths

class BackgroundSetter : StartupActivity {
    private final val lightSchemeName = "Mechanicus Sacred Data-Slate"
    private final val darkSchemeName = "Mechanicus Forge World"

    override fun runActivity(project: Project) {
        applyBackgroundForCurrentScheme()

        // Listen for scheme changes
        EditorColorsManager.getInstance().addEditorColorsListener { this::applyBackgroundForCurrentScheme }
    }

    private fun applyBackgroundForCurrentScheme() {
        val scheme = EditorColorsManager.getInstance().globalScheme
        val pluginPath = Paths.get(javaClass.protectionDomain.codeSource.location.toURI()).parent

        if (scheme.name != lightSchemeName && scheme.name != darkSchemeName) return

        val imageFile = when {
            scheme.isDark -> pluginPath.resolve("resources/images/background_dark.png").toString()
            else -> pluginPath.resolve("resources/images/background_light.png").toString()
        }
        val file = LocalFileSystem.getInstance().findFileByPath(imageFile) ?: return

        val settings = ColorSettingsUtil.getBackgroundImageSettings()

        settings.file = file.toNioPath()
        settings.opacity = 0.2 // adjust transparency
        settings.visible = true
    }
}
