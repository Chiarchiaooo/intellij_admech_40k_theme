import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.editor.colors.EditorColorsListener
import com.intellij.openapi.editor.colors.EditorColorsManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.StartupActivity
import com.intellij.openapi.wm.impl.IdeBackgroundUtil

class BackgroundSetter : StartupActivity {
    companion object {
        private const val DARK_SCHEME_NAME = "Mechanicus Forge World"
        private const val DARK_IMAGE_PATH = "/images/background_dark.png"

        private const val LIGHT_SCHEME_NAME = "Mechanicus Sacred Data-Slate"
        private const val LIGHT_IMAGE_PATH = "/images/background_light.png"
    }

    override fun runActivity(project: Project) {
        applyBackground()
        registerSchemeChangeListener()
    }

    private fun registerSchemeChangeListener() {
        ApplicationManager.getApplication().messageBus.connect()
            .subscribe(EditorColorsManager.TOPIC, EditorColorsListener { applyBackground() })
    }

    private fun applyBackground() {
        val schemeName = getCurrentSchemeName()
        if (!isTargetScheme(schemeName)) return

        val imagePath = getImagePathForScheme(schemeName)
        val imageUrl = loadImageUrl(imagePath) ?: return

        setEditorBackground(imageUrl)
    }

    private fun getCurrentSchemeName(): String {
        return EditorColorsManager.getInstance().globalScheme.name
    }

    private fun isTargetScheme(schemeName: String): Boolean {
        return schemeName == LIGHT_SCHEME_NAME || schemeName == DARK_SCHEME_NAME
    }

    private fun getImagePathForScheme(schemeName: String): String {
        return if (schemeName == DARK_SCHEME_NAME) DARK_IMAGE_PATH else LIGHT_IMAGE_PATH
    }

    private fun loadImageUrl(imagePath: String): String? {
        return javaClass.getResource(imagePath)?.toString()
    }

    private fun setEditorBackground(imageUrl: String) {
        PropertiesComponent.getInstance().setValue(IdeBackgroundUtil.EDITOR_PROP, imageUrl)
        IdeBackgroundUtil.repaintAllWindows()
    }
}